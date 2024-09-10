import java.sql.Timestamp;

/**
 * DataBaseEntry represents an entry in the guestbook. it contains
 * information about the entry such as the name, email, homepage, comment, and
 * the time of entry.
 */
public class DataBaseEntry {

  private int id;
  private String name;
  private String email;
  private String homepage;
  private String comment;
  private Timestamp time;

  /**
   * constructs a new DataBaseEntry
   *
   * @param id the id of the entry
   * @param name the name of the guest
   * @param email the email of the guest
   * @param homepage the homepage of the guest
   * @param comment the comment left by the guest
   * @param time the timestamp of the entry
   */
  public DataBaseEntry(
    int id,
    String name,
    String email,
    String homepage,
    String comment,
    Timestamp time
  ) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.homepage = homepage;
    this.comment = comment;
    this.time = time;
  }

  /**
   * get the id of the entry
   *
   * @return the id of the entry
   */
  public int getId() {
    return this.id;
  }

  /**
   * set the id of the entry
   *
   * @param id the id of the entry
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * get the name of the guest
   *
   * @return the name of the guest
   */
  public String getName() {
    return this.name;
  }

  /**
   * set the name of the guest
   *
   * @param name the name of the guest
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * get the email of the guest
   *
   * @return the email of the guest
   */
  public String getEmail() {
    return this.email;
  }

  /**
   * set the email of the guest
   *
   * @param email the email of the guest
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * get the homepage of the guest
   *
   * @return the homepage of the guest
   */
  public String getHomepage() {
    return this.homepage;
  }

  /**
   * set the homepage of the guest
   *
   * @param homepage the homepage of the guest
   */
  public void setHomepage(String homepage) {
    this.homepage = homepage;
  }

  /**
   * get the comment left by the guest
   *
   * @return the comment left by the guest
   */
  public String getComment() {
    return this.comment;
  }

  /**
   * set the comment left by the guest
   *
   * @param comment the comment left by the guest
   */
  public void setComment(String comment) {
    this.comment = comment;
  }

  /**
   * get the timestamp of when the entry was made
   *
   * @return the timestamp of when the entry was made
   */
  public Timestamp getTime() {
    return this.time;
  }

  /**
   * set the timestamp of when the entry was made
   *
   * @param time the timestamp of when the entry was made
   */
  public void setTime(Timestamp time) {
    this.time = time;
  }

  /**
   * returns a string representation of the DataBaseEntry object formatted as
   * HTML
   * @return a string representation of the DataBaseEntry object in HTML format
   */
  @Override
  public String toString() {
    return (
      "<html>" +
      " Id: " +
      Integer.toString(getId()) +
      ",<br>" +
      "Name: " +
      getName() +
      ",<br>" +
      "Email: " +
      getEmail() +
      ",<br>" +
      "Homepage: " +
      getHomepage() +
      ",<br>" +
      "Comment: " +
      getComment() +
      ",<br>" +
      "Time: " +
      getTime().toString() +
      "<br>" +
      "<br>" +
      "<br>" +
      "</html>"
    );
  }
}
