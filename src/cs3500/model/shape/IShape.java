package cs3500.model.shape;

import java.awt.Color;
import cs3500.model.animator.util.Posn;

/**
 * The interface that represents a Shape.
 */
public interface IShape {

  /**
   * Gets the name of the shape as the String.
   *
   * @return the name of the shape
   */
  String getShape();

  /**
   * Makes and returns a copy of the same Shape.
   *
   * @return a copy of the shape.
   */
  IShape getCopy();

  /**
   * Gets the ID of the shape as a String.
   *
   * @returns the ID of the shape
   */
  String getID();

  /**
   * Gets the name of the color as the String.
   *
   * @return the color of the shape
   */
  Color getColor();


  /**
   * Gets the position of the Shape.
   *
   * @return the position of the Shape as an Posn
   */
  Posn getPosition();

  /**
   * Gets the height of the shape.
   *
   * @return the height of the Shape as an int
   */
  int getHeight();

  /**
   * Gets the width of the shape.
   *
   * @return the width of the Shape as an int
   */
  int getWidth();

  /**
   * Added since Homework 6. To Override the built-in Equals function to determine shape equality.
   *
   * @param obj The Object that you compare the current shape to
   * @return True or False depending on equality
   */
  boolean equals(Object obj);
}
