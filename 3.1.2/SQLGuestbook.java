import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * SQLGuestbook is a guestbook application that interacts with a MySQL database.
 * it implements the InsertGuestbookEntryListener interface to handle the
 * insertion of new guestbook entries from the GUI
 */
public class SQLGuestbook implements InsertGuestbookEntryListener {

  private GUI gui;
  private Connection connection;

  private String computer = "atlas.dsv.su.se";
  private String username = "usr_24358927";
  private String password = "358927";
  private String db_name = "db_24358927";

  /**
   * the main method initializes the SQLGuestbook application. it sets up the
   * GUI, loads the db driver, connects to the db, creates the guestbook table
   * if it does not exist, and updates the GUI with all guestbook entries.
   *
   * @param args not used
   */
  @SuppressWarnings("deprecation")
  public static void main(String[] args) {
    SQLGuestbook guestbook = new SQLGuestbook();
    guestbook.gui = new GUI(guestbook);

    // load the driver
    try {
      Class.forName("com.mysql.jdbc.Driver").newInstance();
    } catch (
      IllegalAccessException | InstantiationException | ClassNotFoundException e
    ) {
      System.err.println(e.getMessage());
      System.exit(1);
    }

    // connect to the db
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

    // if the table doesn't exist, create it
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

    // initialize the GUI with the existing entries
    guestbook.gui.updateEntries(guestbook.getAllGuestbookEntries());
  }

  /**
   * inserts a new entry into the guestbook db and updates the GUI
   *
   * @param name the name of the guest
   * @param email the email of the guest
   * @param homepage the homepage of the guest
   * @param comment the comment left by the guest
   */
  public void insertGuestbookEntry(
    String name,
    String email,
    String homepage,
    String comment
  ) {
    String statementString =
      "INSERT INTO guestbook (name, email, homepage, comment) VALUES (?, ?, ?, ?)";
    // create a PreparedStatement and set the values after removing HTML
    try {
      PreparedStatement statement = connection.prepareStatement(
        statementString
      );
      statement.setString(1, removeHtml(name));
      statement.setString(2, removeHtml(email));
      statement.setString(3, removeHtml(homepage));
      statement.setString(4, removeHtml(comment));

      statement.executeUpdate();

      gui.updateEntries(getAllGuestbookEntries());
    } catch (SQLException e) {
      System.err.println(e.getMessage());
    }
  }

  /**
   * removes HTML tags from the input string and replaces them with "censur"
   *
   * @param input The input string to remove HTML from
   * @return A string with HTML tags replaced by the word "censur"
   */
  private String removeHtml(String input) {
    return input.replaceAll("<.*>", "censur");
  }

  /**
   * gets all entries from the db
   *
   * @return a list of DataBaseEntry objects representing all guestbook entries
   */
  public List<DataBaseEntry> getAllGuestbookEntries() {
    List<DataBaseEntry> entries = new ArrayList<>();
    // query to select all entries
    String query = "SELECT * FROM guestbook";
    // create statement and execute it
    try (
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery(query)
    ) {
      // convert the resultSet into a list of DataBaseEntry
      while (resultSet.next()) {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        String email = resultSet.getString("email");
        String homepage = resultSet.getString("homepage");
        String comment = resultSet.getString("comment");
        Timestamp time = resultSet.getTimestamp("time");
        DataBaseEntry entry = new DataBaseEntry(
          id,
          name,
          email,
          homepage,
          comment,
          time
        );
        entries.add(entry);
      }
    } catch (SQLException e) {
      System.err.println(e.getMessage());
    }
    return entries;
  }
}
