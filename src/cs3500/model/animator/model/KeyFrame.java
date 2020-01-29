package cs3500.model.animator.model;

import java.awt.Color;
import java.util.Objects;

import cs3500.model.animator.util.Posn;

/**
 * Added since HW5. Class to represent all the KeyFrames that can be given to a Shape throughout an
 * Animation. Includes position, width, height, color, and time.
 */
public class KeyFrame {
  private Posn position;
  private int width;
  private int height;
  private Color frameColor;
  private double time;

  /**
   * Constructor for the KeyFrame class.
   *
   * @param position   the position at which the Shape is currently at
   * @param width      the width that the Shape is currently at
   * @param height     the height that the Shape is currently at
   * @param frameColor the color of the Shape
   * @param time       the time that the Shape is currently at
   * @throws IllegalArgumentException if input Posn position is null
   * @throws IllegalArgumentException if the height or width of the shape is negative
   * @throws IllegalArgumentException if the input Color framecolor is null
   * @throws IllegalArgumentException if the time is negative
   */
  public KeyFrame(Posn position, int width, int height, Color frameColor, double time)
          throws IllegalArgumentException {
    if (position == null) {
      throw new IllegalArgumentException("Position can't be null");
    } else if (height <= 0 || width <= 0) {
      throw new IllegalArgumentException("Invalid Scaling.");
    } else if (frameColor == null) {
      throw new IllegalArgumentException("Invalid Color - Cannot be null");
    } else if (time < 0) {
      throw new IllegalArgumentException("Invalid Start/End Time- Negative time");
    }

    this.position = position;
    this.width = width;
    this.height = height;
    this.frameColor = frameColor;
    this.time = time;
  }

  /**
   * Getter method for the KeyFrame position.
   *
   * @return the starting position as a Posn
   */
  public Posn getPosition() {
    Posn copyStartPosition = new Posn(this.position.getX(), this.position.getY());
    return copyStartPosition;
  }


  /**
   * Getter method for the KeyFrame width.
   *
   * @return the width as an int
   */
  public int getWidth() {
    return this.width;
  }

  /**
   * Getter method for the KeyFrame height.
   *
   * @return the height as an int
   */
  public int getHeight() {
    return this.height;
  }


  /**
   * Getter method for the KeyFrame color.
   *
   * @return the next Color as a Color
   */
  public Color getColor() {
    Color copyFinalColor = new Color(this.frameColor.getRGB());
    return copyFinalColor;
  }

  /**
   * Getter method for the KeyFrame time.
   *
   * @return the start time as an int
   */
  public double getTime() {
    return this.time;
  }

  /**
   * Getter method for the KeyFrame itself. Creates a copy of a given KeyFrame and returns the copy
   * of it.
   *
   * @return copy of a KeyFrame
   */
  public KeyFrame getCopyKeyFrame() {
    Posn copyPosition = new Posn(this.position.getX(), this.position.getY());
    int copyWidth = this.width;
    int copyHeight = this.height;
    Color copyColor = new Color(this.frameColor.getRGB());
    double copyTime = this.time;

    KeyFrame copyKeyFrame = new KeyFrame(copyPosition, copyWidth, copyHeight, copyColor, copyTime);

    return copyKeyFrame;
  }

  /**
   * Method to override equals method for KeyFrame Object, by determining if all variables inside of
   * object are equal.
   *
   * @param o input Object to determine whether or not this is equal to
   * @return a boolean, true if o equals this, false if o does not equal this
   */
  @Override
  public boolean equals(Object o) {

    if (o == this) {
      return true;
    }
    if (!(o instanceof KeyFrame)) {
      return false;
    }
    KeyFrame compareKey = (KeyFrame) o;
    return (position.equals(compareKey.getPosition())
            && width == compareKey.getWidth()
            && height == compareKey.getHeight()
            && frameColor.equals(compareKey.getColor())
            && time == compareKey.getTime());
  }

  /**
   * Method to override the hashcode method of a KeyFrame.
   *
   * @return the hashCode
   */
  @Override
  public int hashCode() {
    return Objects.hash(position, width, height, frameColor, time);
  }

  /**
   * Method to turn KeyFrame into an easily readable toString.
   *
   * @return a String representation of the KeyFrame.
   */
  @Override
  public String toString() {
    StringBuilder str = new StringBuilder();
    str.append(time);
    str.append(" ");
    str.append(position.getX());
    str.append(" ");
    str.append(position.getY());
    str.append(" ");
    str.append(width);
    str.append(" ");
    str.append(height);
    str.append(" ");
    str.append(frameColor.getRed());
    str.append(" ");
    str.append(frameColor.getGreen());
    str.append(" ");
    str.append(frameColor.getBlue());
    str.append("\n");
    return str.toString();
  }
}
