object problem2 extends App {
  
  def diffs(lst : List[Int]) = {
    val seed: (List[Int], Option[Int]) = (Nil, None)
    val foldResult = (lst :\ seed)((element, state) => 
        if (state._2 == None){
          (state._1, Option(element))
        }
        else {
          (List(element - state._2.get)++state._1 , Option(element))
        }
      )
    foldResult._1
  }
                
  /*									(List(-6, 5), 2)
   * 									/           	 \ 
   * 							(List(-6), 7)				(2)
   * 							/				\
   * 				(Nil, 1)			(7)
   * 			/				\
   * (Nil, None)	(1)
   */
   println(diffs(List(1, 7, 2, 9)))
   
}
