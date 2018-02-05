

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
       str1.head + "|" + subStringHelper(str1.tail.head, str1.tail.tail) + "|" + subStringHelper2(str1.tail)
      } 
     else if (str1.length() == 1){
       str1.head.toString()
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
    //converts an int argument 'x' into its Binary form returned as a list
    def convertBinary(x : Int, list1 : List[Int]) : List[Int] = {
      if (x%2 != 0){
        val list2 = 1 :: list1
        if (x/2 == 0){
          list2
        }
        else{
          convertBinary(x/2, list2)
        }
      }
      else{
        val list2 = 0 :: list1
        if (x/2 == 0){
          list2
        }
        else{
          convertBinary(x/2, list2)
        }
      }
    }
    //reverse functions were used from lecture 3 slides
    def append(a: List[Int], b: List[Int]): List[Int] = {
        if (a.isEmpty) b else
        a.head :: append(a.tail, b)
    }
    def reverse(a: List[Int]): List[Int] = {
      if (a.isEmpty) a else
      append(reverse(a.tail), List(a.head))
    }
    
    def convertIndecies(list1 : List[Int], list2 : List[Int], index : Int) : List[Int] = {
      if (list2.tail == Nil){
        if (list2.head == 1){
          val list3 = index :: list1;
          list3;
        }
        else{
          list1
        }
      }
      else{
        if (list2.head == 1){
          val list3 = index :: list1;
          convertIndecies(list3, list2.tail, index+1)
        }
        else{
          convertIndecies(list1, list2.tail, index+1)
        }
      }
      
    }
    val emptyList = List();
    val binaryForm = convertBinary(n, emptyList)
    val indecies = reverse(convertIndecies(emptyList, reverse(binaryForm), 0))
    indecies
  }
}