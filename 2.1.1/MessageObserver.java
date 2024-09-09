/**
 * Interface for observing messages.
 */
public interface MessageObserver {
  /**
   * Called when a message is sent.
   *
   * @param message the message that was sent
   */
  void messageSent(String message);
}
