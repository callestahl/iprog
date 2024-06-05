/**
 * This is the main class for the application.
 */
public class Main {
  /**
   * The main method for the application.
   * It starts two threads, T1 and T2, and stops them after a delay.
   *
   * @param args command-line arguments for the application
   */
  public static void main(String[] args) {
    T1 t1 = new T1();
    t1.start();
    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    T2 t2 = new T2();
    Thread thread2 = new Thread(t2);
    thread2.start();
    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    t1.interrupt();

    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    t2.stop();

  }
}
