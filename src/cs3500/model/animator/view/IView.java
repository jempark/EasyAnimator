package cs3500.model.animator.view;

import java.io.IOException;

/**
 * Represents a view for the animation. A view is the ways a user can see how a shape
 * move from one state to another.
 */
public interface IView {
  /**
   * Creates the output for the view- takes the model and displays it.
   *
   * @throws IOException if the input or output is invalid.
   */
  void createView() throws IOException;
}