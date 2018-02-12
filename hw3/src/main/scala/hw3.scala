

object hw3 {
  
  def compose (op1:(Int) => Int, op2:(Int) => Int) : (Int => Int) = {
    def op3(arg1: Int) : Int = {
      op1(op2(arg1))
    }
    op3
  }
  
  def flip(op1: (Int,Int) => Int) : (Int,Int) => Int = {
    def op2 (a: Int, b:Int) : Int = {
      op1(b,a)
    }
    op2
  }
  
  
}