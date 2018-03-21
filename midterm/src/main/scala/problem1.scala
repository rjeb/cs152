object problem1 extends App {
  def diffs(lst: List[Int]) = {
    def helperFun(lst1: List[Int], lst2: List[Int]) : List[Int] = {
      //since we only want adjacent elements, when there is only one element left in a list there is nothing
      //left to calculate
      if (lst1.isEmpty || lst1.size == 1) lst2
      else {
        //appends answer list with (currentElement - nextElement) and recursively calls helperFun
        helperFun(lst1.tail, lst2++List(lst1.head - lst1.tail.head))
      }
    }
    helperFun(lst, List())
  }

  //println(diffs(List(1,7,2,9))) Additional test from index yeilds correct: List(-6, 5, -7)
  println(diffs(List(3, 1, 4, 1, 5, 9, 2, 6)))
}
