

object Part1 extends App{
  
  def altSum1(lst : List[Int]) : Int = {
    def helperFun(lst1 : List[Int], sum : Int, sign: Int) : Int = {
      if (lst1.isEmpty){
        sum
      }
      else{
        helperFun(lst1.tail, sum + lst1.head * sign, sign * -1)
      }
    }
    helperFun(lst, 0, 1)
  }
  /*
  def altSum1(lst : List[Int]) : Int = {
    def recursiveSum(lst1 : List[Int]) : Int = {
      if (lst1.isEmpty) 0
      else{
        if (lst1.size%2 == 0){
          recursiveSum(lst1.dropRight(1)) - lst1.last
        }
        else{
          recursiveSum(lst1.dropRight(1)) + lst1.last
        }
      }
    }
    
    recursiveSum(lst)
  }
  
  def altSum2(lst : List[Int]) : Int = {
    val lst1 = lst.grouped(2).toList
    lst1.foldLeft(0)((A,B) => if(B.size == 2){A + B.head - B.last} else A + B.head)
  }
  */
  
/*
 *		next  next state
 * 		/        \
 * 	next state  2
 * 		/  \
 * state  1
 * 
 * 
 * 
 * 		(-2, 1)
 * 		/			\	
 * 	(1,-1)   3
 * 	/     \ 
 * (0, 1)  1 
 * 
 *  
 */
  
  def altSum2(lst: List[Int]) : Int = {
    val sum = ((0,1) /: lst)((s, e) => (s._1 + e * s._2, s._2 * -1))
    val ans = sum._1
    ans
  }
  
  def allDifferences (lst1 : List[Int], lst2 : List[Int]) : List[Int] = {
    lst1.flatMap(a => lst2.map(b => (a - b)))
  }
  
  
  
  //println(allDifferences(List(1,5), List(2,3,4)))
  println(altSum2(List(5,4,3,2,1, 5)))
}