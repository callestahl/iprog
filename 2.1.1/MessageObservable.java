public interface MessageObservable {
  void notifyObservers();
  void addObserver(MessageObserver observer);
}
