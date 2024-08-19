import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLGuestbook implements InsertGuestbookEntryListener {

  private GUI gui;
  private String computer = "atlas.dsv.su.se";
  private String username = "usr_24358927";
  private String password = "358927";
  private String db_name = "db_24358927";
  private Connection connection;

  @SuppressWarnings("deprecation")
  public static void main(String[] args) {
    SQLGuestbook guestbook = new SQLGuestbook();
    guestbook.gui = new GUI(guestbook);
    try {
      Class.forName("com.mysql.jdbc.Driver").newInstance();
    } catch (
      IllegalAccessException | InstantiationException | ClassNotFoundException e
    ) {
      System.err.println(e.getMessage());
      System.exit(1);
    }
    try {
      String url =
        "jdbc:mysql://" + guestbook.computer + "/" + guestbook.db_name;
      guestbook.connection =
        DriverManager.getConnection(
          url,
          guestbook.username,
          guestbook.password
        );
      System.out.println("Connection successful");
    } catch (SQLException e) {
      System.err.println(e.getMessage());
      System.exit(1);
    }

    String createTable =
      "CREATE TABLE IF NOT EXISTS guestbook (id INT AUTO_INCREMENT PRIMARY KEY," +
      " time TIMESTAMP DEFAULT CURRENT_TIMESTAMP, name VARCHAR(255) NOT NULL," +
      " email VARCHAR(255) NOT NULL, homepage VARCHAR(255) NOT NULL," +
      " comment VARCHAR(255) NOT NULL)";
    try {
      Statement statement = guestbook.connection.createStatement();
      statement.execute(createTable);
    } catch (SQLException e) {
      System.err.println(e.getMessage());
    }
  }

  public void insertGuestbookEntry(
    String name,
    String email,
    String homepage,
    String comment
  ) {
    String statementString =
      "INSERT INTO guestbook (name, email, homepage, comment) VALUES (?, ?, ?, ?)";
    try {
      PreparedStatement statement = connection.prepareStatement(
        statementString
      );
      statement.setString(1, removeHtml(name));
      statement.setString(2, removeHtml(email));
      statement.setString(3, removeHtml(homepage));
      statement.setString(4, removeHtml(comment));
      statement.executeUpdate();
    } catch (SQLException e) {
      System.err.println(e.getMessage());
    }
  }

  private String removeHtml(String input) {
    return input.replaceAll("<.*>", "censur");
  }
}
