import java.io._
import scala.util.parsing.combinator._

object Main2 extends App{

class Expr
  case class Number(value : Int) extends Expr
  case class Variable(name : String) extends Expr
  case class Operator(left : Expr, right : Expr, 
    f: (Int, Int) => Int) extends Expr

  
class SimpleLanguageParser2 extends JavaTokenParsers {    
  def expr: Parser[Expr] = (term ~ opt(("+" | "-") ~ expr)) ^^ { 
    case a ~ None => a
    case a ~ Some("+" ~ b) => Operator(a, b, _ + _)
    case a ~ Some("-" ~ b) => Operator(a, b, _ - _)
    } 
  def term: Parser[Expr] = (factor ~ opt(("*" | "/") ~ term)) ^^ { 
    case a ~ None => a
    case a ~ Some("*" ~ b) => Operator(a, b, _ * _)
    case a ~ Some("/" ~ b) => Operator(a, b, _ / _)
  } 
  def factor: Parser[Expr] = wholeNumber ^^ (x => Number(x.toInt)) | "(" ~> expr <~ ")"
  
}

  val parser = new SimpleLanguageParser2
  val result = parser.parse(parser.expr, new InputStreamReader(System.in))
  println(result)       
}
