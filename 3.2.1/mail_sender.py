import sys
from email.mime.text import MIMEText
import smtplib


class MailSender:
    """
    a class to send emails
    """

    def __init__(self, host: str, port: int, sender_address: str, password: str, receiver_address: str, subject: str, message: str) -> None:
        """
        constructs the MailSender object

        Parameters:
        host : str
            the server host
        port : int
            the server port
        sender_address : str
            the senders email address
        password : str
            the senders password
        receiver_address : str
            the receivers email address
        subject : str
            the subject of the email
        message : str
            the message of the email
        """
        self.host = host
        self.port = port
        self.sender_address = sender_address
        self.password = password
        self.receiver_address = receiver_address
        self.subject = subject
        self.message = message

    def send_mail(self, use_ssl: bool):
        """
        sends an email

        Parameters:
        use_ssl : bool
            if True use SSL to connect to the server. otherwise use TLS
        """
        message = MIMEText(self.message)
        message['Subject'] = self.subject
        message['From'] = self.sender_address
        message['To'] = self.receiver_address

        server = None
        if use_ssl:
            server = smtplib.SMTP_SSL(self.host, self.port)
        else:
            server = smtplib.SMTP(self.host, self.port)
            server.starttls()
        server.login(self.sender_address, self.password)
        server.sendmail(self.sender_address,
                        self.receiver_address, message.as_string())


if __name__ == '__main__':
    """
    main entry point. parses command line arguments and sends an email
    """
    # handle command line arguments
    if len(sys.argv) != 8:
        print('Wrong number of arguments: <host> <port> <username> <password> <to> <subject> <message>')
        sys.exit(1)

    host = sys.argv[1]
    try:
        port = int(sys.argv[2])
    except ValueError:
        print('error parsing port number')
        sys.exit(1)
    sender_address = sys.argv[3]
    password = sys.argv[4]
    receiver_address = sys.argv[5]
    subject = sys.argv[6]
    message = sys.argv[7]

    # construct the MailSender object, try to send with TLS. if that fails try to send with SSL
    mail_sender = MailSender(host, port, sender_address,
                             password, receiver_address, subject, message)
    try:
        print('trying to send using TLS')
        mail_sender.send_mail(use_ssl=False)
    except Exception:
        try:
            print('trying to send using SSL')
            mail_sender.send_mail(use_ssl=True)
        except Exception as e:
            print('could not send email')
            print(e)
            sys.exit(1)
    print('email sent!')
