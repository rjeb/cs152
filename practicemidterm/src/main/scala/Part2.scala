import java.io._
import scala.util.parsing.combinator._

object Part2 extends App {


class StringParser extends JavaTokenParsers { 

  def list: Parser[String] = (term ~ rep(("+") ~ term)) ^^ { 
      case a ~ lst =>  (a /: lst) { 
        case (x, "+" ~ y) => (x ++ y)
      }
    } 
  
  def term: Parser[String] = (factor ~ opt(("*") ~> num)) ^^ { 
      case a ~ Some(args) => {
        def stringMult(str : String, num : Int) : String = {
          if (num == 0){
            ""
          }
          else{
            str++stringMult(str, num-1)
          }
        }
       
       stringMult(a, args);
      }
      case a ~ None => a 
    }
  
  def num: Parser[Int] = wholeNumber ^^ (x => x.toInt)
  
  def factor: Parser[String] = strLit ~ opt("[" ~> num ~ "," ~ num <~"]") ^^ {
    case a ~Some(args) => {
      a.substring(args._1._1, args._2)
    }
    case a ~ None => a
  }
  
  def strLit: Parser[String] = ident ^^ (x => x.toString())
  
  def subString : Parser[String] = (factor ~"("~ num ~ "," ~ num ~ ")") ^^ {
    case a ~ b ~ c ~ d ~ e ~ f => {
      println("You got here")
      a.substring(c, e)
    }
  }
}

  val parser = new StringParser
parser.parseAll(parser.list, new InputStreamReader(System.in)) match {
  case parser.Success(result, next) => println(result)
  case _ => println("Error")
}  
}
