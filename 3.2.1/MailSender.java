

public class MailSender {
  private String host;
  private int port;
  private String senderAddress;
  private String password;
  private String receiverAddress;
  private String subject;
  private String message;

  public static void main(String[] args) {
    if (args.length != 7) {
      System.err.println(
        "Wrong number of arguments: <host> <port>" +
        "<användarnamn> <lösenord> <till> <ärende> <meddelande>"
      );
      System.exit(1);
    }

    MailSender mailSender = new MailSender();
    mailSender.host = args[0];
    try {
      mailSender.port = Integer.parseInt(args[1]);
    } catch (NumberFormatException e) {
      System.err.println(e.getMessage());
      System.exit(1);
    }
    mailSender.senderAddress = args[2];
    mailSender.password = args[3];
    mailSender.receiverAddress = args[4];
    mailSender.subject = args[5];
    mailSender.message = args[6];


  }
}
