import java.io.*;
import org.junit.*;
import static org.junit.Assert.*;

public class ExprTest
{
   public <T> Const<T> c(T v) { return new Const<T>(v); }
   
   @Test public void simpleExpr()
   {
      assertEquals(42, (Object) new Product(c(6), c(7)).value());     
   }

   @Test public void inputExpr()
   {
      System.setIn(new ByteArrayInputStream(
            "13".getBytes(java.nio.charset.StandardCharsets.UTF_8)));
      System.setOut(new PrintStream(new ByteArrayOutputStream()));
      assertEquals(25, (Object) new Sum(c(12), new Read()).value()); 
   }

   @Test public void nestedExpr()
   {
      Op<Integer> p = new Product(c(2), c(3));
      assertEquals(7, (Object) new Sum(c(1), p).value());
   }

   @Test public void randomExpr()
   {
      Expr<Integer> r = new Rand();
      assertEquals(1029516620, (Object) new Sum(r, new Product(r, r)).value());
   }

   @Test public void opExpr()
   {
      Expr<String> cat = new Op<String>(
         strings -> String.join("", strings),
         c("He"), c("l"), c("lo"));
      assertEquals("Hello", cat.value());
   }
}