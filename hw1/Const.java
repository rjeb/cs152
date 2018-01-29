
public class Const<T> implements Expr<T> {
	
	private T value;
	   
	public Const(T value)
	   {
	      this.value = value;
	   }

	public T value() { return value; }	

}
