import java.util.List;

public interface Function<T>
{
   T apply(List<T> args);
}