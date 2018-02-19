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
    lets.reduceLeft(cats(_,_)).filter(words.contains(_))
  }
  
  val words = io.Source.fromURL("http://horstmann.com/sjsu/spring2018/cs152/words").
    getLines.filter(w => Character.isLowerCase(w(0)) && w.length > 1).
    map(_.toUpperCase).toSet + "SCALA"
    
  val wordsForDigitsSequence = (seq: List[String]) =>
    seq.map(e => wordsForDigits(e)).reduceLeft(catsSpaces)
  
  val catsSpaces = (s: List[String], t: List[String]) => {
    t.flatMap(y => s.map(x => x + " " + y))
  }
  
  val grow1 = (c: String, substringLists: List[List[String]]) => substringLists.map(s => c::s)
  
  val grow2 = (c: String, substringLists: List[List[String]]) => substringLists.map(s => c+s.head::s.tail)
  
  val grow = (c: String, a: List[List[String]]) => grow1(c, a) ++ grow2(c,a) 
  
  val substrings = (s: String) => {
    val characters = (s: String) => {
      s.toList
    }
    val chars = characters(s.substring(0, s.length-1))
    chars.foldRight(List(List(s.last.toString())))((b,a) => grow(b.toString(),a))
  }
  
  val phoneMnemonics = (digits: String) => {
    substrings(digits).map(wordsForDigitsSequence).flatten
  }
  
  
  
  println(substrings("2728"))
  println(phoneMnemonics("7225247386"))
  
}