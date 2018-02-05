

object hw2test extends App{
  println(hw2.lcs("Mary had a little lamb", "Its fleece was white as snow"))
  println(hw2.subs("Body"))
  println(hw2.lcs("permission", "cruise missile"))
  val lst = hw2.onebits(13)
  println(lst.toString())
}