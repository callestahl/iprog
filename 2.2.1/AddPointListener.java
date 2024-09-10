import java.awt.*;

/**
 * interface for notifying listener that a point has been added
 */
public interface AddPointListener {
  /**
   * this method is called when a new point is added
   *
   * @param point the Point object that was added
   */
  public void pointAdded(Point point);
}
