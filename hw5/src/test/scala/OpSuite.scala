import org.scalatest.FunSuite
import java.io._
import hw5part1._

class OpSuite extends FunSuite{
  test("simple expression Product") {
    assert(42 == Product(Const(6), Const(7)).value)
  }
  
  test("simple expression Sum") {
    assert(13 == Sum(Const(6), Const(7)).value)
  }

  
  test("string op expression") {
    val cat: Expr[String] = new Op[String](_.mkString(""), Const("He"), Const("l"), Const("lo"))
    assert("Hello" == cat.value)
  }
  
  test("input expression") {
    System.setIn(new ByteArrayInputStream(
      "13".getBytes(java.nio.charset.StandardCharsets.UTF_8)))
    System.setOut(new PrintStream(new ByteArrayOutputStream()))
    assert(25 == Sum(Const(2), Const(10), Read).value)
  }
  
   test("nestedExpr")
   {
      val p = Product(Const(2), Const(3))
      assert(7 == Sum(Const(1), Const(p.value)).value);
   }
   
   test("randomExpr")
   {
      assert(1029516620 == Sum(Const(Rand.value), Product(Const(Rand.value), Const(Rand.value))).value);
   }
  
  
}