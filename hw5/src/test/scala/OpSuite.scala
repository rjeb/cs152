import org.scalatest.FunSuite
import java.io._
import hw5part1._

class OpSuite extends FunSuite{
  test("simple expression") {
    assert(42 == Product(Const(6), Const(7)).value)
  }

  test("string op expression") {
    val cat: Expr[String] = new Op[String](_.mkString(""), Const("He"), Const("l"), Const("lo"))
    assert("Hello" == cat.value)
  }
}