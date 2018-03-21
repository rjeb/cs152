import java.io._
import scala.util.parsing.combinator._
class SimpleLanguageParser1 extends JavaTokenParsers {
  def expr: Parser[Int] = (term ~ opt(("+" | "-") ~ expr)) ^^ {
  case a ~ None => a
  case a ~ Some("+" ~ b) => a + b
  case a ~ Some("-" ~ b) => a - b
  }
  def term: Parser[Int] = (factor ~ opt(("*" | "/") ~ expr)) ^^ {
  case a ~ None => a
  case a ~ Some("*" ~ b) => a * b
  case a ~ Some("/" ~ b) => a / b
  }
  def factor: Parser[Int] = sqrtp | wholeNumber ^^ (_.toInt) | "(" ~> expr <~ ")"
  def sqrtp : Parser[Int] = ("sqrt" ~ factor) ^^ {
    case a ~ b => {
      (math.sqrt(b.toInt)).asInstanceOf[Int]
    }
  }
}
object AdditionalPracticePart3 extends App {
  val parser = new SimpleLanguageParser1
  val result = parser.parse(parser.expr, new InputStreamReader(System.in))
  println(result)
}
/*
import java.io._
import scala.util.parsing.combinator._
import scala.math._

//Change the arithmetic parser from the lab of lecture 10 
//so that it handles expressions of the form sqrt e, where 
//e is an expression. (Compute the integer square root.) The sqrt 
//operator should bind more strongly than multiplication—sqrt 4 * 10 is 20.
object AdditionalPracticePart3 extends App {

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
    
    
    case class Quotient(args : Expr*) extends Expr{
      
      
      def eval(symbols : Map[String, Int]): Int = {
        def recursiveQuo(args1: Expr*) : Int = {
          if (args1.length == 0) 1
          else args1.head.eval(symbols) / recursiveQuo(args1.tail : _*)
        }
        recursiveQuo(args:_*)
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
    
    case class Difference(args : Expr*) extends Expr{
      
      
      def eval(symbols : Map[String, Int]): Int = {
        def recursiveDiff(args1: Expr*) : Int = {
          if (args1.length == 0) 0
          else args1.head.eval(symbols) - recursiveDiff(args1.tail : _*)
        }
        recursiveDiff(args:_*)
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
      case Difference(p @ _*) => {
        def recursiveDiff(args1: Int*) : Int = {
          if (args1.length == 0) 0
          else args1.head - recursiveDiff(args1.tail : _*)
        }
        
        val intVals = p.map(_.eval(symbols))
        recursiveDiff(intVals:_*)
      }
      case Quotient(p @ _*) => {
        def recursiveQuo(args1: Int*) : Int = {
          if (args1.length == 0) 1
          else args1.head / recursiveQuo(args1.tail : _*)
        }
        
        val intVals = p.map(_.eval(symbols))
        recursiveQuo(intVals:_*)
      }
      
      case Op(fun, args @ _*) => {
        fun.apply(args.map(_.eval(symbols)))
      }
      
    }
  
case class Prog(defs: List[Def], expr: Expr) {
  def eval: Int = {
    def makeMap(list1 : List[Def], map: Map[String, Int]): Map[String, Int] = {
      if (list1.isEmpty){
        map
      }
      else{
        val emptyMap = Map[String, Int]()
        val map1 = map + (list1.head.name -> AdditionalPracticePart3.eval(list1.head.expr, emptyMap))
        makeMap(list1.tail, map1)
        
      }
    }
    val emptyMap1 = Map[String, Int]()
    val defMap = makeMap(defs, emptyMap1)
    
    AdditionalPracticePart3.eval(expr, defMap)
  }
}



class SimpleLanguageParser extends JavaTokenParsers { 
  def prog: Parser[Prog] = (rep(valdef) ~ expr) ^^ {
      case x ~ y => {
        Prog(x.toList, y)
      }
  }
  def valdef: Parser[Def] = ("val" ~> ident <~ "=") ~ expr <~ ";" ^^ { case s ~ e => Def(s, e) } 

  def expr: Parser[Expr] = (term ~ rep(("+" | "-") ~ term)) ^^ { 
      case a ~ lst =>  (a /: lst) { 
        case (x, "+" ~ y) => Sum(x, y)
        case (x, "-" ~ y) => Difference(x, y)
      }
    } 
  def term: Parser[Expr] = (factor ~ rep(("*" | "/") ~ factor)) ^^ { 
      case a ~ lst =>  (a /: lst) { 
        case (x, "*" ~ y) => Product(x, y)
        case (x, "/" ~ y) => Quotient(x, y)
      }
    } 
  def factor : Parser[Expr] = sqrtp |ident ^^ (x => Var(x)) | wholeNumber ^^ (x => Const(x.toInt)) | "(" ~> expr <~")"
  def sqrtp : Parser[Expr] = ("sqrt" ~ wholeNumber) ^^ {
    case a ~ b => {
      Const((sqrt(b.toInt)).asInstanceOf[Int])
    }
  }
}


  val parser = new SimpleLanguageParser
  parser.parseAll(parser.prog, new InputStreamReader(System.in)) match {
    case parser.Success(result, next) => println(result.eval)
    case _ => println("Error")
}    
}
*/