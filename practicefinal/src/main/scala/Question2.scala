
object Question2 {
  class SL3Type
  case class SL3Int() extends SL3Type
  case class SL3Bool() extends SL3Type
  case class SL3Fun(paramTypes : List[SL3Type], returnType : SL3Type) extends SL3Type

  def stringify[T](vals : List[T], f : T => String) : String = vals.map(f).mkString(", ")

  def sl3typeToJavaType(type3 : SL3Type) : String = 
  type3 match {    
    case a : SL3Int => "Integer"
    case b : SL3Bool => "Boolean"
    case c : SL3Fun => 
      if (c.paramTypes.length > 1){
        "Function2" + "<" + stringify(c.paramTypes, sl3typeToJavaType) + ", " + sl3typeToJavaType(c.returnType) + ">"
      }
      else{
      "Function1" + "<" + stringify(c.paramTypes, sl3typeToJavaType) + ", " + sl3typeToJavaType(c.returnType) + ">"
      } 
  }
 
  def main(args : Array[String]) : Unit = {
    println(sl3typeToJavaType(SL3Int()))
    println(sl3typeToJavaType(SL3Fun(List(SL3Int()), SL3Bool())))
    println(sl3typeToJavaType(SL3Fun(List(SL3Fun(List(SL3Int()), SL3Bool())),SL3Int())))
  }  
}