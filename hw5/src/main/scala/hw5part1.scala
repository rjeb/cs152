

object hw5part1{
  abstract class Expr[T] {
    def value: T
  }
  
    case class Const[T](value : T) extends Expr[T]
    //case class Variable(name : String) extends Expr[String]
    
    case class Op[T](fun : Seq[T] => T,  args : Expr[T]*) extends Expr[T] {
      override def value: T = fun.apply(args.map(_.value))
    }
    
    case class Sum(args : Expr[Int]*) extends Expr[Int]{
      val intVals = args.map(_.value)
      def recursiveSum(args1: Int*) : Int = {
        if (args1.length == 0) 0
        else args1.head + recursiveSum(args1.tail : _*)
      }
      
      override def value = recursiveSum(intVals:_*)
    }
    
    case class Product(args : Expr[Int]*) extends Expr[Int]{
      val intVals = args.map(_.value)
      def recursiveProduct(args1: Int*) : Int = {
        if (args1.length == 0) 1
        else args1.head * recursiveProduct(args1.tail : _*)
      }
      
      override def value = recursiveProduct(intVals:_*)
    }
    
  object Read extends Expr[Int]{
    private val scanner = new java.util.Scanner(System.in)
    def value : Int = scanner.nextInt()
    
  }
  
  
  object Rand {
    private val gen = new java.util.Random(42)
    def value = gen.nextInt()
  }
  
  
}