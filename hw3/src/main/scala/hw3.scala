

object hw3 {
  
  def compose (op1:(Int) => Int, op2:(Int) => Int) : (Int => Int) = {
    def op3(arg1: Int) : Int = {
      op1(op2(arg1))
    }
    op3
  }
  
}