public interface MessageObservable {
  void notifyObservers(ClientHandlerMessage message);
  void addObserver(MessageObserver observer);
}
