import java.io._

import scala.util.parsing.combinator._


object AdditionalPracticeQuestionsPart1 extends App{
  //Write a function that takes a list and drops every second element,
  //e.g. (1, 2, 3, 4, 5) -> (1, 3, 5). Use fold.
  def lstDrop(lst : List[Int]) : List[Int] = {
        val ans =lst.foldLeft(List().asInstanceOf[List[Int]], 1) ((a,b) => if (a._2 == 1) (a._1 ++ List(b), a._2 * -1) else (a._1, a._2* -1))
        ans._1
    }
  
  //Write a function that takes a list and swaps adjacent elements,
  //e.g. (1, 2, 3, 4, 5) -> (2, 1, 4, 3, 5). Use fold.
  def lstSwap(lst : List[Int]) : List[Int] = {
        val ans =lst.foldLeft(List().asInstanceOf[List[Int]], 1) ((a,b) => if (a._2 == 1) (a._1 ++ List(b), a._2 * -1) else (a._1.dropRight(1)++ List(b) ++List(a._1.last), a._2* -1))
        ans._1
    }
  
    println(lstDrop(List(1,2,3,4,5)))
    println(lstSwap(List(1,2,3,4,5)))
    
  
}