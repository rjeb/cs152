

object hw5part1{
  abstract class Expr[T] {
    def value: T
  }
  
  
    case class Const[T](value : T) extends Expr[T]
    //case class Variable(name : String) extends Expr[String]
    
    class Op[T](fun : Seq[T] => T,  args : Expr[T]*) extends Expr[T] {
      override def value: T = fun.apply(args.map(_.value))
    }
    
    case class Sum(args : Expr[Int]*) extends Op[Int](_.sum, args:_*){
      
    }
    
    case class Product(args : Expr[Int]*) extends Op[Int](_.product, args: _*){

    }
    
  object Read extends Expr[Int]{
    private val scanner = new java.util.Scanner(System.in)
    def value : Int = scanner.nextInt()
    
  }
  
  
  object Rand extends Expr[Int]{
    private val gen = new java.util.Random(42)
    def value = gen.nextInt()
  }
  
  
}