

object hw5part2{
  
  abstract class Expr[T] {
    def eval(symbols : Map[String, T]): T
  }
    case class Const[T](value : T) extends Expr[T]{
      override def eval(symbols : Map[String, T]): T = {
        value
      }
    }
    
    case class Var[T](name : String) extends Expr[T]{
      override def eval(symbols : Map[String, T]): T = {
        symbols(name)
      }
    }
    
    case class Def[T](name: String, expr: Expr[T]) {
    }
    
    case class Product(args : Expr[Int]*) extends Expr[Int]{
      
      def recursiveProduct(args1: Int*) : Int = {
        if (args1.length == 0) 1
        else args1.head * recursiveProduct(args1.tail : _*)
      }
      
      def eval(symbols : Map[String, Int]): Int = {
        def recursiveProduct(args1: Expr[Int]*) : Int = {
          if (args1.length == 0) 1
          else args1.head.eval(symbols) * recursiveProduct(args1.tail : _*)
        }
        recursiveProduct(args:_*)
      }
    }
    
    case class Sum(args : Expr[Int]*) extends Expr[Int]{
      
      
      def eval(symbols : Map[String, Int]): Int = {
        def recursiveSum(args1: Expr[Int]*) : Int = {
          if (args1.length == 0) 0
          else args1.head.eval(symbols) + recursiveSum(args1.tail : _*)
        }
        recursiveSum(args:_*)
      }
    }
    
    case class Op[T](fun : Seq[T] => T,  args : Expr[T]*) extends Expr[T] {
      
      override def eval(symbols : Map[String, T]): T = {
        fun.apply(args.map(_.eval(symbols)))
      }
    }
    
    def eval[T](expr : Expr[T], symbols : Map[String, T]) : T = expr match {
      case Const(c) => c
      case Var(v) => symbols(v)
      case Sum(s @ _*) => {
        def recursiveSum(args1: Int*) : Int = {
          if (args1.length == 0) 0
          else args1.head + recursiveSum(args1.tail : _*)
        }
        
        val intVals = s.map(_.eval(symbols))
        recursiveSum(intVals:_*)
      }
      case Product(p @ _*) => {
        def recursiveProduct(args1: Int*) : Int = {
          if (args1.length == 0) 1
          else args1.head * recursiveProduct(args1.tail : _*)
        }
        
        val intVals = p.map(_.eval(symbols))
        recursiveProduct(intVals:_*)
      }
      case Op(fun, args @ _*) => {
        fun.apply(args.map(_.eval(symbols)))
      }
      
    }
    
    def eval[T](d : Def[T], symbols: Map[String, T]):Map[String, T] = {
      symbols + (d.name -> eval(d.expr, symbols))
    }
    
    
}