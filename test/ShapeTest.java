import org.junit.Before;
import org.junit.Test;

import java.awt.Color;

import cs3500.model.animator.util.Posn;

import cs3500.model.shape.IShape;
import cs3500.model.shape.Ellipse;
import cs3500.model.shape.Rectangle;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;

/**
 * Tests for the Shape Class.
 */
public class ShapeTest {
  IShape r1;
  IShape r2;
  IShape r3;
  IShape e1;
  IShape e2;

  /**
   * Initializes Shapes to test for the Shape class.
   */
  @Before
  public void initData() {
    r1 = new Rectangle("rec1", new Color(255, 0, 0), new Posn(200, 200),
            50, 100);

    r2 = new Rectangle("rec2", new Color(0, 255, 144), new Posn(100, 100),
            25, 25);

    e1 = new Ellipse("ell1", new Color(0, 0, 255), new Posn(440, 70),
            120, 60);

    e2 = new Ellipse("ell2", new Color(255, 10, 255), new Posn(200, 250),
            40, 30);
  }

  @Test
  public void testSimpleRectangleConstructor() {
    r3 = new Rectangle("rec");

    assertEquals("rec", r3.getID());
    assertEquals(new Color(0, 0, 0), r3.getColor());
    assertEquals(1, r3.getWidth());
    assertEquals(1, r3.getHeight());
    assertEquals(new Posn(0, 0), r3.getPosition());
  }

  @Test
  public void testSimpleEllipseConstructor() {
    e1 = new Ellipse("ell");

    assertEquals("ell", e1.getID());
    assertEquals(new Color(0, 0, 0), e1.getColor());
    assertEquals(1, e1.getWidth());
    assertEquals(1, e1.getHeight());
    assertEquals(new Posn(0, 0), e1.getPosition());
  }

  @Test
  public void testRectangleHeightZero() {
    try {
      r1 = new Rectangle("rec", new Color(255, 0, 0), new Posn(200, 200),
              50, 0);
      fail("Did not pass.");
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid Height and/or Width", e.getMessage());
    }
  }

  @Test
  public void testRectangleHeightNeg() {
    try {
      r1 = new Rectangle("rec", new Color(255, 0, 0), new Posn(200, 200),
              50, -50);
      fail("Did not pass.");
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid Height and/or Width", e.getMessage());
    }
  }

  @Test
  public void testRectangleWidthZero() {
    try {
      r1 = new Rectangle("rec", new Color(255, 0, 0), new Posn(200, 200),
              0, 50);
      fail("Did not pass.");
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid Height and/or Width", e.getMessage());
    }
  }

  @Test
  public void testRectangleWidthNeg() {
    try {
      r1 = new Rectangle("rec", new Color(255, 0, 0), new Posn(200, 200),
              -20, 50);
      fail("Did not pass.");
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid Height and/or Width", e.getMessage());
    }
  }

  @Test
  public void testRectangleWidthHeightZero() {
    try {
      r1 = new Rectangle("rec", new Color(255, 0, 0), new Posn(200, 200),
              0, 0);
      fail("Did not pass.");
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid Height and/or Width", e.getMessage());
    }
  }

  @Test
  public void testRectangleWidthHeightNeg() {
    try {
      r1 = new Rectangle("rec", new Color(255, 0, 0), new Posn(200, 200),
              -20, -20);
      fail("Did not pass.");
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid Height and/or Width", e.getMessage());
    }
  }

  @Test
  public void testRectanglePosnNull() {
    try {
      r1 = new Rectangle("rec", new Color(255, 0, 0), null, 50, 50);
      fail("Did not pass.");
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid Position", e.getMessage());
    }
  }

  @Test
  public void testRectangleColorRedNeg() {
    try {
      r1 = new Rectangle("rec", new Color(-1, 0, 0), new Posn(200, 200),
              500, 50);
      fail("Did not pass.");
    } catch (IllegalArgumentException e) {
      assertEquals("Color parameter outside of expected range: Red", e.getMessage());
    }
  }

  @Test
  public void testRectangleColorGreenNeg() {
    try {
      r1 = new Rectangle("rec", new Color(0, -1, 0), new Posn(200, 200),
              500, 50);
      fail("Did not pass.");
    } catch (IllegalArgumentException e) {
      assertEquals("Color parameter outside of expected range: Green", e.getMessage());
    }
  }

  @Test
  public void testRectangleColorBlueNeg() {
    try {
      r1 = new Rectangle("rec", new Color(0, 0, -1), new Posn(200, 200),
              500, 50);
      fail("Did not pass.");
    } catch (IllegalArgumentException e) {
      assertEquals("Color parameter outside of expected range: Blue", e.getMessage());
    }
  }

  @Test
  public void testRectangleColorRedGreenNeg() {
    try {
      r1 = new Rectangle("rec", new Color(-1, -1, 0), new Posn(200, 200),
              500, 50);
      fail("Did not pass.");
    } catch (IllegalArgumentException e) {
      assertEquals("Color parameter outside of expected range: Red Green",
              e.getMessage());
    }
  }

  @Test
  public void testRectangleColorRedBlueNeg() {
    try {
      r1 = new Rectangle("rec", new Color(-1, 0, -1), new Posn(200, 200),
              500, 50);
      fail("Did not pass.");
    } catch (IllegalArgumentException e) {
      assertEquals("Color parameter outside of expected range: Red Blue",
              e.getMessage());
    }
  }

  @Test
  public void testRectangleColorGreenBlueNeg() {
    try {
      r1 = new Rectangle("rec", new Color(0, -1, -1), new Posn(200, 200),
              500, 50);
      fail("Did not pass.");
    } catch (IllegalArgumentException e) {
      assertEquals("Color parameter outside of expected range: Green Blue",
              e.getMessage());
    }
  }

  @Test
  public void testRectangleColorAllNeg() {
    try {
      r1 = new Rectangle("rec", new Color(-1, -1, -1), new Posn(200, 200),
              500, 50);
      fail("Did not pass.");
    } catch (IllegalArgumentException e) {
      assertEquals("Color parameter outside of expected range: Red Green Blue",
              e.getMessage());
    }
  }

  @Test
  public void testRectangleColorNull() {
    try {
      r1 = new Rectangle("rec", null, new Posn(200, 200),
              500, 50);
      fail("Did not pass.");
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid Color - Cannot be null",
              e.getMessage());
    }
  }

  @Test
  public void testEmptyRectangleID() {
    try {
      r1 = new Rectangle("", new Color(255, 0, 0), new Posn(200, 200),
              500, 50);
      fail("Did not pass.");
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid Shape ID",
              e.getMessage());
    }
  }

  @Test
  public void testEllipseHeightZero() {
    try {
      e1 = new Ellipse("ell", new Color(255, 0, 0), new Posn(200, 200),
              50, 0);
      fail("Did not pass.");
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid Height and/or Width", e.getMessage());
    }
  }

  @Test
  public void testEllipseHeightNeg() {
    try {
      e1 = new Ellipse("ell", new Color(255, 0, 0), new Posn(200, 200),
              50, -50);
      fail("Did not pass.");
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid Height and/or Width", e.getMessage());
    }
  }

  @Test
  public void testEllipseWidthZero() {
    try {
      e1 = new Ellipse("ell", new Color(255, 0, 0), new Posn(200, 200),
              0, 50);
      fail("Did not pass.");
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid Height and/or Width", e.getMessage());
    }
  }

  @Test
  public void testEllipseWidthNeg() {
    try {
      e1 = new Ellipse("ell", new Color(255, 0, 0), new Posn(200, 200),
              -20, 50);
      fail("Did not pass.");
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid Height and/or Width", e.getMessage());
    }
  }

  @Test
  public void testEllipseWidthHeightZero() {
    try {
      e1 = new Ellipse("ell", new Color(255, 0, 0), new Posn(200, 200),
              0, 0);
      fail("Did not pass.");
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid Height and/or Width", e.getMessage());
    }
  }

  @Test
  public void testEllipseWidthHeightNeg() {
    try {
      e1 = new Ellipse("ell", new Color(255, 0, 0), new Posn(200, 200),
              -20, -20);
      fail("Did not pass.");
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid Height and/or Width", e.getMessage());
    }
  }

  @Test
  public void testEllipsePosnNull() {
    try {
      e1 = new Ellipse("ell", new Color(255, 0, 0), null, 50, 50);
      fail("Did not pass.");
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid Position", e.getMessage());
    }
  }

  @Test
  public void testEllipseColorRedNeg() {
    try {
      e1 = new Ellipse("ell", new Color(-1, 0, 0), new Posn(200, 200),
              500, 50);
      fail("Did not pass.");
    } catch (IllegalArgumentException e) {
      assertEquals("Color parameter outside of expected range: Red", e.getMessage());
    }
  }

  @Test
  public void testEllipseColorGreenNeg() {
    try {
      e1 = new Ellipse("ell", new Color(0, -1, 0), new Posn(200, 200),
              500, 50);
      fail("Did not pass.");
    } catch (IllegalArgumentException e) {
      assertEquals("Color parameter outside of expected range: Green", e.getMessage());
    }
  }

  @Test
  public void testEllipseColorBlueNeg() {
    try {
      e1 = new Ellipse("ell", new Color(0, 0, -1), new Posn(200, 200),
              500, 50);
      fail("Did not pass.");
    } catch (IllegalArgumentException e) {
      assertEquals("Color parameter outside of expected range: Blue", e.getMessage());
    }
  }

  @Test
  public void testEllipseColorRedGreenNeg() {
    try {
      e1 = new Ellipse("ell", new Color(-1, -1, 0), new Posn(200, 200),
              500, 50);
      fail("Did not pass.");
    } catch (IllegalArgumentException e) {
      assertEquals("Color parameter outside of expected range: Red Green",
              e.getMessage());
    }
  }

  @Test
  public void testEllipseColorRedBlueNeg() {
    try {
      e1 = new Ellipse("ell", new Color(-1, 0, -1), new Posn(200, 200),
              500, 50);
      fail("Did not pass.");
    } catch (IllegalArgumentException e) {
      assertEquals("Color parameter outside of expected range: Red Blue",
              e.getMessage());
    }
  }

  @Test
  public void testEllipseColorGreenBlueNeg() {
    try {
      e1 = new Ellipse("ell", new Color(0, -1, -1), new Posn(200, 200),
              500, 50);
      fail("Did not pass.");
    } catch (IllegalArgumentException e) {
      assertEquals("Color parameter outside of expected range: Green Blue",
              e.getMessage());
    }
  }

  @Test
  public void testEllipseColorAllNeg() {
    try {
      e1 = new Ellipse("ell", new Color(-1, -1, -1), new Posn(200, 200),
              500, 50);
      fail("Did not pass.");
    } catch (IllegalArgumentException e) {
      assertEquals("Color parameter outside of expected range: Red Green Blue",
              e.getMessage());
    }
  }

  @Test
  public void testEllipseColorNull() {
    try {
      e1 = new Ellipse("ell", null, new Posn(200, 200),
              500, 50);
      fail("Did not pass.");
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid Color - Cannot be null",
              e.getMessage());
    }
  }

  @Test
  public void testEmptyEllipseID() {
    try {
      e1 = new Ellipse("", new Color(255, 0, 0), new Posn(200, 200),
              500, 50);
      fail("Did not pass.");
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid Shape ID",
              e.getMessage());
    }
  }

  @Test
  public void testGetRectangleName() {
    assertEquals("Rectangle", r1.getShape());
  }

  @Test
  public void testGetIDRectangle() {
    assertEquals("rec1",
            r1.getID());
  }

  @Test
  public void testGetColorRectangle() {
    assertEquals(new Color(0, 255, 144),
            r2.getColor());
  }

  @Test
  public void testGetPositionRectangle() {
    assertEquals(new Posn(100, 100), r2.getPosition());
  }

  @Test
  public void testGetHeightRectangle() {
    assertEquals(25, r2.getHeight());
  }

  @Test
  public void testGetWidthRectangle() {
    assertEquals(25, r2.getWidth());
  }

  @Test
  public void testGetEllipseName() {
    assertEquals("Ellipse", e1.getShape());
  }

  @Test
  public void testGetIDEllipse() {
    assertEquals("ell1",
            e1.getID());
  }

  @Test
  public void testGetColorEllipse() {
    assertEquals(new Color(255, 10, 255),
            e2.getColor());
  }

  @Test
  public void testGetPositionEllipse() {
    assertEquals(new Posn(200, 250), e2.getPosition());
  }

  @Test
  public void testGetHeightEllipse() {
    assertEquals(30, e2.getHeight());
  }

  @Test
  public void testGetWidthEllipse() {
    assertEquals(40, e2.getWidth());
  }
}
