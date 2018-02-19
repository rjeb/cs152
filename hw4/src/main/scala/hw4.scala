import scala.util.matching._

object hw4 extends App{
  //Q1
  def firstMatch(str: String, lst1: List[Regex]) : String = {
		if (lst1.isEmpty){
			null
		}
		else {
			lst1.head.findPrefixOf(str).getOrElse(firstMatch(str, lst1.tail));
		}
	}
  def tokens(input: String, list1 : List[Regex], list2 : List[Regex]) : (List[String], Int) = {
    
    def tokensHelper(input : String, patterns : List[Regex]) : List[String] = {
  	  if (input.length == 0) Nil else {
  		  val m = firstMatch(input, patterns)
  		  if (m == null) Nil else m :: tokensHelper(input.substring(m.length), patterns)	
  	  }
    }
    
    def firstError(str: String, lst1: List[Regex], lst2: List[Regex], index: Int) : Int = {
      if (str.isEmpty()){
			  -1
		  }
		  else {
		    if (firstMatch(str.head.toString(), lst1) == null && firstMatch(str.head.toString(), lst2) == null){
		      return index;
		    }
		    else{
		      firstError(str.tail, lst1, lst2, index+1)
		    }
		  }
    }
    
    (tokensHelper(input, list1), firstError(input, list1, list2, 0))
  }
  
  
  //Q2
  val map1 = Map("2" -> "ABC", "3" -> "DEF", "4" -> "GHI", "5" -> "JKL", "6" -> "MNO", "7" -> "PRS", "8" -> "TUV", "9" -> "WXY");
  
  val characters = (s: String) => s.toList.map("" +_);
  
  val letters ={
    map1.map(e => (e._1, characters(e._2)))
  }
  
  val cats = (s: List[String], t: List[String]) => {
    t.flatMap(y => s.map(x => x + y))
  }
  
  val wordsForDigits = (digits: String) => {
    val lets = digits.map(c => letters(c.toString))
    println(lets)
    lets.reduceLeft(cats(_,_))
  }
  
  println(cats(letters("2"), letters("3")).toSet);
  println(wordsForDigits("23"))
  
  
}