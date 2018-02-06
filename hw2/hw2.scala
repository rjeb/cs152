

object hw2 {
  def subs(s : String) : String = {
    //function that returns 2 ^ arg
    def twoTimes(int1:Int, int2:Int, index: Int) : Int = {
      if (index != int2){
        val x = int1 * 2
        twoTimes(x, int2 , index+1)
      }
      else{
        int1
      }
    }
    //function that returns a list of all binary representation of numbers <= than arg
    def binaryList(lst1 : List[List[Int]], int1 : Int) : List[List[Int]] = {
      if (int1 != 0){
        val lst2 = onebits(int1) :: lst1
        binaryList(lst2, int1 -1)
      }
      else{
        lst1
      }
      
    }
    
    //given a List of indecies and a string, returns corresponding subString
    def printSubString(lst1 :List[Int], str : String) : String = {
      if (lst1.length == 0){
        ""
      }
      else{
        str.charAt(lst1.head).toString() + (printSubString(lst1.tail, str))
      }
    }
    
    //prints SubString using a list of list of indecies until there are no more lists of indecies
    def printAll(lst1:List[List[Int]], str:String) : String = {
      if (lst1.length == 0){
        ""
      }
      else{
        printSubString(lst1.head, str) + "|" + printAll(lst1.tail, str)
      }
    }
    
    val permLength = twoTimes(1, s.length(), 0) - 1
    val indeciesList = binaryList(List(), permLength)
    
    
    printAll(indeciesList, s);
    
    //divide the problem into 3 cases: No head, with head, and emptySet case
    
  }
  
  def lcs(a : String, b : String) : String = {
    
    //returns whether any substring of str2 of size index2 is in str1 as a Boolean
    def commonChecker(str1 : String, str2 : String, index1 : Int, index2 : Int) : Boolean = {
      if (str2.isEmpty()){
        true
      }
      else if (str1.contains(str2.substring(index1, index1 + index2 -1))){
        true
      }
      else{
        if (index1+1 + index2 <= str2.length()){
        commonChecker(str1, str2, index1+1, index2)
        }
        else{
          false
        }
      }
    }
    
    //checks if largest case substring is shared. If not, check lower length substring recursively
    def commonChecker2(str1 : String, str2 : String, index1 : Int, index2: Int) : String = {
      if (str2.isEmpty()){
        ""
      }
      else if (str1.contains(str2.substring(index1, index2-1))){
        str2.substring(index1, index2-1)
      }
      else{
        if (commonChecker(str1, str2, index1, index2-2)){
          commonCheckerVerify(str1, str2, index1, index2-2)
        }
        else{
          commonChecker2(str1, str2, index1, index2-1)
        }
      }
    }
    //returns whether any substring of str2 of size index2 is in str1
    def commonCheckerVerify(str1 : String, str2 : String, index1 : Int, index2 : Int) : String = {
      if (str2.isEmpty()){
        ""
      }
      else if (str1.contains(str2.substring(index1, index1 + index2 -1))){
        str2.substring(index1, index1 + index2 - 1)
      }
      else{
        if (index1+1 + index2 <= str2.length()){
        commonCheckerVerify(str1, str2, index1+1, index2)
        }
        else{
          ""
        }
      }
    }
    
    if (a.length() >= b.length()){
      commonChecker2(a, b, 0, b.length())
    }
    else{
      commonChecker2(b, a, 0, a.length())
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
    
    //turns binary representation to indecy representation
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