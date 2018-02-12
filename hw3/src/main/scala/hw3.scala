

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
  def zip (list1 : List[Int], list2 : List[Int], op : (Int, Int) => Int) : List[Int] = {
    
    def zipHelper (list1 : List[Int], list2 : List[Int], op : (Int, Int) => Int) : List[Int] = {
      if (list1.isEmpty && list2.isEmpty){
        Nil
      }
      else if (list1.isEmpty!=true && list2.isEmpty){
        list1.head::zipHelper(list1.tail, list2, op)
      }
      else if (list1.isEmpty && list2.isEmpty != true){
        list2.head::zipHelper(list1, list2.tail, op)
      }
      else /* if (list1.tail.head != Nil && list2.tail.head != Nil) */{
        op(list1.head, list2.head)::zipHelper(list1.tail, list2.tail, op)
      }
      
    }
    zipHelper(list1, list2, op)
  }
  
  def combineNeighbors(list1:List[Int], op: (Int, Int) => Int) : List[Int] = {
    if (list1.isEmpty){
      Nil
    }
    else if(list1.tail.isEmpty){
      list1.head::combineNeighbors(list1.tail, op)
    }
    else{
      op(list1.head, list1.tail.head)::combineNeighbors(list1.tail.tail, op)
    }
  }
  
  
  //list reverse functions from lecture 3 slide 7
  def append(a: List[Int], b: List[Int]): List[Int] = if (a.isEmpty) b else
    a.head :: append(a.tail, b)
    
  def reverse(a: List[Int]): List[Int] = if (a.isEmpty) a else
    append(reverse(a.tail), List(a.head))
}