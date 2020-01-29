package cs3500.model.animator.model;

import cs3500.model.shape.IShape;

/**
 * Interface that holds all the methods that will be implemented in the Animator class. All of the
 * animations should contain shapes and a list of direction for each shape.
 */
public interface IAnimatorModel extends ReadOnlyIAnimatorModel {
  /**
   * Method to add a Shape to the animator.
   *
   * @param shape representation of a Shape (rectangle, eclipse, etc.)
   * @throws IllegalArgumentException if Shape added is null
   */
  void addShape(IShape... shape);

  /**
   * Method to remove a Shape from the animator.
   *
   * @param shape representation of a Shape (rectangle, eclipse, etc.)
   */
  void removeShape(IShape... shape);

  /**
   * Added since HW6.
   * Returns the Shape in Animator based of the Shape ID user inputs.
   *
   * @param shapeID the given ID of the Shape
   * @return the Shape corresponding to that shape ID
   */
  IShape getShapeWithID(String shapeID);

  /**
   * Added from Hw 6. Method to remove a Shape from the animator given an ID.
   *
   * @param shapeID The specific ID of the shape
   */
  void removeShapeWithID(String shapeID);

  /**
   * Method to add Directions for a given Shape to events List.
   *
   * @param shape     representation of a Shape (rectangle, eclipse, etc.)
   * @param direction representation of a Direction
   * @throws IllegalArgumentException if there are overlapping directions, added from hw5
   */
  void addDirection(IShape shape, Direction... direction);

  /**
   * Method to remove Directions for a given Shape from event List.
   *
   * @param shape     representation of a Shape (rectangle, ellipse, etc.)
   * @param direction representation of a Direction
   */
  void removeDirection(IShape shape, Direction direction);

  /**
   * Added since HW5. Returns a String representing the Animation.
   *
   * @return Animation as a String
   */
  String toString();

  /**
   * Finds the time it takes for the entire animation to complete.
   *
   * @return the last possible tick in the animation.
   */
  double totalDuration();

  /**
   * Added since HW6. Method to add KeyFrame to model. If Directions do not exist for the given
   * shape, extend KeyFrame 1 second with no changes to position, size, color.
   *
   * @param shapeID ID of the Shape to be changed
   * @param k       The KeyFrame to be added
   */
  void addKeyFrame(String shapeID, KeyFrame k);

  /**
   * Added since HW6. Method to remove a KeyFrame from a shape in Animation.
   *
   * @param shapeID the ID of the shape which is to be edited
   * @param kTime   the time at the KeyFrame of the shape that is to be deleted.
   */
  void deleteKeyFrame(String shapeID, double kTime);
}
