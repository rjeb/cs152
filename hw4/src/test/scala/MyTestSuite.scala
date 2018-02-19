import org.scalatest.FunSuite
import scala.util.matching._
import hw4._

class MyTestSuite extends FunSuite{
  
  test("Testing question 1 regex token function") {
    val patterns = List("if|def|val".r, """\p{L}(\p{L}|\p{N}|_)*""".r,
   """[+-]?\p{N}+""".r, "[+*/%<=>-]".r, "[(){};]".r)
    val ignore = List("""\p{Z}+""".r)
    val input = "if(x<0) 0 else root(x);"
    val result = tokens(input, patterns, ignore)
    assert(result == (List("if", "(", "x", "<", "0", ")"),-1))
  }
  
  test("Testing list of concatenations of two strings") {
    println(hw4.letters("2"))
  }
  
  
}