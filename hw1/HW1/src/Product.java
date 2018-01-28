
public class Product extends Op<Integer>{
	
	@SafeVarargs public Product(Expr<Integer>... args)
	   {
		//passes the arguments with the Multiplication Function
		super(new Multiplication(), args);
	   }

}
