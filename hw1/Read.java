import java.util.Scanner;

public class Read implements Expr<Integer>{

	private static Scanner in = new  Scanner(System.in);

	@Override
	public Integer value() {
		return in.nextInt();
	}
	
}
