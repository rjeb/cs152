import org.scalatest.FunSuite
import scala.util.matching._
import hw4._

class MyTestSuite extends FunSuite{
  
  //prof provided test case
  test("Testing question 1 regex token function") {
    val patterns = List("if|def|val".r, """\p{L}(\p{L}|\p{N}|_)*""".r,
   """[+-]?\p{N}+""".r, "[+*/%<=>-]".r, "[(){};]".r)
    val ignore = List("""\p{Z}+""".r)
    val input = "if(x<0) 0 else root(x);"
    val result = tokens(input, patterns, ignore)
    assert(result == (List("if", "(", "x", "<", "0", ")"),-1))
  }
  
  test("Testing list of concatenations of two strings") {
    assert(cats(letters("2"), letters("3")).toSet
    == Set("AD", "BD", "CD", "AE", "BE", "CE", "AF", "BF", "CF"))
  }
  
  test("Testing words for Digits") {
    assert(wordsForDigits("72252").contains("SCALA"))
  }
  
  test("Testing grow") {
    assert(grow("1", grow("2", grow("3", List(List("4"))))) == List(List("1", "2", "3", "4"), List("1", "2", "34"), List("1", "23", "4"), List("1", "234"), List("12", "3", "4"), List("12", "34"), List("123", "4"), List("1234")))
  }
  
  test("Testing substrings") {
    assert(substrings("2728") == List(List("2", "7", "2", "8"), List("2", "7", "28"), List("2", "72", "8"), List("2", "728"), List("27", "2", "8"), List("27", "28"), List("272", "8"), List("2728")))
  }
  
  test("Testing Phone Mnemonics") {
    assert(phoneMnemonics("7225247386") == List("PA ALB GS DUN", "PA ALB IS DUN", "PA ALB GS FUN", "PA ALB IS FUN", "PA ALB GS DUO", "PA ALB IS DUO", "PA ALB IRE UM", "PA ALB IRE TO", "PA BLAH RE UM", "PA BLAH RE TO", "SAC LA GS DUN", "SAC LA IS DUN", "SAC LA GS FUN", "SAC LA IS FUN", "SAC LA GS DUO", "SAC LA IS DUO", "SAC LA IRE UM", "SAC LA IRE TO", "SAC JAG RE UM", "SAC LAG RE UM", "SAC JAG RE TO", "SAC LAG RE TO", "SAC LAIR DUN", "SAC JAGS DUN", "SAC LAGS DUN", "SAC LAIR FUN", "SAC JAGS FUN", "SAC LAGS FUN", "SAC LAIR DUO", "SAC JAGS DUO", "SAC LAGS DUO", "PACK AH RE UM", "RACK AH RE UM", "SACK AH RE UM", "PACK AH RE TO", "RACK AH RE TO", "SACK AH RE TO", "PACK AIR DUN", "RACK AIR DUN", "SACK AIR DUN", "PACK AIR FUN", "RACK AIR FUN", "SACK AIR FUN", "PACK AIR DUO", "RACK AIR DUO", "SACK AIR DUO", "PACK BIRD UM", "RACK BIRD UM", "SACK BIRD UM", "PACK BIRD TO", "RACK BIRD TO", "SACK BIRD TO", "SCALA GS DUN", "SCALA IS DUN", "SCALA GS FUN", "SCALA IS FUN", "SCALA GS DUO", "SCALA IS DUO", "SCALA IRE UM", "SCALA IRE TO"))
  }
  
  //source: https://piazza.com/class/jc2tx52pwh47ad?cid=124
  test("Testing invalid tokens found") {
    val reported = List("if|def|val".r, """\p{L}(\p{L}|\p{N}|_)*""".r,
      """[+-]?\p{N}+""".r, "[+*/%<=>-]".r, "[(){};]".r, """[:.]""".r, "\".*\"".r)
    val ignored = List("""\p{Z}+""".r, """//.*""".r)

    val input = "if(x<0)& 0 else root(x);"

    assert(tokens(input, reported, ignored) ==
      (List("if", "(", "x", "<", "0", ")"), 7))
  }
  
  
}