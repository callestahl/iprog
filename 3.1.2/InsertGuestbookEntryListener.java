/**
 * interface for notifying main program that the GUI wants to add a entry
 */
public interface InsertGuestbookEntryListener {
  /**
   * called when a new entry should be made
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
  );
}
