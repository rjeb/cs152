import scala.util.parsing.combinator._

object problem4 extends App {
   class SimpleLanguageParser extends JavaTokenParsers {    
     def expr: Parser[Boolean] = (term ~ opt(("||") ~ expr)) ^^ { 
       case a ~ None => a
       case a ~ Some("||" ~ b) => a || b
     } 
    
     def term: Parser[Boolean] = (factor ~ opt(("&&") ~ term)) ^^ { 
       case a ~ None => a
       case a ~ Some("&&" ~ b) => a && b
     }
     
     def factor: Parser[Boolean] = "true" ^^ (x => true) | "false" ^^ (x => false) | notExpr/*wholeNumber ^^ (_.toInt)*/ | "(" ~> expr <~ ")"
     def notExpr: Parser[Boolean] = ("!" ~> factor) ^^ {
       case a => !a
     }
   } 
   
  val parser = new SimpleLanguageParser
  val result = parser.parseAll(parser.expr, " !true || !false && !!true")
  
  //the following results are not printed but have been tested to evaluate
  val result1 = parser.parseAll(parser.expr, "true || false") // evals to true
  val result2 = parser.parseAll(parser.expr, "true && false") // evals to false
  val result3 = parser.parseAll(parser.expr, "false") // evals to false
  val result4 = parser.parseAll(parser.expr, "true") // evals to true
  val result5 = parser.parseAll(parser.expr, "!true") // evals to false
  val result6 = parser.parseAll(parser.expr, "!false") // evals to true
  val result7 = parser.parseAll(parser.expr, "!(true && false)") // evaluates to true
  
  println(result)//given test case evals to true         
}
