/**
 * T2 is a Runnable that prints "Tråd 2" every second until it is stopped.
 */
public class T2 implements Runnable {

  private volatile boolean shouldStop = false;

  /**
   * The run method is called when the Runnable is used to create a Thread 
   * and the thread is started.
   * It enters a loop where it prints "Tråd 2" and sleeps for 1 second.
   * The loop continues until the stop method is called on this T2 object.
   */
  @Override
  public void run() {
    while (!shouldStop) {
      System.out.println("Tråd 2");
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        return;
      }
    }
  }

  /**
   * The stop method sets a flag that causes the run method to exit its loop and
   * finish.
   * After this method is called, the T2 object will stop printing "Tråd 2" and
   * the thread will finish.
   */
  public void stop() {
    shouldStop = true;
  }

}
