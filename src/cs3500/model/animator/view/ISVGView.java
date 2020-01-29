package cs3500.model.animator.view;

import java.io.IOException;

/**
 * Represents the SVG view for the animation. A view is the ways a user can see how a shape move
 * from one state to another.
 */
public interface ISVGView extends IView {
  /**
   * Generates a file that represents the data in the model in SVG format.
   *
   * @param fileName where the SVG will be saved
   * @throws IOException when there is an issue with writing to the file
   */
  void outputSVG(String fileName) throws IOException;
}
