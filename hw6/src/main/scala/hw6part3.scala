import java.io._
import scala.util.parsing.combinator._

object hw6part3 extends App {

class Expr
case class Element(name : String, attrs : List[(String, String)], children : List[Element])
case class Def(name: String, value: String) {
}


class XMLLikeParser extends JavaTokenParsers { 
  def list : Parser[Element] = (expr | selfClosing) ^^ {
    case a => a
  }
  def expr: Parser[Element] = (recursiveDef | simpleDef) ^^ { 
       case a => a
    }
  def recursiveDef: Parser[Element] = ((tag ~ (rep(list)|rep(attr)) <~ endtag )) ^^ { 
       case a ~ b => b match{
         case b: List[Element] => {
           Element(a, List(), b)
         }
         case b: List[(String, String)] => {
           Element(a, b, List())
         }
       }
    }
  def simpleDef : Parser[Element] = (deftag ~rep(list) <~endtag) ^^ {
    case a ~ b => Element(a.name, a.attrs, b.toList)
  }
  
  def deftag : Parser[Element] = ("<"~>ident ~ rep(valdef) <~">") ^^ {
    case a ~ b=> Element(a, b, List())
  }
  def children : Parser[Element] = (tag ~ rep(list) <~ endtag) ^^ {
    case a ~ b => Element(a, List(), b)
  }
  
  def tag: Parser[String] = ("<" ~> ident <~ ">") ^^{
    case a => a
  }
  
  def endtag: Parser[String] = ("</" ~> ident <~ ">") ^^ {
    case a => a
  }
  
  def selfClosing: Parser[Element] = ("<" ~> ident ~ rep(valdef)<~ "/>") ^^{
    case a ~ lst => {
      Element(a, lst.toList, List())
    }
  }
  
  def valdef: Parser[(String, String)] = (ident <~ "=") ~ stringLiteral ^^ { case s ~ e => (s, e) } 
  
  def attr : Parser[(String, String)] = (tag ~ (stringLiteral|ident) <~ endtag) ^^ {
    case a~b => (a,b)
  }
}

  val parser = new XMLLikeParser
    parser.parseAll(parser.list, new InputStreamReader(System.in)) match {
    case parser.Success(result, next) => println(result)
    case _ => println("Error")
  } 
}