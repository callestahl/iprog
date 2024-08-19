import java.sql.Timestamp;

public class DataBaseEntry {

  private int id;
  private String name;
  private String email;
  private String homepage;
  private String comment;
  private Timestamp time;

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

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getHomepage() {
    return this.homepage;
  }

  public void setHomepage(String homepage) {
    this.homepage = homepage;
  }

  public String getComment() {
    return this.comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public Timestamp getTime() {
    return this.time;
  }

  public void setTime(Timestamp time) {
    this.time = time;
  }

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
