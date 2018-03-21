object problem2 extends App {
  
  def diffs(lst : List[Int]) = {
    val seed: (List[Int], Option[Int]) = (Nil, None)
    val foldResult = (lst :\ seed)((element, state) => 
        if (state._2 == None){
          //case for when the program starts and there is no Int stored
          (state._1, Option(element))
        }
        else {
          //when there is a previous int stored, append the list with the difference of the 
          //current and previousint and update the current int to be the previous and continue
          (List(element - state._2.get)++state._1 , Option(element))
        }
      )
    foldResult._1
  }
                        
  /*														v This List is the final answer
   * 												(List(-6, 5, -7), 9)	
   * 												/											\
   * 									(List(-6, 5), 2)						(9)
   * 									/           	 \ 
   * 							(List(-6), 7)				(2)
   * 							/				\
   * 				(Nil, 1)			(7)
   * 			/				\
   * (Nil, None)	(1)
   */
   println(diffs(List(1, 7, 2, 9)))
   
}
