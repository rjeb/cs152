import java.util.List;
import java.io.*;

public class Multiplication<Integer> implements Function<Integer>{

	Integer returnValue;

	@Override
	public Integer apply(List<Integer> args) {
		returnValue = args.get(0);
		return returnValue;
	}
	

}
