
import java.io._
import scala.util.parsing.combinator._

class SL3Type
case class SL3Int() extends SL3Type
case class SL3Bool() extends SL3Type
case class SL3Fun(paramTypes : List[SL3Type], returnType : SL3Type) extends SL3Type

class ExprTree 

case class Block(defs : List[Valdef], expr : ExprTree)

case class Number(value : Int) extends ExprTree
case class Variable(name : String) extends ExprTree
case class Operator(left : ExprTree, right : ExprTree,
  f: String) extends ExprTree

case class BoolOp(left : ExprTree, right : ExprTree,
  f: String) extends ExprTree

case class Equals(left : ExprTree, right : ExprTree) extends ExprTree
case class NotEquals(left : ExprTree, right : ExprTree) extends ExprTree

case class Function(params : List[(String, SL3Type)], body : Block) extends ExprTree
case class IfExpr(cond : ExprTree, thenBlock : Block, elseBlock : Block) extends ExprTree

case class Funcall(fun : ExprTree, args : List[ExprTree]) extends ExprTree

case class Valdef(name : String, expr : ExprTree)

class SL3Parser extends JavaTokenParsers {
  def block: Parser[Block] = rep(valdef) ~ expr ^^ { case lst ~ e => Block(lst, e) }

  def expr: Parser[ExprTree] = ("if" ~> ("(" ~> expr <~ ")") ~ block <~ "else") ~ block ^^ {
    case e ~ b1 ~ b2 => IfExpr(e, b1, b2)
  } | expr2

  def expr2: Parser[ExprTree] = (expr3 ~ rep(("==" | "!=") ~ expr3)) ^^ {
      case a ~ lst => (a /: lst) {
        case (x, "==" ~ y) => Equals(x, y)
        case (x, "!=" ~ y) => NotEquals(x, y)
      }
    }

  def expr3: Parser[ExprTree] = (expr4 ~ rep(("<" | "<=" | ">" | ">=" ) ~ expr4)) ^^ {
      case a ~ lst => (a /: lst) {
        case (x, op ~ y) => BoolOp(x, y, op)
      }
    }

  def expr4: Parser[ExprTree] = (term ~ rep(("+" | "-") ~ term)) ^^ {
      case a ~ lst => (a /: lst) {
        case (x, op ~ y) => Operator(x, y, op)
      }
    }

  def term: Parser[ExprTree] = (factor ~ rep(("*" | "/" ) ~ factor)) ^^ {
      case a ~ lst =>  (a /: lst) {
        case (x, op ~ y) => Operator(x, y, op)
      }
    }

  def factor: Parser[ExprTree] = wholeNumber ^^ { x : String => Number(x.toInt) } |
      "(" ~> expr <~ ")" | valOrFuncall

  def valOrFuncall = valOrFun ~ opt( "(" ~> repsep(expr, ",") <~ ")" ) ^^ {
    case expr ~ Some(args) => Funcall(expr, args)
    case expr ~ None => expr
  }

  def valOrFun = "(" ~> expr <~ ")" |
    ident ^^ { Variable(_) } |
    funliteral

  def typeSpec : Parser[SL3Type] = "Int" ^^ { x : String => SL3Int() } |
    "Bool" ^^ { x : String => SL3Bool() } |
    ("(" ~> repsep(typeSpec, ",") <~ ")") ~ ("=>" ~> typeSpec) ^^ {
      case paramTypes ~ returnType => SL3Fun(paramTypes, returnType)
    }

  def typedIdent : Parser[(String, SL3Type)] = (ident <~ ":") ~ typeSpec ^^ {
    case id ~ ty => (id, ty)
  }

  def funliteral: Parser[Function] = ("{" ~> repsep(typedIdent, ",") <~ "=>") ~ block <~ "}" ^^ {
    case params ~ block => Function(params, block)
  }

  def funcall: Parser[ExprTree] = expr ~ ( "(" ~> repsep(expr, ",") <~ ")" ) ^^ {
    case fun ~ args => Funcall(fun, args)
  }

  def valdef: Parser[Valdef] = ("val" ~> ident <~ "=") ~ (expr <~ ";") ^^ {
    case name ~ expr => Valdef(name, expr)
  }
}


object Main {
  def spy[T](t : T) = { println(t); t }

  def lookup[T](name : String, symbols : List[(String, T)]) =
    symbols.find(_._1 == name) match {
      case Some(pair) => pair._2
    }

  def typeof(expr : ExprTree, types : List[(String, SL3Type)]) : SL3Type =
    expr match {
      case Number(_) => SL3Int()
      case Variable(name) => lookup(name, types)
      case Operator(left, right, _) => {
        assert(typeof(left, types) == SL3Int())
        assert(typeof(right, types) == SL3Int())
        SL3Int()
        }
      case BoolOp(left, right, _) => {
        assert(typeof(left, types) == SL3Int())
        assert(typeof(right, types) == SL3Int())
        SL3Bool()
        }
      case Equals(_, _) => SL3Bool()
      case NotEquals(_, _) => SL3Bool()
      case IfExpr(cond, block1, block2) => {
        assert(typeof(cond, types) == SL3Bool())
        val blocktype = typeofBlock(block1, types)
        assert(blocktype == typeofBlock(block2, types))
        blocktype
      }
      case Function(params, body) => SL3Fun(
        params.map({_._2}), typeofBlock(body, params ::: types))

      case Funcall(expr, args) => {
        val exprType = typeof(expr, types)
        exprType match {
          case fun : SL3Fun => {
            // assert fun params match types of args
            assert(fun.paramTypes.size == args.size)
            assert(fun.paramTypes.zip(args).forall(scala.Function.tupled {
              _ == typeof(_, types) }))
            fun.returnType
          }
        }
      }
    }

    def typeofDef(types : List[(String, SL3Type)], defn : Valdef) : List[(String, SL3Type)] =
    defn match {
      case Valdef(name, expr) => { (name, typeof(expr, types)) :: types }
    }

    def typeofBlock(block : Block, symbols : List[(String, SL3Type)]) : SL3Type = {
      typeof(block.expr, (symbols /: block.defs) {
        typeofDef(_, _) } )
    }

  def stringify[T](vals : List[T], f : T => String) : String = vals.map(f).mkString(", ")

  def sl3typeToJavaType(type3 : SL3Type) : String = 
    type3 match {    
    case a : SL3Int => "Integer"
    case b : SL3Bool => "Boolean"
    case c : SL3Fun => 
      if (c.paramTypes.length > 1){
        "Function2" + "<" + stringify(c.paramTypes, sl3typeToJavaType) + ", " + sl3typeToJavaType(c.returnType) + ">"
      }
      else{
      "Function1" + "<" + stringify(c.paramTypes, sl3typeToJavaType) + ", " + sl3typeToJavaType(c.returnType) + ">"
      } 
  }
  
  def codegen(expr : ExprTree, types : List[(String, SL3Type)]) : String =
  expr match {
      case Number(num) => "" + num
      case Variable(name) => name
      case Operator(left, right, f) => codegen(left, types) + " " + f + " " + codegen(right, types)
      case BoolOp(left, right, f) => codegen(left, types) + " " + f + " " + codegen(right, types)
      case Equals(left, right) => codegen(left, types) + " == " + codegen(right, types)
      case NotEquals(left, right) => codegen(left, types) + " != " + codegen(right, types)
      case IfExpr(cond, block1, block2) => "if (" + codegen(cond, types) + ") \n" + codegenBlock(block1, types) + " else \n" + codegenBlock(block2, types)
      case Function(params, body) => "new " + sl3typeToJavaType(typeof(Function(params, body), types)) + 
        "() { public " + sl3typeToJavaType(typeofBlock(body, params ::: types)) + 
        " invoke(" + 
        stringify(params, ((nt : (String, SL3Type)) => sl3typeToJavaType(nt._2) + " " + nt._1)) + 
        ") \n " + 
        codegenBlock(body, params ::: types) + "}\n"
      //case Funcall(fun, args) => codegen(fun, types) + ".invoke(" + args.foldLeft("")((A,B) => if (A.length() == 0){A + codegen(B, types)} else A + ", " + codegen(B, types)) + ")"
      case Funcall(fun, args) => codegen(fun, types) + ".invoke(" + stringify(args, (B : ExprTree) => codegen(B, types)) + ")"
  }

  def codegenDefs(defs : List[Valdef], expr : ExprTree, types : List[(String, SL3Type)]) : String =
    if (defs.isEmpty) "return " + codegen(expr, types) + ";\n" else defs.head match {
      case Valdef(defName, defExpr) => 
        val defType =  typeof(defExpr, types)
        "final " + sl3typeToJavaType(defType) + " " + defName + " = " + codegen(defExpr, types) + ";\n" +
        codegenDefs(defs.tail, expr, (defName, defType) :: types)
  }

  def codegenBlock(block : Block, types : List[(String, SL3Type)]) : String = "{\n" + 
    codegenDefs(block.defs, block.expr, types) + "}\n"

  def main(args : Array[String]) : Unit = {
    val parser = new SL3Parser
    val result = parser.parse(parser.block, new InputStreamReader(System.in)).get
    println("// " + result);
    println("interface Function0<R> { R invoke(); }");
    println("interface Function1<A1, R> { R invoke(A1 a); }");
    println("interface Function2<A1, A2, R> { R invoke(A1 a1, A2 a2); }");
    println("interface Function3<A1, A2, A3, R> { R invoke(A1 a1, A2 a2, A3 a3); }");
    println("public class Main {")
    println("public static void main(String[] args) { System.out.println(main()); }")
    println("public static " +  sl3typeToJavaType(typeofBlock(result, List())) + " main()")
    println(codegenBlock(result, List()))
    println("}");
  }
}