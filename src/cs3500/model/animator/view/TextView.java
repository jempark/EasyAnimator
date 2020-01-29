package cs3500.model.animator.view;

import cs3500.model.animator.model.ReadOnlyIAnimatorModel;

import java.io.IOException;

/**
 * Represents the Text View of the Animator model and returns a string containing the details of the
 * shapes and the respective given directions.
 */
public class TextView implements IView {
  private ReadOnlyIAnimatorModel model;
  private Appendable ap;

  /**
   * Constructs a TextView given a model, an appendable, and a tempo.
   *
   * @param model the animator model
   * @param ap    the appendable object
   * @throws IllegalArgumentException if the model or Appendable object is null or the tempo is less
   *                                  than or equal to 0
   */
  public TextView(ReadOnlyIAnimatorModel model, Appendable ap) {
    if (model == null || ap == null) {
      throw new IllegalArgumentException("Null model or Appendable");
    }

    this.model = model;
    this.ap = ap;
  }

  /**
   * Creates the output for the view- takes the model and displays it.
   *
   * @throws IOException if the input or output is invalid.
   */
  @Override
  public void createView() throws IOException {
    ap.append(model.toString());
  }
}
