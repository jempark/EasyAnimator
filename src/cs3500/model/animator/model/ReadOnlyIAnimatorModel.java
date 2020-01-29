package cs3500.model.animator.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import cs3500.model.shape.IShape;

/**
 * The Read Only Interface for model to be passed into Views so that the Views will only be able to
 * access get methods and not have access to editing the model itself. Added since Homework 6.
 */
public interface ReadOnlyIAnimatorModel {
  /**
   * Added since HW 5. Creates a new copy of the Linked HashMap representing the Animator's HashMap
   * of events.
   *
   * @return the Linked HashMap of directions with the shapes being the key
   */
  LinkedHashMap<IShape, ArrayList<Direction>> getCopyDirectionMap();

  /**
   * Added since HW5. Creates a new copy of the Linked HashMap in a way that is rebounded so that
   * the points are correct relative to the canvas, which starts at (0,0) in SVG and Visual View.
   *
   * @return the Linked HashMap of directions with the shapes being the key
   */
  LinkedHashMap<IShape, ArrayList<Direction>> getCopyDirectionMapRebounded();

  /**
   * Added since HW5. Creates a new copy of the Shape Array List.
   *
   * @return a copy of an ArrayList of Shapes
   */
  ArrayList<IShape> getCopyShapeList();

  /**
   * Added since HW5. Getter method for Animator Model to return the upper left X coordinate of the
   * canvas.
   *
   * @return the upper left X coordinate of the canvas.
   */
  int getBoundX();

  /**
   * Added since HW5. Getter method for Animator Model to return the upper left Y coordinate of the
   * canvas.
   *
   * @return the upper left Y coordinate of the canvas.
   */
  int getBoundY();

  /**
   * Added since HW5. Getter method for Animator Model to return the Width of the canvas.
   *
   * @return the upper left Width coordinate of the canvas.
   */
  int getBoundWidth();

  /**
   * Added since HW5. Getter method for Animator Model to return the Height of the canvas.
   *
   * @return the upper left Height coordinate of the canvas.
   */
  int getBoundHeight();

  /**
   * Added since HW6. Getter method for Animator Model to return list of existing Shape IDs.
   *
   * @return ArrayList of current existing shape IDs
   */
  ArrayList<String> getShapeIDList();
}
