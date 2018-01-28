import java.util.ArrayList;
import java.util.List;

public class Op<T> implements Expr<T>{

	T returnValue;
	List listArgs;
	Function<T> function;
	Expr<T>[] arguments; 
	
	@SafeVarargs public Op(Function<T> fun, Expr<T>... args){
		this.function = fun;
		this.arguments = args;
	}

	@Override
	public T value() {
		listArgs = new ArrayList<T>();
		for (int i = 0; i < arguments.length; i++) {
			//puts all of the Expr into a List and passes it through the function argument's apply method
			listArgs.add(arguments[i].value());
		}
		returnValue = (T)function.apply(listArgs);
		return returnValue;
	}
	
	public boolean equals(Expr<T> that) {
		return (this.value().equals(that.value()));
	}
}
