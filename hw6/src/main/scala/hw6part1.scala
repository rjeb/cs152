import java.io._
import scala.util.parsing.combinator._

object hw6part1 extends App {

//copied code from my hw5part2
abstract class Expr{
    def eval(symbols : Map[String, Int]): Int
  }
    case class Const(value : Int) extends Expr{
      override def eval(symbols : Map[String, Int]): Int = {
        value
      }
    }
    
    case class Var(name : String) extends Expr{
      override def eval(symbols : Map[String, Int]): Int = {
        symbols(name)
      }
    }
    
    case class Def(name: String, expr: Expr) {
    }
    
    case class Product(args : Expr*) extends Expr{
      
      def recursiveProduct(args1: Int*) : Int = {
        if (args1.length == 0) 1
        else args1.head * recursiveProduct(args1.tail : _*)
      }
      
      def eval(symbols : Map[String, Int]): Int = {
        def recursiveProduct(args1: Expr*) : Int = {
          if (args1.length == 0) 1
          else args1.head.eval(symbols) * recursiveProduct(args1.tail : _*)
        }
        recursiveProduct(args:_*)
      }
    }
    
    case class Sum(args : Expr*) extends Expr{
      
      
      def eval(symbols : Map[String, Int]): Int = {
        def recursiveSum(args1: Expr*) : Int = {
          if (args1.length == 0) 0
          else args1.head.eval(symbols) + recursiveSum(args1.tail : _*)
        }
        recursiveSum(args:_*)
      }
    }
    
    case class Op(fun : Seq[Int] => Int,  args : Expr*) extends Expr {
      
      override def eval(symbols : Map[String, Int]): Int = {
        fun.apply(args.map(_.eval(symbols)))
      }
    }

def eval(expr : Expr, symbols : Map[String, Int]) : Int = expr match {
      case Const(c) => c
      case Var(v) => symbols(v)
      case Sum(s @ _*) => {
        def recursiveSum(args1: Int*) : Int = {
          if (args1.length == 0) 0
          else args1.head + recursiveSum(args1.tail : _*)
        }
        
        val intVals = s.map(_.eval(symbols))
        recursiveSum(intVals:_*)
      }
      case Product(p @ _*) => {
        def recursiveProduct(args1: Int*) : Int = {
          if (args1.length == 0) 1
          else args1.head * recursiveProduct(args1.tail : _*)
        }
        
        val intVals = p.map(_.eval(symbols))
        recursiveProduct(intVals:_*)
      }
      case Op(fun, args @ _*) => {
        fun.apply(args.map(_.eval(symbols)))
      }
      
    }
//end of code from hw5
  
case class Prog(defs: List[Def], expr: Expr) {
  def eval: Int = {
    def makeMap(list1 : List[Def], map: Map[String, Int]): Map[String, Int] = {
      if (list1.isEmpty){
        map
      }
      else{
        val emptyMap = Map[String, Int]()
        val map1 = map + (list1.head.name -> hw6part1.eval(list1.head.expr, emptyMap))
        makeMap(list1.tail, map1)
        
      }
    }
    val emptyMap1 = Map[String, Int]()
    val defMap = makeMap(defs, emptyMap1)
    
    hw6part1.eval(expr, defMap)
  }
}

def eval(expr : Expr[Int], symbols : Map[String, Int]) : Int = expr match {
      case Const(c) => c
      case Var(v) => symbols(v)
      case Sum(s @ _*) => {
        def recursiveSum(args1: Int*) : Int = {
          if (args1.length == 0) 0
          else args1.head + recursiveSum(args1.tail : _*)
        }
        
        val intVals = s.map(_.eval(symbols))
        recursiveSum(intVals:_*)
      }
      case Product(p @ _*) => {
        def recursiveProduct(args1: Int*) : Int = {
          if (args1.length == 0) 1
          else args1.head * recursiveProduct(args1.tail : _*)
        }
        
        val intVals = p.map(_.eval(symbols))
        recursiveProduct(intVals:_*)
      }
      case Op(fun, args @ _*) => {
        fun.apply(args.map(_.eval(symbols)))
      }
      
}

class SimpleLanguageParser extends JavaTokenParsers { 
  def prog: Parser[Prog] =
  def expr: Parser[Expr] = (term ~ rep(("+" | "-") ~ term)) ^^ { 
      case a ~ lst =>  (a /: lst) { 
        case (x, "+" ~ y) => Operator(x, y, _ + _)
        case (x, "-" ~ y) => Operator(x, y, _ - _)
      }
    } 
  def term: Parser[Expr] = (factor ~ rep(("*" | "/") ~ factor)) ^^ { 
      case a ~ lst =>  (a /: lst) { 
        case (x, "+" ~ y) => Operator(x, y, _ * _)
        case (x, "-" ~ y) => Operator(x, y, _ / _)
      }
    } 
  def factor : Parser[Expr] = ident ^^ (x => Variable(x)) | wholeNumber ^^ (x => Number(x.toInt)) | "(" ~> expr <~")"
}

  val parser = new SimpleLanguageParser
  parser.parseAll(parser.prog, new InputStreamReader(System.in)) match {
    case parser.Success(result, next) => println(result.eval)
    case _ => println("Error")
}    
}