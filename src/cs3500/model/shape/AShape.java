package cs3500.model.shape;

import java.awt.Color;
import java.util.Objects;

import cs3500.model.animator.util.Posn;

/**
 * Abstract class for Shape representation, implements IShape.
 */
public abstract class AShape implements IShape {
  protected String id;
  protected Color color;
  protected Posn position;
  protected int width;
  protected int height;

  /**
   * Constructor for abstract Shape that takes in only id.
   *
   * @param id the ID of the shape
   * @throws IllegalArgumentException if width or height is less than 0
   * @throws IllegalArgumentException if position is null
   * @throws IllegalArgumentException if Color is null
   */
  public AShape(String id) {
    this(id, new Color(0, 0, 0), new Posn(0, 0), 1, 1);
  }


  /**
   * Default Constructor for abstract Shape.
   *
   * @param id       the ID of the shape
   * @param color    the color of the Shape
   * @param position the starting Position of the Shape
   * @param width    the starting Width of the Shape
   * @param height   the starting Height of the Shape
   * @throws IllegalArgumentException if width or height is less than 0
   * @throws IllegalArgumentException if position is null
   * @throws IllegalArgumentException if Color is null
   */
  public AShape(String id, Color color, Posn position, int width,
                int height) throws IllegalArgumentException {
    if (width <= 0 || height <= 0) {
      throw new IllegalArgumentException("Invalid Height and/or Width");
    } else if (position == null) {
      throw new IllegalArgumentException("Invalid Position");
    } else if (color == null) {
      throw new IllegalArgumentException("Invalid Color - Cannot be null");
    } else if (id.equals("") || id == null) {
      throw new IllegalArgumentException("Invalid Shape ID");
    }

    this.id = id;
    this.color = color;
    this.position = position;
    this.width = width;
    this.height = height;
  }


  /**
   * Gets the name of the shape as the String.
   *
   * @return the name of the shape
   */
  @Override
  public abstract String getShape();

  /**
   * Makes and returns a copy of the same Shape.
   *
   * @return a copy of the shape.
   */
  @Override
  public abstract IShape getCopy();


  /**
   * Gets the ID of the shape as a String.
   *
   * @returns the ID of the shape
   */
  @Override
  public abstract String getID();

  /**
   * Gets the name of the color as the String.
   *
   * @return the color of the shape
   */
  @Override
  public Color getColor() {
    Color copyColor = new Color(this.color.getRGB());
    return copyColor;
  }

  /**
   * Gets the position of the Shape.
   *
   * @return the position of the Shape as an Posn
   */
  @Override
  public Posn getPosition() {
    Posn copyPosn = new Posn(this.position.getX(), this.position.getY());
    return copyPosn;
  }

  /**
   * Gets the height of the shape.
   *
   * @return the height of the Shape as an int
   */
  @Override
  public int getHeight() {
    return height;
  }

  /**
   * Gets the width of the shape.
   *
   * @return the width of the Shape as an int
   */
  @Override
  public int getWidth() {
    return width;
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