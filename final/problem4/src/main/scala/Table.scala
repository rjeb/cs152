class Table[K,V](entries: List[(K,V)] = List()) { // TODO: Type variance
  def get(k: K) = . . . // TODO: Implementation
  // TODO: The put method doesn't quite work. Make the appropriate
  // changes.
  def put(k: K, v: V): Table[K, V] = new Table((k, v) :: entries)
}
