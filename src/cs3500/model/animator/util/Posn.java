package cs3500.model.animator.util;

import java.util.Objects;

/**
 * Help create a Posn given two Integer values.
 */
public class Posn {
  private int x;
  private int y;

  /**
   * The constructor for this Posn.
   *
   * @param x The x coordinate of this Posn
   * @param y The y coordinate of this Posn
   */
  public Posn(int x, int y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Method to set the x value.
   *
   * @param x the x value
   */
  public void setX(int x) {
    this.x = x;
  }

  /**
   * Method to set the y value.
   *
   * @param y the y value
   */
  public void setY(int y) {
    this.y = y;
  }

  /**
   * Method to get the x value.
   *
   * @return the x value as an int
   */
  public int getX() {
    return this.x;
  }

  /**
   * Method to get the y value.
   *
   * @return the y value as an int
   */
  public int getY() {
    return this.y = y;
  }

  /**
   * Calculates the distance from this Posn to another Posn.
   *
   * @param b the other Posn that's not this Posn
   * @return the distance as a double
   */
  public double distance(Posn b) {
    return (Math.sqrt(Math.pow((this.x - b.x), 2) + Math.pow((this.y - b.y), 2)));
  }

  /**
   * Method that overrides the equals method.
   *
   * @param obj the other Object being compared to
   * @return a bool, True if obj equals this, false otherwise
   */
  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Posn)) {
      return false;
    } else {
      return (((Posn) obj).getX() == this.x && ((Posn) obj).getY() == this.y);
    }
  }

  /**
   * Method that overrides the hashCode method.
   *
   * @return hashCode
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.x, this.y);
  }
}
