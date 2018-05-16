import java.io._
import scala.util.parsing.combinator._

import org.apache.bcel._
import org.apache.bcel.generic._
import org.apache.bcel.Constants._

class Statement
class Expr extends Statement
                  
case class Number(value : Int) extends Expr
case class Variable(name : String) extends Expr
case class Operator(left : Expr, right : Expr, 
                    op : String) extends Expr
case class Funcall(fun : String, args : List[Expr]) extends Expr

case class Fundef(name : String, params : List[String], defs : List[String], stats : List[Statement], result : Expr)

case class Block(stats : List[Statement])

case class Assign(left : String, right : Expr) extends Statement
case class BoolOp(left : Expr, right : Expr, 
                  op : String) 

case class IfStat(cond : BoolOp, thenBlock : Block, elseBlock : Option[Block]) extends Statement
case class WhileStat(cond : BoolOp, block : Block) extends Statement

case class Program(funs : List[Fundef])

class SL3Parser extends JavaTokenParsers {
  def program: Parser[Program] = rep(fundef) ^^ { Program(_) }
  
  def stat: Parser[Statement] = assign | ifstat | whilestat | expr <~ ";" 

  def block: Parser[Block] = stat ^^ (x => Block(List(x))) | 
    "{" ~> rep(stat) <~ "}" ^^ { Block(_) }
    
  def fundef: Parser[Fundef] = ident ~ ("(" ~> repsep(ident, ",") <~ ")") ~ 
    ("{" ~> rep(vardef)) ~ rep(stat) ~ (("return" ~> expr <~ ";") <~ "}") ^^ { 
      case name ~ params ~ vars ~ stats ~ result => Fundef(name, params, vars, stats, result) }
  
  def vardef: Parser[String] = "var" ~> ident  <~ ";"
 
  def assign: Parser[Assign] = (ident <~ "=") ~ (expr <~ ";") ^^ {
    case ident ~ expr => Assign(ident,expr) }
  
  def ifstat: Parser[Statement] =  "if" ~> ("(" ~> cond <~ ")") ~ block ~ opt("else" ~> block) ^^ { 
    case e ~ b1 ~ b2 => IfStat(e, b1, b2) }
    
  def whilestat: Parser[Statement] = "while" ~> ("(" ~> cond <~ ")") ~ block ^^ { case cond ~ body => WhileStat(cond, body) }
 
  def expr: Parser[Expr] = (term ~ rep(("+" | "-") ~ term)) ^^ { 
    case a ~ lst => (a /: lst) { 
      case (x, op ~ y) => Operator(x, y, op)
    }
  }	
  
  def term: Parser[Expr] = (factor ~ rep(("*" | "/" ) ~ factor)) ^^ { 
    case a ~ lst =>  (a /: lst) { 
      case (x, op ~ y) => Operator(x, y, op)
    }
  }
  
  def factor: Parser[Expr] = wholeNumber ^^ { x : String => Number(x.toInt) } |
    varOrFuncall | "(" ~> expr <~ ")"
  
  def varOrFuncall: Parser[Expr] = ident ~ opt( "(" ~> repsep(expr, ",") <~ ")" ) ^^ { 
    case name ~ Some(args) => Funcall(name, args)
    case name ~ None => Variable(name) 
  }   
  
  def cond: Parser[BoolOp] = expr ~ ("==" | "!=" | "<=" | "<" | ">=" | ">") ~ expr ^^ { 
    case x ~ op ~ y => BoolOp(x, y, op)}
}	
  
object Main extends App {
  def codegen(prog : Program, className : String) {
    val cg = new ClassGen(className, "java.lang.Object", // Class, superclass
      "(no filename)", ACC_PUBLIC | ACC_SUPER, null)
    val cp = cg.getConstantPool() // cg creates constant pool
    val il = new InstructionList()
    val factory = new InstructionFactory(cg)
    var mg : MethodGen = null
  
    val notrel2op = Map(">=" -> Constants.IFLT, ">" -> Constants.IFLE, "<=" -> Constants.IFGT,
      "<" -> Constants.IFGE, "!=" -> Constants.IFEQ, "==" -> Constants.IFNE)
    
    // Nested functions to generate code for blocks, statements, expressions, etc.
  
    def codeofBlock(block : Block) = {
      block.stats.foreach(codeofStat _)
    }
    
    def codeofDef(name : String) = {
      val varGen = mg.addLocalVariable(name, Type.INT, null, null)
      val varIndex = varGen.getIndex()
      il.append(factory.createConstant(0))
      val loc = il.append(InstructionFactory.createStore(Type.INT, varIndex))
      varGen.setStart(loc)
    }
    
    def codeofStat(stat : Statement) : Unit = {
      stat match {
        case IfStat(BoolOp(expr1, expr2, rel), body1, optBody2) => {
          /*
           * expr1
           * expr2
           * isub
           * if !rel goto :label1
           * body1
           * goto :label2
           * :label1 nop
           * body2
           * :label2 nop
           */
          codeof(expr1)
          codeof(expr2)
          il.append(InstructionFactory.createBinaryOperation("-", Type.INT))
          val instr1 = InstructionFactory.createBranchInstruction(notrel2op(rel), null)
          il.append(instr1)
          codeofBlock(body1)
          val instr2 = InstructionFactory.createBranchInstruction(Constants.GOTO, null)
          il.append(instr2)
          instr1.setTarget(il.append(InstructionConstants.NOP))
          optBody2 match {
            case Some(body2) => codeofBlock(body2)
            case None =>
          }
          instr2.setTarget(il.append(InstructionConstants.NOP))
        }
        case WhileStat(BoolOp(expr1, expr2, rel), body) => {
          /*
           * :label1 nop
           * expr1
           * expr2
           * isub
           * if !rel goto :label2
           * body
           * goto :label1
           * :label2 nop
           */
          val loc = il.append(InstructionConstants.NOP)
          codeof(expr1)
          codeof(expr2)
          il.append(InstructionFactory.createBinaryOperation("-", Type.INT))
          val instr = InstructionFactory.createBranchInstruction(notrel2op(rel), null)
          il.append(instr)
          codeofBlock(body)
          il.append(InstructionFactory.createBranchInstruction(Constants.GOTO, loc))
          instr.setTarget(il.append(InstructionConstants.NOP))
        }
        case Assign(left, right) => {
          codeof(right)
          mg.getLocalVariables().find(_.getName() == left) match { case Some(v) =>
            il.append(InstructionFactory.createStore(v.getType, v.getIndex))
          }
        }
        case e : Expr => {
          codeof(e)
          il.append(InstructionConstants.POP)
        }
      }
    }
    
    def codeof(expr : Expr) : Unit = {
      expr match {
        case Number(n) => il.append(factory.createConstant(n))
        case Variable(name) => {
          mg.getLocalVariables().find(_.getName() == name) match { case Some(v) =>
            il.append(InstructionFactory.createLoad(v.getType, v.getIndex))
          }
        }
        case Operator(left, right, op) => {
          codeof(left)
          codeof(right)
          il.append(InstructionFactory.createBinaryOperation(op, Type.INT))
        }
        case Funcall(name, args) => {
          args.foreach(codeof _)
          prog.funs.find(_.name == name) match {
            case Some(f) =>
              il.append(factory.createInvoke(className, f.name,
                Type.INT, f.params.map(x => Type.INT).toArray, // return type, param types
                Constants.INVOKESTATIC))
            case None => {
              assert(name == "print")
              il.append(factory.createInvoke(className, "print",
                Type.INT, Array(Type.INT), // return type, param types
                Constants.INVOKESTATIC))
            }
          }
        }
      }
    }
    // Back to the codegen function
    // Make a static function for every function
    
    prog.funs.foreach((f : Fundef) => {
      mg = new MethodGen(ACC_STATIC | ACC_PUBLIC, // access flags
        Type.INT,
        f.params.map(x => Type.INT).toArray, // param types
        f.params.toArray, // param names
        f.name, className, // method, class
        il, cp)
      
      f.defs.foreach(codeofDef _)
      codeofBlock(Block(f.stats))
      codeof(f.result)
      il.append(InstructionFactory.createReturn(Type.INT))
      mg.setMaxStack()
      cg.addMethod(mg.getMethod())
      il.dispose()
    })
  
    // In main(String[] args), call main()
    val mg1 = new MethodGen(ACC_STATIC | ACC_PUBLIC, // access flags
      Type.VOID, // return type
      Array(new ArrayType(Type.STRING, 1)), // argument types--had to change new Type[] {...}
      Array( "argv" ), // arg names
      "main", className, // method, class
      il, cp)
    il.append(factory.createInvoke(className, "main",
      Type.INT, Array(),
      Constants.INVOKESTATIC))
    il.append(InstructionFactory.createReturn(Type.VOID))
    mg1.setMaxStack()
    cg.addMethod(mg1.getMethod())
    il.dispose()
    
    // add a static function print(int)
    
    val mg2 = new MethodGen(ACC_STATIC | ACC_PUBLIC, // access flags
      Type.INT, // return type
      Array(Type.INT), // argument types--had to change new Type[] {...}
      Array( "x" ), // arg names
      "print", className, // method, class
      il, cp)
    il.append(factory.createFieldAccess("java.lang.System", "out",
      new ObjectType("java.io.PrintStream"),
      Constants.GETSTATIC))
    il.append(InstructionFactory.createLoad(Type.INT, 0))
    il.append(factory.createInvoke("java.io.PrintStream", "println",
      Type.VOID, Array(Type.INT),
      Constants.INVOKEVIRTUAL))
    il.append(InstructionFactory.createLoad(Type.INT, 0))
    il.append(InstructionFactory.createReturn(Type.INT))
    
    mg2.setMaxStack()
    cg.addMethod(mg2.getMethod())
    il.dispose()
    cg.addEmptyConstructor(ACC_PUBLIC)
    cg.getJavaClass().dump(className + ".class")
  }
  
  val parser = new SL3Parser
  val result = parser.parse(parser.program,
    if (args.size == 0) new InputStreamReader(System.in) else
      new FileReader(args(0)))
  println(result.get)
  codegen(result.get, if (args.size == 0) "Main" else args(0).replaceAll(".sl4", ""))
}
