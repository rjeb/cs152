import static org.junit.Assert.*;

import org.junit.Test;

public class ExprTest2 {

	public <T> Const<T> c(T v) { return new Const<T>(v); }
	
	
   @Test public void mixedExprTest()
   {
      Op<Integer> p = new Product(c(2), c(3));
      Expr<String> cat = new Op<String>(
    	         strings -> String.join("", strings),
    	         c("He"), c("l"), c("lo"), c(p.value().toString()));
      
      assertEquals("Hello6", cat.value());
   }
   
   @Test public void factorial10Test()
   {
	  Expr finalProduct = new Const(1);
	  for (int i = 2; i <= 10; i++) {
		  finalProduct = new Product(finalProduct, c(i));
	  }
      assertEquals(3628800, finalProduct.value());     
   }
   
   @Test public void multExpressionTest()
   {
	  Op<Integer> p = new Product(c(2), c(3), c(4), c(5), c(6), c(7), c(8), c(9), c(10));
      assertEquals(3628800, (Object)p.value());     
   }

}
