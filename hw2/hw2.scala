

object hw2 {
  def subs(s : String) : String = {
    def subStrings(str : String) : String = {
      if (s.length() == 1){
        s
      }
      
      else{    
      s.head + " | " + subs(s.tail)
      }
    }
    if (s.length() == 0){
      ""
    }
    else{
      subStrings(s)  
    }
  }
  
  def lcs(a : String, b : String) : String = {
    "Hello";
  }
  
  def onebits(n: Int) : List[Int] = {
    List(1, 2);
  }
}