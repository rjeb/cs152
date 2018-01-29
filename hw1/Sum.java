import java.util.stream.*;

public class Sum extends Op<Integer>
{
   @SafeVarargs public Sum(Expr<Integer>... args)
   {
      super(a -> a.stream().reduce(Integer::sum).orElse(0), args);
   }
}