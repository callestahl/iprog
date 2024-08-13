public class ClientHandlerMessage {

  private String message;
  private MessageType messageType;
  private ClientHandler sender;

  public ClientHandlerMessage(
    ClientHandler sender,
    String message,
    MessageType messageType
  ) {
    this.sender = sender;
    this.message = message;
    this.messageType = messageType;
  }

  public ClientHandler getSender() {
    return this.sender;
  }

  public void setSender(ClientHandler sender) {
    this.sender = sender;
  }

  public String getMessage() {
    return this.message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public MessageType getMessageType() {
    return this.messageType;
  }

  public void setMessageType(MessageType messageType) {
    this.messageType = messageType;
  }
}
