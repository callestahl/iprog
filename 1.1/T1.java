public class T1 extends Thread  {

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