

object hw4 extends App{
  val map1 = Map("2" -> "ABC", "3" -> "DEF", "4" -> "GHI", "5" -> "JKL", "6" -> "MNO", "7" -> "PRS", "8" -> "TUV", "9" -> "WXY");
  val characters = (s: String) => s.toList.map("" +_);
  val letters = map1.map(e => (e._1, characters(e._2)));
  println(letters("3").flatMap(y => letters("2").map(x => x + y)));
  
}