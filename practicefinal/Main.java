interface Function0<R> { R invoke(); }
interface Function1<A1, R> { R invoke(A1 a1); }
interface Function2<A1, A2, R> { R invoke(A1 a1, A2 a2); }
interface Function3<A1, A2, A3, R> { R invoke(A1 a1, A2 a2, A3 a3); }
public class Main {
  public static void main(String[] args) { System.out.println(main()); }
  public static Integer main() {
    final Function2<Function1<Integer, Integer>, Integer, Integer> twice = 
      new Function2<Function1<Integer, Integer>, Integer, Integer>() { 
        public Integer invoke(Function1<Integer, Integer> f, Integer a) {
          return f.invoke(f.invoke(a));
        }
      };
    final Integer n = 3;
    final Function1<Integer, Integer> mul_by_n = 
      new Function1<Integer, Integer>() { public Integer invoke(Integer x) {
        return n * x;
      }
    };
    return twice.invoke(mul_by_n, 2);
  }
}
