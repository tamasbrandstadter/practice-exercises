package holczer.udemy;

public interface Tree<T> {
  void insert(T data);
  void delete(T data);
  T min();
  T max();
  void traverse();
}
