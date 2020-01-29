package cs3500.model.animator.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Interface that controls the interactive view and allows users to change the original animation
 * model through the interactive view.
 */
public interface IController extends ActionListener {
  /**
   * Creates an Interactive View.
   */
  void executeInteractiveView() throws IOException;

  /**
   * Overrides actionPerformed from ActionListener. Connected to all of the button ins Interactive
   * view that edit the model directly.
   *
   * @param e The ActionEvent
   */
  void actionPerformed(ActionEvent e);
}
