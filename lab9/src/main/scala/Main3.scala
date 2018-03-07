import java.io._
import scala.util.parsing.combinator._

class Expr
  case class Number(value : Int) extends Expr
  case class Variable(name : String) extends Expr
  case class Operator(left : Expr, right : Expr, 
    f: (Int, Int) => Int) extends Expr
  

  
class SimpleLanguageParser3 extends JavaTokenParsers {    
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

object Main extends App {
}