class Table[-K,+V](entries: List[(K,V)] = List()) { // TODO: Type variance
  def sum(k: K, v : V) = 1;
  def put(k : K,  v: V) = new Table((k, v) :: entries)
  def get(k: K) : Option[V] = None
  // TODO: The put method doesn't quite work. Make the appropriate
}
