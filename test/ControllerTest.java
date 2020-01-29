import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.io.IOException;

import cs3500.model.animator.controller.Controller;
import cs3500.model.animator.controller.IController;
import cs3500.model.animator.model.Direction;
import cs3500.model.animator.model.IAnimatorModel;
import cs3500.model.animator.model.SimpleAnimatorModel;
import cs3500.model.animator.util.Posn;
import cs3500.model.animator.view.IInteractiveView;
import cs3500.model.animator.view.InteractiveView;
import cs3500.model.shape.Ellipse;
import cs3500.model.shape.IShape;
import cs3500.model.shape.Rectangle;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;

/**
 * Tests for the Controller and Listener. Tests both valid and invalid button clicks with inputs.
 */
public class ControllerTest {
  IShape r;
  IShape r1;
  IShape e;
  Direction recDirection0;
  Direction recDirection1;
  Direction recDirection2;
  Direction recDirection3;
  Direction recDirection4;
  Direction ellDirection0;
  Direction ellDirection1;
  Direction ellDirection2;
  Direction ellDirection3;
  Direction ellDirection4;

  IAnimatorModel animator = new SimpleAnimatorModel();
  IController testController;
  IInteractiveView interactiveView;

  /**
   * Setting up the tests.
   */
  @Before
  public void initConstructor() {
    r = new Rectangle("rec", new Color(255, 0, 0), new Posn(200, 200),
            50, 100);

    r1 = r = new Rectangle("rec", new Color(0, 255, 0), new Posn(100, 100),
            50, 100);

    e = new Ellipse("elli", new Color(0, 0, 255), new Posn(440, 70),
            120, 60);

    recDirection0 = new Direction(new Posn(200, 200), new Posn(200, 200), 50,
            50, 100, 100,
            new Color(255, 0, 0), new Color(255, 0, 0), 1, 10);

    recDirection1 = new Direction(new Posn(200, 200), new Posn(300, 300), 50,
            50, 100, 100,
            new Color(255, 0, 0), new Color(255, 0, 0), 10, 50);

    recDirection2 = new Direction(new Posn(300, 300), new Posn(300, 300), 50,
            50, 100, 100,
            new Color(255, 0, 0), new Color(255, 0, 0), 50, 51);

    recDirection3 = new Direction(new Posn(300, 300), new Posn(300, 300), 50,
            25, 100, 100,
            new Color(255, 0, 0), new Color(255, 0, 0), 51, 70);

    recDirection4 = new Direction(new Posn(300, 300), new Posn(200, 200), 25,
            25, 100, 100,
            new Color(255, 0, 0), new Color(255, 0, 0), 70, 100);

    ellDirection0 = new Direction(new Posn(440, 70), new Posn(440, 70), 120,
            120, 60, 60,
            new Color(0, 0, 255), new Color(0, 0, 255), 6, 20);

    ellDirection1 = new Direction(new Posn(440, 70), new Posn(440, 250), 120,
            120, 60, 60,
            new Color(0, 0, 255), new Color(0, 0, 255), 20, 50);

    ellDirection2 = new Direction(new Posn(440, 250), new Posn(440, 370), 120,
            120, 60, 60,
            new Color(0, 0, 255), new Color(0, 170, 85), 50, 70);

    ellDirection3 = new Direction(new Posn(440, 370), new Posn(440, 370), 120,
            120, 60, 60,
            new Color(0, 170, 85), new Color(0, 255, 0), 70, 80);

    ellDirection4 = new Direction(new Posn(440, 370), new Posn(440, 370), 120,
            120, 60, 60,
            new Color(0, 255, 0), new Color(0, 255, 0), 80, 100);

    animator.addShape(r, e);
    animator.addDirection(r, recDirection0, recDirection1, recDirection2, recDirection3,
            recDirection4);
    animator.addDirection(e, ellDirection0, ellDirection1, ellDirection2, ellDirection3,
            ellDirection4);

    testController = new Controller(animator, 10);
    interactiveView = new InteractiveView(animator, 10, testController);
  }

  @Test
  public void buttonPressDeleteShape() {
    try {
      testController.executeInteractiveView();
      testController.actionPerformed(new ActionEvent(interactiveView, 1, "Delete Shape"));
    } catch (IOException e) {
      System.out.println(e);
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void buttonPressAddShapeWithoutParams() {
    try {
      testController.executeInteractiveView();
      testController.actionPerformed(new ActionEvent(interactiveView, 1, "Add Shape"));
    } catch (IOException e) {
      System.out.println(e);
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid Shape ID", e.getMessage());
    }
  }

  @Test
  public void buttonPressAddKeyFrame() {
    try {
      testController.executeInteractiveView();
      testController.actionPerformed(new ActionEvent(interactiveView, 1, "Add Keyframe"));
    } catch (IOException e) {
      System.out.println(e);
    } catch (IllegalArgumentException e) {
      assertEquals("For input string: \"\"", e.getMessage());
    }
  }

  @Test
  public void buttonPressDeleteKeyFrame() {
    try {
      testController.executeInteractiveView();
      testController.actionPerformed(new ActionEvent(interactiveView, 1, "Delete Keyframe"));
    } catch (IOException e) {
      System.out.println(e);
    } catch (IllegalArgumentException e) {
      assertEquals("For input string: \"\"", e.getMessage());
    }
  }

  @Test
  public void buttonPressSaveAnimation() {
    try {
      testController.executeInteractiveView();
      testController.actionPerformed(new ActionEvent(interactiveView, 1, "Save Animation"));
    } catch (IOException e) {
      System.out.println(e);
    } catch (Exception e) {
      fail();
    }
  }


  @Test
  public void buttonPressMultipleCommands() {
    String[] actionEvents = {"Delete Shape", "Save Animation"};
    for (String actionEvent : actionEvents) {
      try {
        testController.executeInteractiveView();
        testController.actionPerformed(new ActionEvent("fuck", 1, actionEvent));
      } catch (IOException e) {
        System.out.println(e);
      } catch (Exception e) {
        fail();
      }
    }
  }

  @Test
  public void buttonPressInvalidCommand() {
    try {
      testController.executeInteractiveView();
      testController.actionPerformed(new ActionEvent(interactiveView, 1, "not valid"));
    } catch (IOException e) {
      System.out.println(e);
    } catch (IllegalArgumentException e) {
      assertEquals("Should not reach this case", e.getMessage());
    }
  }

  @Test
  public void buttonPressInvalidCommandEmpty() {
    try {
      testController.executeInteractiveView();
      testController.actionPerformed(new ActionEvent(interactiveView, 1, ""));
    } catch (IOException e) {
      System.out.println(e);
    } catch (IllegalArgumentException e) {
      assertEquals("Should not reach this case", e.getMessage());
    }
  }

  @Test
  public void buttonPressPause() {
    try {
      interactiveView.createView();
      interactiveView.actionPerformed(new ActionEvent(interactiveView, 1, "Pause"));
    } catch (IOException e) {
      System.out.println(e);
    } catch (IllegalArgumentException e) {
      fail();
    }
  }

  @Test
  public void buttonPressPlay() {
    try {
      interactiveView.createView();
      interactiveView.actionPerformed(new ActionEvent(interactiveView, 1, "Play"));
    } catch (IOException e) {
      System.out.println(e);
    } catch (IllegalArgumentException e) {
      fail();
    }
  }

  @Test
  public void buttonPressPlayPause() {
    try {
      interactiveView.createView();
      interactiveView.actionPerformed(new ActionEvent(interactiveView, 1, "Play/Pause"));
    } catch (IOException e) {
      System.out.println(e);
    } catch (IllegalArgumentException e) {
      fail();
    }
  }

  @Test
  public void buttonPressLoop() {
    try {
      interactiveView.createView();
      interactiveView.actionPerformed(new ActionEvent(interactiveView, 1, "Loop"));
    } catch (IOException e) {
      System.out.println(e);
    } catch (IllegalArgumentException e) {
      fail();
    }
  }

  @Test
  public void buttonPressRestart() {
    try {
      interactiveView.createView();
      interactiveView.actionPerformed(new ActionEvent(interactiveView, 1, "Play"));
    } catch (IOException e) {
      System.out.println(e);
    } catch (IllegalArgumentException e) {
      fail();
    }
  }

  @Test
  public void buttonPressIncreaseSpeed() {
    try {
      interactiveView.createView();
      interactiveView.actionPerformed(new ActionEvent(interactiveView, 1, "Increase Speed"));
    } catch (IOException e) {
      System.out.println(e);
    } catch (IllegalArgumentException e) {
      fail();
    }
  }

  @Test
  public void buttonPressDecreaseSpeed() {
    try {
      interactiveView.createView();
      interactiveView.actionPerformed(new ActionEvent(interactiveView, 1, "Decrease Speed"));
    } catch (IOException e) {
      System.out.println(e);
    } catch (IllegalArgumentException e) {
      fail();
    }
  }

  @Test
  public void buttonPressGetOutputFileNoSpecified() {
    try {
      interactiveView.createView();
      assertEquals("", interactiveView.getOutputFile());
    } catch (IOException e) {
      System.out.println(e);
    }
  }

  @Test
  public void buttonPressGetSaveType() {
    try {
      interactiveView.createView();
      assertEquals("SVG", interactiveView.getSaveType());
    } catch (IOException e) {
      System.out.println(e);
    }
  }

  @Test
  public void buttonPressGetKeyFrameNoTime() {
    try {
      interactiveView.createView();
      interactiveView.getKeyFrameTime();
      fail();
    } catch (IOException e) {
      System.out.println(e);
    } catch (IllegalArgumentException e) {
      assertEquals("For input string: \"\"", e.getMessage());
    }
  }

  @Test
  public void buttonPressGetUserKeyFrame() {
    try {
      interactiveView.createView();
      interactiveView.getUserKeyFrame();
      fail();
    } catch (IOException e) {
      System.out.println(e);
    } catch (IllegalArgumentException e) {
      assertEquals("For input string: \"\"", e.getMessage());
    }
  }

  @Test
  public void buttonPressGetNewShapeID() {
    try {
      interactiveView.createView();
      assertEquals("", interactiveView.getNewShapeID());
    } catch (IOException e) {
      System.out.println(e);
    }
  }

  @Test
  public void buttonPressGetNewShapeType() {
    try {
      interactiveView.createView();
      assertEquals("Rectangle", interactiveView.getNewShapeType());
    } catch (IOException e) {
      System.out.println(e);
    }
  }

  @Test
  public void buttonPressGetSelectedShape() {
    try {
      interactiveView.createView();
      assertEquals("rec", interactiveView.getSelectedShapeID());
    } catch (IOException e) {
      System.out.println(e);
    }
  }
}
