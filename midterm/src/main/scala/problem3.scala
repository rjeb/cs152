object problem3 extends App {
  
  def allCombinations(as: List[Int], bs: List[Int]) = {
    as.flatMap(a => bs.map(b => (a*10 + b)))
  }
  
  println(allCombinations(List(1, 2), List(3, 4, 5)))
}
