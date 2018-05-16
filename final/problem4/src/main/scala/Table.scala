class Table[-K,+V](entries: List[(K,V)] = List()) { // TODO: Type variance
  def get(k: K) : Option[V] = None
  def put(pair : Pair[K, V]) = 1;
  // TODO: The put method doesn't quite work. Make the appropriate
}
