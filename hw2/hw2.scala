

object hw2 {
  def subs(s : String) : String = {
    def subStrings(str : String) : String = {
      str.head + "|" + str.head + str.tail.tail + "|" + subs(str.tail)
    }
    if (s.length() == 0){
     "" + "|"
    }
    if (s.length() == 1){
      s
    }
    else{
      s + "|" + subStrings(s)  
    }
  }
  
  def lcs(a : String, b : String) : String = {
    "Hello";
  }
  
  def onebits(n: Int) : List[Int] = {
    List(1, 2);
  }
}