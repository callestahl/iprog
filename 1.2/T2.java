public class T2 implements Runnable {

  private boolean shouldStop = false;

  @Override
  public void run() {
    while(!shouldStop) {
      System.out.println("Tråd 2");
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        return;
      }
    }
  }

  public void stop() {
    shouldStop = true;
  }
  
}
