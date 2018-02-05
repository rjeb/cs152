

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
    //divide the problem into 3 cases: No head, with head, and emptySet case
    subStringHelper2(s) + subStringHelper(s.head, s.tail) + "|";
  }
  
  def lcs(a : String, b : String) : String = {
    def commonChecker(str1 : String, str2 : String) : String = {
      if (str2.isEmpty()){
        ""
      }
      else if (str1.contains(str2)){
        str2
      }
      else{
        commonChecker(str1, str2.tail)
      }
    }
    if (a.length() >= b.length()){
      commonChecker(a, b)
    }
    else{
      commonChecker(b, a)
    }
  }
  
  def onebits(n: Int) : List[Int] = {
    List(1, 2);
  }
}