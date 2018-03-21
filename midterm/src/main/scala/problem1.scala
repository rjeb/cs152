object problem1 extends App {
  def diffs(lst: List[Int]) = {
    def helperFun(lst1: List[Int], lst2: List[Int]) : List[Int] = {
      if (lst1.isEmpty || lst1.size == 1) lst2
      else {
        helperFun(lst1.tail, lst2++List(lst1.head - lst1.tail.head))
      }
    }
    helperFun(lst, List())
  }

  println(diffs(List(1,7,2,9)))
  println(diffs(List(3, 1, 4, 1, 5, 9, 2, 6)))
}
