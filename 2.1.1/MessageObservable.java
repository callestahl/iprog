/**
 * Interface for adding and notifying message observers
 */
public interface MessageObservable {
  /**
   * Notifies all registered observers of a change.
   */
  void notifyObservers();

  /**
   * Adds an observer to the list of observers.
   *
   * @param observer the observer to be added
   */
  void addObserver(MessageObserver observer);
}
