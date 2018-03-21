object problem2 extends App {
  def diffs(lst : List[Int]) = {
    val seed: (List[Int], Option[Int]) = (Nil, None)
    val foldResult = (lst :\ seed)((element, state) => 
      ...
      )
    foldResult._1
  }
  
  /*
     Your diagram here...

   */
   println(diffs(List(1, 7, 2, 9)))
}
