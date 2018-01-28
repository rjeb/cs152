import java.util.ArrayList;
import java.util.List;

public class Op<T> implements Expr<T>{

	T returnValue;
	List listArgs;
	
	@SafeVarargs public Op(Function<T> fun, Expr<T>... args){
		listArgs = new ArrayList();
		for (int i = 0; i < args.length; i++) {
			//puts all of the Expr into a List and passes it through the function argument's apply method
			System.out.println(args.length);
			listArgs.add(args[i]);
			returnValue = (T)fun.apply(listArgs);
		}
	}

	@Override
	public T value() {
		return returnValue;
	}
	
	public boolean equals(Expr<T> that) {
		return (this.value().equals(that.value()));
	}
}
