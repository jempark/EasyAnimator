package cs3500.model.shape;

import java.awt.Color;
import java.util.Objects;

import cs3500.model.animator.util.Posn;

/**
 * Representation of an Ellipse Shape that extends from the Abstract Shape Class.
 */
public class Ellipse extends AShape {
  private String shapeName = "Ellipse";

  /**
   * Constructor for the Ellipse that takes in only ID.
   *
   * @param id the ID of the Ellipse
   */
  public Ellipse(String id) {
    super(id);
  }

  /**
   * Default Constructor for the Ellipse.
   *
   * @param id       the ID of the Ellipse
   * @param color    the color of the Ellipse
   * @param position the starting position of the Ellipse
   * @param width    the width of the Ellipse
   * @param height   the height of the Ellipse
   */
  public Ellipse(String id, Color color, Posn position, int width, int height) {
    super(id, color, position, width, height);
  }

  /**
   * Gets the name of the Ellipse as the String.
   *
   * @return the name of the Ellipse
   */
  @Override
  public String getShape() {
    String copyName = this.shapeName;
    return copyName;
  }

  /**
   * Makes and returns a copy of the same Ellipse.
   *
   * @return a copy of the Ellipse.
   */
  @Override
  public IShape getCopy() {
    String copyID = this.id;
    Color copyColor = new Color(this.color.getRGB());
    Posn copyPosn = new Posn(this.position.getX(), this.position.getY());
    int copyWidth = this.width;
    int copyHeight = this.height;


    IShape copyEllipse = new Ellipse(copyID, copyColor, copyPosn, copyWidth, copyHeight);
    return copyEllipse;
  }

  /**
   * Gets the ID of the Ellipse as a String.
   *
   * @returns the ID of the Ellipse
   */
  @Override
  public String getID() {
    String copyID = this.id;
    return copyID;
  }

  /**
   * Added since Homework 6. To Override the built-in Equals function to determine shape equality.
   *
   * @param obj The Object that you compare the current shape to
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
    if (!(obj instanceof IShape)) {
      return false;
    }
    IShape o = (IShape) obj;
    return (o.getID() == this.id
            && o.getShape() == this.shapeName
            && o.getColor() == this.color
            && o.getHeight() == this.height
            && o.getWidth() == this.width
            && o.getPosition() == this.position);
  }

  /**
   * Method that overrides the hashCode method.
   *
   * @return hashCode
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.id, this.color, this.height, this.width, this.position);
  }
}
