 class Expr
  case class Number(value : Int) extends Expr
  case class Variable(name : String) extends Expr
  case class Operator(left : Expr, right : Expr, f: (Int, Int) => Int) extends Expr
  case class Definition(name : String, expr : Expr)
  

object Main extends App{
  abstract class SimpleTree
  case class Leaf(value : Int) extends SimpleTree
  case class Node(left : SimpleTree, right : SimpleTree) extends SimpleTree 
  
  def sum(t : SimpleTree) : Int = t match { 
    case Node(l, r) => sum(l) + sum(r) 
    case Leaf(v) => v 
  }

  def eval(expr : Expr, symbols : Map[String, Int]) : Int = 
  expr match {
    case Number(num) => num
    case Variable(name) => symbols(name)
    case Operator(left, right, f) => f(eval(left, symbols), eval(right, symbols))
  }
  
  //def eval2(expr: Expr)
  
  val textExpr = Operator(Number(3), Operator(Number(4), Variable("x"), _*_), _+_)
  println("Tree for expression: " + textExpr)
  val answer = eval(textExpr, Map("x" -> 5))
  
  println(answer)
  
  
}