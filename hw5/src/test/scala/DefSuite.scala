
import org.scalatest.FunSuite
import hw5part2._

class DefSuite extends FunSuite {
  
  test("Variable") {
    assert(42 == Var("x").eval(Map("x" -> 42)))
    assert("Hello" == Var("greeting").eval(Map("greeting" -> "Hello", "location" -> "World")))
  }

  test("Expression with sum") {
    assert(13 == Sum(Const(6), Var("factor")).eval(Map("factor" -> 7)))

  }
  
  test("Expression with variable") {
    assert(42 == Product(Const(6), Var("factor")).eval(Map("factor" -> 7)))

  }
  
  test("Op test"){
    val cat: Expr[String] = new Op[String](_.mkString(""), Const("He"), Const("l"), Const("lo"))
    assert("Hello" == cat.eval(Map()))
  }
  
  test("Def test"){
    
    val cat: Expr[String] = new Op[String](_.mkString(""), Const("He"), Const("l"), Const("lo"))
    val test = new Def[Int]("s", Const(2))
    assert("Hello" == cat.eval(Map()))
  }
  
  
}