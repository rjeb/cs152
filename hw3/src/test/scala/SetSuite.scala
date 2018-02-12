
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
  
  test("Flipping args in -") {
    assert(hw3.flip(_ - _)(3, 4) == 1)
    assert(hw3.flip(_ - _)(4, 3) == -1)
  }
  
  test("First list longer") {
    assert(hw3.zip(List(1, 2, 3, 4), List(4, 5, 6), _ + _) == List(5, 7, 9, 4))
  }
  
  test("Multiplying neighbors, odd length") {
    assert(hw3.combineNeighbors(List(1, 2, 3, 4, 5), _ * _) == List(2, 12, 5))
  }
  
  test("Iterating * 2 five times") {
    assert(hw3.iterateN(1, _ * 2, 5) == List(1, 2, 4, 8, 16))
  }
  
  test("Iterating + 1 while less than 10") {
    assert(hw3.iterateWhile(0, _ + 1, _ < 10) == List(0, 1, 2, 3, 4, 5, 6, 7, 8, 9))
  }
  
  test("computing sqrt(2)") {
    assert(hw3.iterateUntil(2, x => (x + 2 / x) / 2, (x, y) => math.abs(x - y) < 1E-8) == List(2.0, 1.5, 1.4166666666666665, 1.4142156862745097, 1.4142135623746899))
  }
  /*
  test("Reducing sum with default 0") {
    assert(hw3.reduceWithDefault(0, (1 to 100).toList, _ + _) == 5050)
  }
  
  test("Reducing difference with default -1") {
    assert(hw3.otherReduceWithDefault(-1, (1 to 5).toList, _ - _) == 4)
  }
  
  test("Small and odd") {
    assert(hw3.both(x => x < 10, x => x % 2 == 1)(3))
    assert(!hw3.both(x => x < 10, x => x % 2 == 1)(4))
    assert(!hw3.both(x => x < 10, x => x % 2 == 1)(11))
  }
  
  test("Small, odd, or really large") {
    assert(!hw3.any(List(x => x < 10, x => x % 2 == 1, x => x > 1000000))(300))
  }
  
  */
  
}