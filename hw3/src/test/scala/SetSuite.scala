
import org.scalatest.FunSuite

class SetSuite extends FunSuite {

  test("An empty Set should have size 0") {
    assert(Set.empty.size == 0)
  }

  test("Invoking head on an empty Set should produce NoSuchElementException") {
    assertThrows[NoSuchElementException] {
      Set.empty.head
    }
  }
  
  test("Composing +1 and *2") {
    assert(hw3.compose(_ + 1, _ * 2)(3) == 7)
  }
  
  
}