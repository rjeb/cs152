
import org.scalatest.FunSuite

class MyTestSuite extends FunSuite {
  
  test("Composing -11 and *2") {
    assert(hw3.compose(_ -11, _ * 2)(12) == 13)
  }
  
  test("Flipping args in /") {
    assert(hw3.flip(_ / _)(2, 10) == 5)
  }
  
  test("Second list longer") {
    assert(hw3.zip(List(1, 2, 3), List(4, 5, 6, 4), _ - _) == List(-3, -3, -3, 4))
  }
  
  test("Multiplying neighbors, even length") {
    assert(hw3.combineNeighbors(List(1, 2, 3, 4), _ * _) == List(2, 12))
  }
  
  test("Iterating + 2 five times") {
    assert(hw3.iterateN(1, _ + 2, 5) == List(1, 3, 5, 7, 9))
  }
  
  test("Iterating * 2 while less than 200") {
    assert(hw3.iterateWhile(1, _ * 2, _ < 200) == List(1, 2, 4, 8, 16, 32, 64, 128))
  }
  
  test("computing /2") {
    assert(hw3.iterateUntil(2, x => x / 2, (x, y) => math.abs(x - y) < .1) == List(2.0, 1.0, 0.5, 0.25, 0.125))
  }
  
  test("Reducing sum with default 10") {
    assert(hw3.reduceWithDefault(10, (1 to 100).toList, _ + _) == 5060)
  }
  
  test("Reducing difference with default 0") {
    assert(hw3.otherReduceWithDefault(0, (1 to 10).toList, _ - _) == -5)
  }
  
  test("Big and Even") {
    assert(hw3.both(x => x > 100, x => x % 2 == 0)(102))
    assert(!hw3.both(x => x > 100, x => x % 2 == 0)(101))
    assert(!hw3.both(x => x > 100, x => x % 2 == 0)(98))
  }
  
  test("Empty List") {
    assert(!hw3.any(List())(300))
  }
    
}