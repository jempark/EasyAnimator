import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.io.IOException;

import cs3500.model.animator.model.Direction;
import cs3500.model.animator.model.IAnimatorModel;
import cs3500.model.animator.model.SimpleAnimatorModel;
import cs3500.model.animator.view.TextView;
import cs3500.model.shape.Ellipse;
import cs3500.model.shape.IShape;
import cs3500.model.shape.Rectangle;
import cs3500.model.animator.util.Posn;

import static junit.framework.TestCase.assertEquals;

/**
 * Tests for the Text View.
 */
public class TextViewTest {
  IAnimatorModel animator = new SimpleAnimatorModel();
  Appendable ap = new StringBuilder();
  IShape r;
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

  /**
   * Setting up the tests.
   */
  @Before
  public void initConstructor() {
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
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullModel() {
    new TextView(null, ap);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullAppendable() {
    new TextView(animator, null);
  }

  @Test
  public void testGenerateViewOnEmptyAnimator() throws IOException {
    TextView textView = new TextView(animator, ap);
    textView.createView();
    assertEquals("canvas 0 0 1000 1000\n", ap.toString());
  }

  @Test
  public void testGenerateViewOnShapesNoDirection() throws IOException {
    animator.addShape(r, e);
    TextView textView = new TextView(animator, ap);
    textView.createView();
    assertEquals("canvas 0 0 1000 1000\n" +
            "shape rec rectangle\n" +
            "\n" +
            "shape elli ellipse\n", ap.toString());
  }

  @Test
  public void testGenerateView() throws IOException {
    animator.addShape(r, e);
    animator.addDirection(r, recDirection0, recDirection1, recDirection2, recDirection3,
            recDirection4);
    animator.addDirection(e, ellDirection0, ellDirection1, ellDirection2, ellDirection3,
            ellDirection4);

    TextView textView = new TextView(animator, ap);
    textView.createView();
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
            ap.toString());
  }

  @Test
  public void testGenerateViewAfterRemoveShapeAndDirections() throws IOException {
    animator.addShape(r, e);
    animator.addDirection(r, recDirection0, recDirection1, recDirection2, recDirection3,
            recDirection4);
    animator.addDirection(e, ellDirection0, ellDirection1, ellDirection2, ellDirection3,
            ellDirection4);
    animator.removeShape(r);

    TextView textView = new TextView(animator, ap);
    textView.createView();

    assertEquals("canvas 0 0 1000 1000\n" +
                    "shape elli ellipse\n" +
                    "motion elli 6.0 440 70 120 60 0 0 255 20.0 440 70 120 60 0 0 255\n" +
                    "motion elli 20.0 440 70 120 60 0 0 255 50.0 440 250 120 60 0 0 255\n" +
                    "motion elli 50.0 440 250 120 60 0 0 255 70.0 440 370 120 60 0 170 85\n" +
                    "motion elli 70.0 440 370 120 60 0 170 85 80.0 440 370 120 60 0 255 0\n" +
                    "motion elli 80.0 440 370 120 60 0 255 0 100.0 440 370 120 60 0 255 0\n",
            animator.toString());
  }
}
