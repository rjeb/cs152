import java.io._
import scala.util.parsing.combinator._
//Write a Scala program that parses Lisp expressions. 
//A Lisp expression has the form ( e1 e2 ... ), where ei 
//is a list, integer, or identifier, or 'e, where e is a list or identifier.
object AdditionalPracticePart2 extends App {

class Expr
case class List1(attrs : List[Any]) extends Expr
case class Integer1(value: Int) extends Expr{
}
case class Indent1(name: String) extends Expr{
  
}


class LispLikeParser extends JavaTokenParsers { 
  def list1 : Parser[List1] = ("("~>rep(expr)<~")") ^^ {
    case a => List1(a.toList)
  }
  def expr : Parser[Expr] = wholeNumber ^^ (x => Integer1(x.toInt)) | ident ^^ (x => Indent1(x)) | apost | list1 
  
  def apost : Parser[Expr] = "'" ~> ident ^^ (x => Indent1(x)) | "'" ~> list1 
}

  val parser = new LispLikeParser
    parser.parseAll(parser.list1, new InputStreamReader(System.in)) match {
    case parser.Success(result, next) => println(result)
    case _ => println("Error")
  } 
}