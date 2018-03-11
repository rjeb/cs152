import java.io._
import scala.util.parsing.combinator._

object hw6part2 extends App {


class ListParser extends JavaTokenParsers { 

  def list: Parser[List[Int]] = (term ~ rep(("::") ~ term)) ^^ { 
      case a ~ lst =>  (a /: lst) { 
        case (x, "::" ~ y) => (x ++ y)
      }
    } 
  def term: Parser[List[Int]] = wholeNumber ^^ (x => List(x.toInt)) | "(" ~> list1 <~")"
      
  def list1 : Parser[List[Int]] = (term ~ rep((",") ~ term)) ^^ { 
      case a ~ lst =>  (a /: lst) { 
        case (x, "," ~ y) => (x ++ y)
      }
    } 
}

  val parser = new ListParser
parser.parseAll(parser.list, new InputStreamReader(System.in)) match {
  case parser.Success(result, next) => println(result)
  case _ => println("Error")
}  
}