〈!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"〉
〈html xmlns='http://www.w3.org/1999/xhtml'  
  〈head  
    〈meta content='text/html; charset=utf-8' http-equiv=content-type〉 
    〈title Green Sheet〉 
    〈link href='styles.css' rel='stylesheet' type='text/css'〉 
  〉
  〈body
    〈h1 San Jose State University | CS 152 Section 3 | Spring 2018〉
    〈h2 Midterm Exam〉
    〈h3 Exam Rules〉
    〈ul
      〈li You may put any files that you like on your laptop, including the slides, the Scala API, and your and my homework and lab solutions.〉
      〈li You may NOT use the Internet for anything during the exam. 〉
      〈li You may NOT communicate with anyone other than the exam proctor.〉
      〈li All your exam work must be in a folder called 〈 midterm〉 in your repo.〉
      〈li Submit the requested files 〈 problem1.scala〉, 〈 problem2.scala〉, and so on, inside the 〈 src/main/scala〉 directory.〉
      〈li You 〈b must〉 run git commit every 10 minutes.〉
      〈li When the exam is over, run git push to push your repo.〉
      〈li The exam is 70 minutes long.〉
    〉
    〈h3 Exam Problems〉

    〈ol
      〈li Write a Scala function 〈 diffs〉 that receives a 〈 List[Int]〉 with at least two elements and returns a 〈 List[Int]〉 with the differences of adjacent elements. Use a recursive helper function. For example,
        〈pre
diffs(List(1, 7, 2, 9))
〉 returns
        〈pre
List(-6, 5, -7)
〉
        Submit a file 〈 problem1.scala〉
      〉
      〈li Repeat the preceding exercise, but now use a right fold. This is a bit tricky because the fold operation (which you need to design) only sees one new element at a time. Use as a seed value a tuple 〈 (〈var partial result list〉, 〈var previously seen value〉)〉 Your operator should take in the next list value, prepend the difference to the list and update the previously seen value. As another complexity, in the very first step, you can't yet do anything because you have nothing to subtract. Solve this by using an 〈 Option〉 for the previously seen value. Submit 〈 problem2.scala〉 with your function and a comment showing the fold diagram in ASCII art.〉
      〈li Given two lists of integer digits, produce a list of all combinations of the first and second. Use 〈 map〉/〈 flatMap〉. For example,
        〈pre
allCombinations(List(1, 2), List(3, 4, 5))〉 yields
        〈pre
List(13, 14, 15, 23, 24, 25)
〉〉
      〈li Change the expression parser so that it can handle Boolean expressions with 〈 ||〉 〈 &&〉 〈 !〉 instead of integers and arithmetic. Instead of 〈 wholeNumber〉, accept 〈 true〉 or 〈 false〉. 〈 &&〉 binds stronger than 〈 ||〉. For example, 〈 !true || !false && !!true〉 evalues to 〈 true〉.〉
      〈li Enhance SL1 so that it can handle 〈 ^〉 as a “raise to a power” operator. Raising to a power  binds more strongly than multiplication/division and is right-associative. For example, 4^2^3 = 4^(2^3) = 65536. For your convenience, here is
        〈pre
def intpow(a: Int, b: Int) = math.pow(a, b).asInstanceOf[Int]〉〉
    〉
  〉
〉
