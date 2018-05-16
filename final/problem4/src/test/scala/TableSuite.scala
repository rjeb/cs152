import org.scalatest.FunSuite

class TableSuite extends FunSuite {
  class Person(val name : String) {
    override def toString = getClass.getName + "[name=" + name + "]"
  }

  class Student(name : String, val id : Int) extends Person(name) {
    override def toString = super.toString + "[id=" + id + "]"
  }

  test("Basic put and get") {
    val t1 = new Table[String, Int]
    val t2 = t1.put("Harry", 42)
    assert(t2.get("Harry") == Some(42))
  }

  test("Variance in values") {

    def wantsPersonTable(t: Table[String, Person]): Option[String] = t.get("Harry").map(_.name)

    val t1 = new Table[String, Student]
    val t2 = t1.put("Harry", new Student("Harry Lee", 42))
    assert(wantsPersonTable(t2) == Option("Harry Lee"))
  }

  test("Variance in keys") {
    def wantsStringTable(t: Table[String, Person]): Option[String] = t.get("Harry").map(_.name)

    val t1 = new Table[Any, Person]
    val t2 = t1.put("Harry", new Person("Harry Lee"))
    assert(wantsStringTable(t2) == Option("Harry Lee"))
  }
}
