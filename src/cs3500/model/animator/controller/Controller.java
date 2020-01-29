package cs3500.model.animator.controller;

import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import cs3500.model.animator.model.IAnimatorModel;
import cs3500.model.animator.view.IInteractiveView;
import cs3500.model.animator.view.IView;
import cs3500.model.animator.view.InteractiveView;

import cs3500.model.animator.view.SVGView;
import cs3500.model.animator.view.TextView;
import cs3500.model.shape.Ellipse;
import cs3500.model.shape.IShape;
import cs3500.model.shape.Rectangle;

/**
 * Implements the IController interface. Class to control interactive view and takes in an IAnimator
 * model and the speed at which the animation initially run. Allows users to change the original
 * animation model through the interactive view.
 */
public class Controller implements IController {
  IAnimatorModel model;
  double speed;
  IInteractiveView interactiveView;

  /**
   * Constructor for Controller. Takes in an IAnimator model and the speed.
   *
   * @param model The IAnimator model
   * @param speed Th speed at which the animation is initially run
   */
  public Controller(IAnimatorModel model, double speed) {
    this.model = model;
    this.speed = speed;
  }

  /**
   * Creates an Interactive View.
   */
  public void executeInteractiveView() throws IOException {
    interactiveView = new InteractiveView(model, speed, this);
    interactiveView.createView();
  }

  /**
   * Overrides actionPerformed from ActionListener. Connected to all of the button ins Interactive
   * view that edit the model directly.
   *
   * @param e The ActionEvent
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    String shapeID;
    switch (e.getActionCommand()) {
      case "Delete Shape":
        interactiveView.stopView();
        model.removeShapeWithID(interactiveView.getDeletedShapeID());
        break;
      case "Add Shape":
        interactiveView.stopView();
        IShape shape;
        if (interactiveView.getNewShapeType().equals("Rectangle")) {
          shape = new Rectangle(interactiveView.getNewShapeID());
        } else if (interactiveView.getNewShapeType().equals("Ellipse")) {
          shape = new Ellipse(interactiveView.getNewShapeID());
        } else {
          throw new IllegalArgumentException("Did not select valid shape type");
        }
        model.addShape(shape);
        break;
      case "Add Keyframe":
        interactiveView.stopView();
        shapeID = interactiveView.getSelectedShapeID();
        model.addKeyFrame(shapeID, interactiveView.getUserKeyFrame());
        break;
      case "Delete Keyframe":
        interactiveView.stopView();
        shapeID = interactiveView.getSelectedShapeID();
        model.deleteKeyFrame(shapeID, interactiveView.getKeyFrameTime());
        break;
      case "Save Animation":
        interactiveView.stopView();
        try {
          saveView();
        } catch (IOException e1) {
          e1.printStackTrace();
        }
        break;
      default:
        throw new IllegalArgumentException("Should not reach this case");
    }
    try {
      executeInteractiveView();
    } catch (IOException e1) {
      e1.printStackTrace();
    }
  }

  private void saveView() throws IOException {
    String outputFile = interactiveView.getOutputFile();
    String type = interactiveView.getSaveType();
    Appendable out = System.out;

    if (!outputFile.isEmpty()) {
      out = new StringBuilder();
    }

    if (type.equals("SVG")) {
      outputFile = outputFile + ".svg";
      IView svgView = new SVGView(model, out, speed);
      svgView.createView();
    } else if (type.equals("Text")) {
      outputFile = outputFile + ".txt";
      IView textView = new TextView(model, out);
      textView.createView();
    } else {
      throw new IllegalArgumentException("Did not select valid save type");
    }

    if (!out.equals("System.out")) {
      BufferedWriter writer = null;
      writer = new BufferedWriter(new FileWriter(new File(outputFile)));
      writer.write(out.toString());
      writer.flush();
      writer.close();
    }
  }
}



