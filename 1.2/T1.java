/**
 * T1 is a Thread that prints "Tråd 1" every second until it is interrupted.
 */
public class T1 extends Thread {

  /**
   * The run method is called when the thread is started.
   * It enters a loop where it prints "Tråd 1" and sleeps for 1 second.
   * The loop continues until the thread is interrupted.
   */
  @Override
  public void run() {
    while (!Thread.currentThread().isInterrupted()) {
      System.out.println("Tråd 1");
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        return;
      }
    }
  }

}