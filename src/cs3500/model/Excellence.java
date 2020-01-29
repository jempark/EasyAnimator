package cs3500.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import cs3500.model.animator.controller.Controller;
import cs3500.model.animator.controller.IController;
import cs3500.model.animator.model.IAnimatorModel;
import cs3500.model.animator.model.SimpleAnimatorModel;
import cs3500.model.animator.util.AnimationReader;
import cs3500.model.animator.view.IView;
import cs3500.model.animator.view.SVGView;
import cs3500.model.animator.view.TextView;
import cs3500.model.animator.view.VisualView;

/**
 * Excellence class. Takes in user input "-in" for input file, "-out" for output file, "-speed" for
 * speed (ticks/sec) of animation, and "-view" for which view to use (svg, text, visual,
 * interactive).
 */
public class Excellence {

  private static final String IN = "-in";
  private static final String OUT = "-out";
  private static final String SPEED = "-speed";
  private static final String VIEW = "-view";
  private static final String DEFAULT_OUT = "System.out";
  private static String inFile = null;
  private static String outFile = DEFAULT_OUT;
  private static double speed = 1;
  private static String viewType = null;

  /**
   * Main class for Excellence. Determined input file, output file, speed of animation, and view
   * type.
   *
   * @param args the user input which determines the actions of main.
   * @throws IllegalArgumentException if user does not specify in file
   * @throws IllegalArgumentException if user does not specify view type
   * @throws IllegalArgumentException if user does not properly format input
   */
  public static void main(String[] args) throws IOException {
    Appendable out = System.out;

    getUserInput(args);

    if (inFile == null) {
      throw new IllegalArgumentException("Error: input file is null");
    }
    if (viewType == null) {
      throw new IllegalArgumentException("Error: view is null");
    }
    if (outFile != OUT) {
      out = new StringBuilder();
    }

    AnimationReader reader = new AnimationReader();
    SimpleAnimatorModel.Builder builder = new SimpleAnimatorModel.Builder();
    BufferedReader in = new BufferedReader(new FileReader(inFile));
    reader.parseFile(in, builder);
    IAnimatorModel model = builder.build();

    createView(model, out);

    if (!out.equals("System.out") && (viewType.equals("svg") || viewType.equals("text"))) {
      BufferedWriter writer = new BufferedWriter(new FileWriter(new File(outFile)));
      writer.write(out.toString());
      writer.flush();
      writer.close();
    }
  }

  /**
   * Helper method for main, translates user input in terms of inFile, outFile, speed of animation
   * and view type.
   *
   * @param args the user input
   * @throws IllegalArgumentException if user input is incorrectly formatted.
   */
  static void getUserInput(String[] args) {
    int i = 0;
    while (i < args.length) {
      try {
        switch (args[i]) {
          case IN:
            inFile = args[i + 1];
            break;
          case OUT:
            outFile = args[i + 1];
            break;
          case SPEED:
            speed = Integer.parseInt(args[i + 1]);
            break;
          case VIEW:
            viewType = args[i + 1];
            break;
          default:
            throw new IllegalArgumentException("Error: input is in incorrect format");
        }
      } catch (IndexOutOfBoundsException e) {
        throw new IllegalArgumentException("Error: input is in incorrect format");
      }
      i += 2;
    }
  }

  /**
   * Helper method for the main, creates a view depending on what type of view the user selected.
   *
   * @param model Animator model
   * @param out   The Appendable
   */
  static void createView(IAnimatorModel model, Appendable out) throws IOException {
    switch (viewType) {
      case "text":
        IView textView = new TextView(model, out);
        textView.createView();
        break;
      case "svg":
        IView svgView = new SVGView(model, out, speed);
        svgView.createView();
        break;
      case "visual":
        IView visualView = new VisualView(model, speed);
        visualView.createView();
        break;
      case "edit":
        IController controller = new Controller(model, speed);
        controller.executeInteractiveView();
        break;
      default:
        throw new IllegalArgumentException("Error: view not valid, please re-enter");
    }
  }
}