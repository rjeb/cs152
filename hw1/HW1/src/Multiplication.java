import java.util.List;

public class Multiplication<T> implements Function<T>{

	Integer returnValue = 5;
	
	@Override
	public T apply(List<T> args) {
		returnValue = (Integer)args.get(0);
		for (int i = 1; i < args.size(); i++) {
			returnValue = returnValue * (Integer)args.get(i);
		}
		return (T)returnValue;
	}
	

}
