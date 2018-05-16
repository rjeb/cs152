import java.io._
import scala.util.parsing.combinator._

object Question6 {
  case class Term(fun : String, args : List[Term])
  case class Clause(fun : Term, terms : List[Term])
  
  class PrologParser extends JavaTokenParsers {
    def program : Parser[List[Clause]] = rep(clause|clause1) ^^ {
      case a => a
    }
    def clause : Parser[Clause] = term <~"." ^^ {
      case a => Clause(a, List())
    }
    def clause1 : Parser[Clause] = term ~ ":-" ~ terms <~"." ^^ {
      case a ~ ":-" ~ b => Clause(a, b)
    }
    def terms : Parser[List[Term]] = term ~ rep("," ~> term)^^ {
      case a ~ b => List(a) ++ b
    }
    def term : Parser[Term] = ident ~ opt("(" ~> repsep(term, ",") <~ ")") ^^ {
      case f ~ None => Term(f, List())
      case f ~ Some(a) => Term(f, a)
    }
  }
    
  def main(args : Array[String]) : Unit = {
    val parser = new PrologParser
    val result = parser.parse(parser.program, new InputStreamReader(System.in)).get
    println(result)
  }
}