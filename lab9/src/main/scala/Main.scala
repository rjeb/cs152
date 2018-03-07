import java.io._
import scala.util.parsing.combinator._

class SimpleLanguageParser1 extends JavaTokenParsers {    
  def expr: Parser[Int] = (term ~ opt(("+" | "-") ~ expr)) ^^ { 
    case a ~ None => a
    case a ~ Some("+" ~ b) => a + b
    case a ~ Some("-" ~ b) => a - b
    //case a ~ Some(_)
    }
  def term : Parser[Int] = (factor ~ opt(("*" | "/") ~ term)) ^^ { 
    case a ~ None => a
    case a ~ Some("*" ~ b) => a * b
    case a ~ Some("/" ~ b) => a / b
  }
  def factor: Parser[Int] = wholeNumber ^^ (_.toInt) | "(" ~> expr <~ ")"
}

object Main extends App {
  val parser = new SimpleLanguageParser1
  val result = parser.parse(parser.expr, new InputStreamReader(System.in))
  println(result)       
}