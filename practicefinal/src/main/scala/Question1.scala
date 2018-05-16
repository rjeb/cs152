
object Question1 {
  def stringify1[T](vals : List[T], f : T => String) : String = {
    if (vals.isEmpty){
      ""
    }
    else if (vals.tail.isEmpty){
      f.apply(vals.head) + stringify1(vals.tail, f)
    }
    else{
      f.apply(vals.head) + ", " + stringify1(vals.tail, f);
    }
  }
  def stringify2[T](vals : List[T], f : T => String) : String = {
    if (vals.isEmpty){
        ""
      }
    else{
      vals.foldLeft("")((A,B) => if (A.length() == 0) {A + f(B)} else {A + ", " + f(B)})
    }
  }    
  def stringify3[T](vals : List[T], f : T => String) : String = vals.map(f).mkString(", ")
  
  def main(args : Array[String]) : Unit = {
    println(stringify1(List(1, 2, 3), ((x : Int) => "$" + x)))
    println(stringify2(List(1, 2, 3), ((x : Int) => "$" + x)))
    println(stringify3(List(1, 2, 3), ((x : Int) => "$" + x)))    
  }  
}