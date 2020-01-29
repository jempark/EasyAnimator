import org.junit.Before;
import org.junit.Test;

import java.awt.Color;

import cs3500.model.animator.model.Direction;
import cs3500.model.animator.model.IAnimatorModel;
import cs3500.model.animator.model.SimpleAnimatorModel;
import cs3500.model.animator.util.Posn;
import cs3500.model.shape.Ellipse;
import cs3500.model.shape.IShape;
import cs3500.model.shape.Rectangle;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;

/**
 * Tests for the Simple Animator class.
 */
public class AnimatorTest {
  IShape r;
  IShape e;
  Direction recDirection0;
  Direction recDirection1;
  Direction recDirectionFake;
  Direction recDirection2;
  Direction recDirection3;
  Direction recDirection4;
  Direction ellDirection0;
  Direction ellDirection1;
  Direction ellDirection2;
  Direction ellDirection3;
  Direction ellDirection4;
  IAnimatorModel animator;

  /**
   * Initializes Direction and Shapes for the Simple Animation.
   */
  @Before
  public void init() {
    r = new Rectangle("rec", new Color(255, 0, 0), new Posn(200, 200),
            50, 100);

    e = new Ellipse("elli", new Color(0, 0, 255), new Posn(440, 70),
            120, 60);

    recDirection0 = new Direction(new Posn(200, 200), new Posn(200, 200), 50,
            50, 100, 100,
            new Color(255, 0, 0), new Color(255, 0, 0), 1, 10);

    recDirection1 = new Direction(new Posn(200, 200), new Posn(300, 300), 50,
            50, 100, 100,
            new Color(255, 0, 0), new Color(255, 0, 0), 10, 50);

    recDirectionFake = new Direction(new Posn(200, 200), new Posn(300, 300), 50,
            50, 100, 100,
            new Color(255, 0, 0), new Color(255, 0, 0), 25, 30);

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

    animator = new SimpleAnimatorModel();
  }

  @Test
  public void testAddNullShape1() {
    try {
      animator.addShape(null, r);
      fail("Did not pass");
    } catch (IllegalArgumentException e) {
      assertEquals("Shape cannot be null and every shape must have uniqueIDs.",
              e.getMessage());
    }
  }

  @Test
  public void testAddNullShape2() {
    try {
      animator.addShape(r, e, null);
      fail("Did not pass");
    } catch (IllegalArgumentException e) {
      assertEquals("Shape cannot be null and every shape must have uniqueIDs.",
              e.getMessage());
    }
  }

  /**
   * We toString() the animator because we want to check whether or not the shapes were added
   * correctly.
   */
  @Test
  public void testAddShapesToList() {
    animator.addShape(r, e);
    assertEquals("canvas 0 0 1000 1000\n" +
            "shape rec rectangle\n" +
            "\n" +
            "shape elli ellipse\n", animator.toString());
  }

  @Test
  public void testAddSameShapeWithSameID() {
    try {
      animator.addShape(r, r);
      fail("Did not pass");
    } catch (IllegalArgumentException e) {
      assertEquals("Shape cannot be null and every shape must have uniqueIDs.",
              e.getMessage());
    }
  }

  @Test
  public void testRemoveThenAddSameShape() {
    animator.addShape(r);
    animator.removeShape(r);
    animator.addShape(r);

    assertEquals("canvas 0 0 1000 1000\n" +
            "shape rec rectangle\n", animator.toString());
  }

  /**
   * We toString() the animator because we want to check whether or not the shapes were added
   * correctly.
   */
  @Test
  public void testRemoveShapeFromList() {
    animator.addShape(r, e);
    animator.removeShape(r);
    assertEquals("canvas 0 0 1000 1000\n" +
            "shape elli ellipse\n", animator.toString());
  }

  /**
   * We toString() the animator because we want to check whether or not the shapes were added
   * correctly.
   */
  @Test
  public void testRemoveDirectionFromShape() {
    animator.addShape(r);
    animator.addDirection(r, recDirection0, recDirection1, recDirection2, recDirection3,
            recDirection4);
    animator.removeDirection(r, recDirection4);

    assertEquals("canvas 0 0 1000 1000\n" +
                    "shape rec rectangle\n" +
                    "motion rec 1.0 200 200 50 100 255 0 0 10.0 200 200 50 100 255 0 0\n" +
                    "motion rec 10.0 200 200 50 100 255 0 0 50.0 300 300 50 100 255 0 0\n" +
                    "motion rec 50.0 300 300 50 100 255 0 0 51.0 300 300 50 100 255 0 0\n" +
                    "motion rec 51.0 300 300 50 100 255 0 0 70.0 300 300 25 100 255 0 0\n",
            animator.toString());
  }

  @Test
  public void testRemoveDirectionFromEmptyDirectionList() {
    try {
      animator.addShape(r);
      animator.removeDirection(r, recDirection4);
      fail("Did not pass");
    } catch (IllegalArgumentException e) {
      assertEquals("Cannot remove because direction doesn't exist.", e.getMessage());
    }
  }

  @Test
  public void testRemoveDirectionThatDoesntExistInDirectionList() {
    try {
      animator.addShape(r);
      animator.addDirection(r, recDirection0, recDirection1, recDirection2, recDirection3,
              recDirection4);
      animator.removeDirection(r, recDirectionFake);
      fail("Did not pass");
    } catch (IllegalArgumentException e) {
      assertEquals("Cannot remove because direction doesn't exist.", e.getMessage());
    }
  }

  @Test
  public void testRemoveDirectionFromNonExistantShape() {
    try {
      animator.addShape(r);
      animator.removeDirection(e, recDirectionFake);
      fail("Did not pass");
    } catch (IllegalArgumentException e) {
      assertEquals("Cannot remove because shape doesn't exist.", e.getMessage());
    }
  }

  @Test
  public void testRemoveDirectionFromNullShape() {
    try {
      animator.addShape(r);
      animator.removeDirection(null, recDirectionFake);
      fail("Did not pass");
    } catch (IllegalArgumentException e) {
      assertEquals("Cannot remove because shape doesn't exist.", e.getMessage());
    }
  }

  @Test
  public void testRemoveNullDirectionFromShape() {
    try {
      animator.addShape(r);
      animator.removeDirection(r, null);
      fail("Did not pass");
    } catch (IllegalArgumentException e) {
      assertEquals("Cannot remove because direction doesn't exist.", e.getMessage());
    }
  }

  /**
   * We toString() the animator because we want to check whether or not the shapes were added
   * correctly.
   */
  @Test
  public void testAddShapeAndDirection() {
    animator.addShape(r);
    animator.addDirection(r, recDirection0, recDirection1, recDirection2, recDirection3,
            recDirection4);
    assertEquals("canvas 0 0 1000 1000\n" +
                    "shape rec rectangle\n" +
                    "motion rec 1.0 200 200 50 100 255 0 0 10.0 200 200 50 100 255 0 0\n" +
                    "motion rec 10.0 200 200 50 100 255 0 0 50.0 300 300 50 100 255 0 0\n" +
                    "motion rec 50.0 300 300 50 100 255 0 0 51.0 300 300 50 100 255 0 0\n" +
                    "motion rec 51.0 300 300 50 100 255 0 0 70.0 300 300 25 100 255 0 0\n" +
                    "motion rec 70.0 300 300 25 100 255 0 0 100.0 200 200 25 100 255 0 0\n",
            animator.toString());
  }

  @Test
  public void testAddShapeAndOverlappingDirection() {
    try {
      animator.addShape(r);
      animator.addDirection(r, recDirection0, recDirection1, recDirectionFake, recDirection2,
              recDirection3, recDirection4);
      fail("Did not pass");
    } catch (IllegalArgumentException e) {
      assertEquals("Direction cannot have any gaps", e.getMessage());
    }
  }

  @Test
  public void testAddShapeandGapDirection() {
    try {
      animator.addShape(r);
      animator.addDirection(r, recDirection0, recDirection2, recDirection3, recDirection4);
      fail("Did not pass");
    } catch (IllegalArgumentException e) {
      assertEquals("Direction cannot have any gaps", e.getMessage());
    }
  }

  @Test
  public void testUnsortedDirection() {
    animator.addShape(r);
    animator.addDirection(r, recDirection2, recDirection1, recDirection4,
            recDirection3, recDirection0);
    assertEquals("canvas 0 0 1000 1000\n" +
                    "shape rec rectangle\n" +
                    "motion rec 1.0 200 200 50 100 255 0 0 10.0 200 200 50 100 255 0 0\n" +
                    "motion rec 10.0 200 200 50 100 255 0 0 50.0 300 300 50 100 255 0 0\n" +
                    "motion rec 50.0 300 300 50 100 255 0 0 51.0 300 300 50 100 255 0 0\n" +
                    "motion rec 51.0 300 300 50 100 255 0 0 70.0 300 300 25 100 255 0 0\n" +
                    "motion rec 70.0 300 300 25 100 255 0 0 100.0 200 200 25 100 255 0 0\n",
            animator.toString());
  }

  @Test
  public void testAddShapeAndTestStartEndDirection() {
    animator.addShape(r);
    animator.addDirection(r, recDirection0, recDirection1);

    assertEquals(recDirection0.getEndTime(), recDirection1.getStartTime(), .01);
  }

  @Test
  public void testAddShapeAndNullDirection() {
    try {
      animator.addShape(r);
      animator.addDirection(r, recDirection0, recDirection1, recDirection2, recDirection3,
              recDirection4, null);
      fail("Did not pass");
    } catch (IllegalArgumentException e) {
      assertEquals("Direction cannot be null", e.getMessage());
    }
  }

  /**
   * We toString() the animator because we want to check whether or not the shapes were added
   * correctly.
   */
  @Test
  public void testRemoveShapesAndDirections() {
    animator.addShape(r, e);
    animator.addDirection(r, recDirection0, recDirection1, recDirection2, recDirection3,
            recDirection4);
    animator.addDirection(e, ellDirection0, ellDirection1, ellDirection2, ellDirection3,
            ellDirection4);
    animator.removeShape(r, e);
    assertEquals("canvas 0 0 1000 1000\n", animator.toString());
  }

  @Test
  public void testRemoveFromEmpty() {
    try {
      animator.removeShape(r);
      fail("Did not pass");
    } catch (IllegalArgumentException e) {
      assertEquals("Cannot remove from an empty List/Hash Map", e.getMessage());
    }
  }

  @Test
  public void testToString() {
    animator.addShape(r, e);
    animator.addDirection(r, recDirection0, recDirection1, recDirection2, recDirection3,
            recDirection4);
    animator.addDirection(e, ellDirection0, ellDirection1, ellDirection2, ellDirection3,
            ellDirection4);

    assertEquals("canvas 0 0 1000 1000\n" +
                    "shape rec rectangle\n" +
                    "motion rec 1.0 200 200 50 100 255 0 0 10.0 200 200 50 100 255 0 0\n" +
                    "motion rec 10.0 200 200 50 100 255 0 0 50.0 300 300 50 100 255 0 0\n" +
                    "motion rec 50.0 300 300 50 100 255 0 0 51.0 300 300 50 100 255 0 0\n" +
                    "motion rec 51.0 300 300 50 100 255 0 0 70.0 300 300 25 100 255 0 0\n" +
                    "motion rec 70.0 300 300 25 100 255 0 0 100.0 200 200 25 100 255 0 0\n" +
                    "\n" +
                    "shape elli ellipse\n" +
                    "motion elli 6.0 440 70 120 60 0 0 255 20.0 440 70 120 60 0 0 255\n" +
                    "motion elli 20.0 440 70 120 60 0 0 255 50.0 440 250 120 60 0 0 255\n" +
                    "motion elli 50.0 440 250 120 60 0 0 255 70.0 440 370 120 60 0 170 85\n" +
                    "motion elli 70.0 440 370 120 60 0 170 85 80.0 440 370 120 60 0 255 0\n" +
                    "motion elli 80.0 440 370 120 60 0 255 0 100.0 440 370 120 60 0 255 0\n",
            animator.toString());
  }
}