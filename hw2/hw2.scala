

object hw2 {
  def subs(s : String) : String = {
    def subStringHelper(char1 : Char, str : String) : String = {
      if (str.length() > 1){
      char1 + str + "|" + subStringHelper(char1, str.tail) + "|" + subStringHelper(char1, str.head.toString())
      }
      else if (str.length() == 1){
        char1 + str
      }
      else{
        ""
      }
      
    }
    
    def subStringHelper2(str1 : String) : String = {
     if (str1.length() > 1){
       str1.head + "|" + str1.tail + "|" + subStringHelper2(str1.tail)
      } 
     else{
        ""
     }
    }
    subStringHelper2(s) + subStringHelper(s.head, s.tail);
  }
  
  def lcs(a : String, b : String) : String = {
    "Hello";
  }
  
  def onebits(n: Int) : List[Int] = {
    List(1, 2);
  }
}