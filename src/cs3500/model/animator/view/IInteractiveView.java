package cs3500.model.animator.view;

import java.awt.event.ActionListener;

import cs3500.model.animator.model.KeyFrame;

/**
 * Represents an Interactive View for the animation. Plays the Animation as a Visual View and allows
 * the user to do actions such as play, pause, loop, increase/decrease speed. Also allows users to
 * add or remove shapes and key frames. User can save their masterpieces after edit as an svg or
 * text file as well.
 */
public interface IInteractiveView extends IView, ActionListener {
  /**
   * Method to stop the model for the Interactive View once the model has changed.
   */
  void stopView();

  /**
   * Gets the selected shape ID that the user selected.
   *
   * @return a String of the shape ID
   */
  String getSelectedShapeID();

  /**
   * Gets the deleted shape ID that the user selected.
   *
   * @return a String of the shape ID
   */
  String getDeletedShapeID();

  /**
   * Gets the shape type that the user selected.
   *
   * @return the String of the shape Type
   */
  String getNewShapeType();

  /**
   * Gets the input ID of the shape that the user added.
   *
   * @return the String of the input ID
   */
  String getNewShapeID();

  /**
   * Get the Keyframe that the user has created by compiling all of the test input in the UI.
   *
   * @return keyframe to be added
   */
  KeyFrame getUserKeyFrame();

  /**
   * Gets the time of the key frame.
   *
   * @return a double representing the time of the keyframe
   */
  double getKeyFrameTime();

  /**
   * Gets the user defined name of the Output File at which the animation is to be saved.
   *
   * @return the defined name of the Output File as a String
   */
  String getOutputFile();

  /**
   * Gets the format in which the animation would be saved as (SVG, Text).
   *
   * @return the format as a String.
   */
  String getSaveType();
}
