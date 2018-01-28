public class Rand implements Expr<Integer>
{
   private static java.util.Random generator
      = new java.util.Random(42);   
   
   public Integer value()
   {
      return generator.nextInt();
   }
}