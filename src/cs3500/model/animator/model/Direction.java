package cs3500.model.animator.model;

import java.awt.Color;
import java.util.Objects;

import cs3500.model.animator.util.Posn;

/**
 * Class to represent all the Directions that can be given to a Shape throughout an Animation.
 * Includes start and end: position, width, height, color, and time.
 */
public class Direction {
  private Posn startPosition;
  private Posn finalPosition;
  private int startWidth;
  private int finalWidth;
  private int startHeight;
  private int finalHeight;
  private Color startColor;
  private Color finalColor;
  private double startTime;
  private double endTime;

  /**
   * Constructor for the Direction class.
   *
   * @param startPosition the position at which the Shape starts
   * @param finalPosition the position at which the Shape will be next
   * @param startWidth    the width that the Shape starts as
   * @param finalWidth    the width that the Shape will be
   * @param startHeight   the height that the Shape starts as
   * @param finalHeight   the height that the Shape will be
   * @param startColor    the initial color of the Shape
   * @param finalColor    the next color of the Shape
   * @param startTime     the time at which the Animation for a given Direction starts
   * @param endTime       the time at which the Animation for a given Direction ends.
   * @throws IllegalArgumentException if the given position or color is null
   * @throws IllegalArgumentException if the given width or height is less than or equal to 0
   * @throws IllegalArgumentException if the given startTime or endTime is less than 0 or the
   *                                  startTime is greater than endTime
   */
  public Direction(Posn startPosition, Posn finalPosition, int startWidth, int finalWidth,
                   int startHeight, int finalHeight, Color startColor, Color finalColor,
                   double startTime, double endTime) throws IllegalArgumentException {
    if (startPosition == null || finalPosition == null) {
      throw new IllegalArgumentException("Position can't be null");
    } else if (startHeight <= 0 || startWidth <= 0 || finalWidth <= 0 || finalHeight <= 0) {
      throw new IllegalArgumentException("Invalid Scaling.");
    } else if (startColor == null || finalColor == null) {
      throw new IllegalArgumentException("Invalid Color - Cannot be null");
    } else if (startTime < 0 || endTime < 0) {
      throw new IllegalArgumentException("Invalid Start/End Time- Negative time");
    } else if (startTime > endTime) {
      throw new IllegalArgumentException("Invalid Start/End Time- " +
              "Start cannot be greater than End");
    }

    this.startPosition = startPosition;
    this.finalPosition = finalPosition;
    this.startWidth = startWidth;
    this.finalWidth = finalWidth;
    this.startHeight = startHeight;
    this.finalHeight = finalHeight;
    this.startColor = startColor;
    this.finalColor = finalColor;
    this.startTime = startTime;
    this.endTime = endTime;
  }

  /**
   * Getter method the initial position of the Direction.
   *
   * @return the starting position as a Posn
   */
  public Posn getStartPosition() {
    Posn copyStartPosition = new Posn(this.startPosition.getX(), this.startPosition.getY());
    return copyStartPosition;
  }

  /**
   * Getter method the next position of the Direction.
   *
   * @return the next position as a Posn
   */
  public Posn getFinalPosition() {
    Posn copyFinalPosition = new Posn(this.finalPosition.getX(), this.finalPosition.getY());
    return copyFinalPosition;
  }

  /**
   * Getter method for the initial width of the Direction.
   *
   * @return the width as an int
   */
  public int getStartWidth() {
    return this.startWidth;
  }

  /**
   * Getter method for the next width of the Direction.
   *
   * @return the width as an int
   */
  public int getFinalWidth() {
    return this.finalWidth;
  }

  /**
   * Getter method for initial width of the Direction.
   *
   * @return the width as an int
   */
  public int getStartHeight() {
    return this.startHeight;
  }

  /**
   * Getter method for the next height of the Direction.
   *
   * @return the height as an int
   */
  public int getFinalHeight() {
    return this.finalHeight;
  }

  /**
   * Gets the next Color of the Direction.
   *
   * @return the next Color as a Color
   */
  public Color getStartColor() {
    Color copyStartColor = new Color(this.startColor.getRGB());
    return copyStartColor;
  }

  /**
   * Gets the next Color of the Direction.
   *
   * @return the next Color as a Color
   */
  public Color getFinalColor() {
    Color copyFinalColor = new Color(this.finalColor.getRGB());
    return copyFinalColor;
  }

  /**
   * Getter method for the start time of the Direction.
   *
   * @return the start time as an int
   */
  public double getStartTime() {
    return this.startTime;
  }

  /**
   * Getter method for the end time of the Direction.
   *
   * @return the end time as an int
   */
  public double getEndTime() {
    return this.endTime;
  }

  /**
   * Getter method for the Direction itself. Creates a copy of a given direction and returns the
   * copy of it.
   *
   * @return copy of a Direction
   */
  public Direction getCopyDirection() {
    Posn copyStartPosition = new Posn(this.startPosition.getX(), this.startPosition.getY());
    Posn copyFinalPosition = new Posn(this.finalPosition.getX(), this.finalPosition.getY());
    int copyStartWidth = this.startWidth;
    int copyFinalWidth = this.finalWidth;
    int copyStartHeight = this.startHeight;
    int copyFinalHeight = this.finalHeight;
    Color copyStartColor = new Color(this.startColor.getRGB());
    Color copyFinalColor = new Color(this.finalColor.getRGB());
    double copyStartTime = this.startTime;
    double copyEndTime = this.endTime;

    Direction copyDirection = new Direction(copyStartPosition, copyFinalPosition, copyStartWidth,
            copyFinalWidth, copyStartHeight, copyFinalHeight, copyStartColor, copyFinalColor,
            copyStartTime, copyEndTime);

    return copyDirection;
  }

  /**
   * Returns as a String representing the Direction.
   *
   * @return direction as a String
   */
  @Override
  public String toString() {
    StringBuilder str = new StringBuilder();
    str.append(startTime);
    str.append(" ");
    str.append(startPosition.getX());
    str.append(" ");
    str.append(startPosition.getY());
    str.append(" ");
    str.append(startWidth);
    str.append(" ");
    str.append(startHeight);
    str.append(" ");
    str.append(startColor.getRed());
    str.append(" ");
    str.append(startColor.getGreen());
    str.append(" ");
    str.append(startColor.getBlue());
    str.append(" ");
    str.append(endTime);
    str.append(" ");
    str.append(finalPosition.getX());
    str.append(" ");
    str.append(finalPosition.getY());
    str.append(" ");
    str.append(finalWidth);
    str.append(" ");
    str.append(finalHeight);
    str.append(" ");
    str.append(finalColor.getRed());
    str.append(" ");
    str.append(finalColor.getGreen());
    str.append(" ");
    str.append(finalColor.getBlue());
    str.append("\n");
    return str.toString();
  }

  /**
   * Added since Homework 6. To Override the built-in Equals function to determine Direction
   * equality.
   *
   * @param obj The Object that you compare the current Direction to
   * @return True or False depending on equality
   */
  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof Direction)) {
      return false;
    }
    Direction o = (Direction) obj;
    return (o.getStartTime() == this.startTime
            && o.getEndTime() == this.endTime
            && o.getStartColor() == this.startColor
            && o.getFinalColor() == this.finalColor
            && o.getStartPosition() == this.startPosition
            && o.getFinalPosition() == this.finalPosition
            && o.getStartHeight() == this.startHeight
            && o.getFinalHeight() == this.finalHeight
            && o.getStartWidth() == this.startWidth
            && o.getFinalWidth() == this.finalWidth);
  }

  /**
   * Method that overrides the hashCode method.
   *
   * @return hashCode
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.startTime, this.endTime, this.startColor, this.finalColor,
            this.startPosition, this.finalPosition, this.startHeight, this.finalHeight,
            this.startWidth, this.finalWidth);
  }
}