object problem3 extends App {
  
  def allCombinations(as: List[Int], bs: List[Int]) = {
    //multiplies the list of first digits by 10 and adds the second. This results in all combinations between
    //the two lists
    as.flatMap(a => bs.map(b => (a*10 + b)))
  }
  
  println(allCombinations(List(1, 2), List(3, 4, 5))) // prints the correct result of List(13, 14, 15, 23, 24, 25)
}
