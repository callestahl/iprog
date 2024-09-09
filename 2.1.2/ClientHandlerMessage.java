/**
 * represents a message sent by a client handler
 * contains the message content, its type, and the sender
 */
public class ClientHandlerMessage {

  private String message;
  private MessageType messageType;
  private ClientHandler sender;

  /**
   * constructs a new ClientHandlerMessage
   *
   * @param sender the client handler that sent the message
   * @param message the content of the message
   * @param messageType the type of the message
   */
  public ClientHandlerMessage(
    ClientHandler sender,
    String message,
    MessageType messageType
  ) {
    this.sender = sender;
    this.message = message;
    this.messageType = messageType;
  }

  /**
   * get the sender of the message
   *
   * @return the client handler that sent the message
   */
  public ClientHandler getSender() {
    return this.sender;
  }

  /**
   * set the sender of the message
   *
   * @param sender the client handler to set as the sender
   */
  public void setSender(ClientHandler sender) {
    this.sender = sender;
  }

  /**
   * get the content of the message
   *
   * @return the content of the message
   */
  public String getMessage() {
    return this.message;
  }

  /**
   * set the content of the message
   *
   * @param message the content to set for the message
   */
  public void setMessage(String message) {
    this.message = message;
  }

  /**
   * get the type of the message
   *
   * @return the type of the message
   */
  public MessageType getMessageType() {
    return this.messageType;
  }

  /**
   * sets the type of the message
   *
   * @param messageType the type to set for the message
   */
  public void setMessageType(MessageType messageType) {
    this.messageType = messageType;
  }
}
