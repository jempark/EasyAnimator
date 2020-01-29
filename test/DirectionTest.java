import org.junit.Before;
import org.junit.Test;

import java.awt.Color;

import cs3500.model.animator.model.Direction;
import cs3500.model.animator.util.Posn;


import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;

/**
 * Tests for the Direction class.
 */
public class DirectionTest {
  Posn startPosition;
  Posn finalPosition;
  int startWidth;
  int finalWidth;
  int startHeight;
  int finalHeight;
  Color startColor;
  Color finalColor;
  int startTime;
  int endTime;
  Direction direction;

  /**
   * Initializes variables to test for the Directions.
   */
  @Before
  public void initData() {
    startPosition = new Posn(10, 10);
    finalPosition = new Posn(20, 30);
    startWidth = 3;
    finalWidth = 5;
    startHeight = 11;
    finalHeight = 10;
    startColor = new Color(20, 120, 244);
    finalColor = new Color(255, 0, 120);
    startTime = 0;
    endTime = 20;
    direction = new Direction(startPosition, finalPosition, startWidth, finalWidth, startHeight,
            finalHeight, startColor, finalColor, startTime, endTime);
  }

  @Test
  public void testGetStartPosition() {
    assertEquals(startPosition, direction.getStartPosition());
  }

  @Test
  public void testGetFinalPosition() {
    assertEquals(finalPosition, direction.getFinalPosition());
  }

  @Test
  public void testGetStartWidth() {
    assertEquals(startWidth, direction.getStartWidth());
  }

  @Test
  public void testGetFinalWidth() {
    assertEquals(finalWidth, direction.getFinalWidth());
  }

  @Test
  public void testGetStartHeight() {
    assertEquals(startHeight, direction.getStartHeight());
  }

  @Test
  public void testGetFinalHeight() {
    assertEquals(finalHeight, direction.getFinalHeight());
  }

  @Test
  public void testGetStartColor() {
    assertEquals(startColor, direction.getStartColor());
  }

  @Test
  public void testGetFinalColor() {
    assertEquals(finalColor, direction.getFinalColor());
  }

  @Test
  public void testGetStartTime() {
    assertEquals(startTime, direction.getStartTime(), 0.1);
  }

  @Test
  public void testGetEndTime() {
    assertEquals(endTime, direction.getEndTime(), 0.1);
  }

  @Test
  public void testNullStartPosnError() {
    try {
      direction = new Direction(null, finalPosition, startWidth, finalWidth, startHeight,
              finalHeight, startColor, finalColor, startTime, endTime);
      fail("Did not pass");
    } catch (IllegalArgumentException e) {
      assertEquals("Position can't be null", e.getMessage());
    }
  }

  @Test
  public void testNullFinalPosnError() {
    try {
      direction = new Direction(startPosition, null, startWidth, finalWidth, startHeight,
              finalHeight, startColor, finalColor, startTime, endTime);
      fail("Did not pass");
    } catch (IllegalArgumentException e) {
      assertEquals("Position can't be null", e.getMessage());
    }
  }

  @Test
  public void testInvalidStartWidth0() {
    try {
      direction = new Direction(startPosition, finalPosition, 0, finalWidth, startHeight,
              finalHeight, startColor, finalColor, startTime, endTime);
      fail("Did not pass");
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid Scaling.", e.getMessage());
    }
  }

  @Test
  public void testInvalidNegativeStartWidth() {
    try {
      direction = new Direction(startPosition, finalPosition, -2, finalWidth, startHeight,
              finalHeight, startColor, finalColor, startTime, endTime);
      fail("Did not pass");
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid Scaling.", e.getMessage());
    }
  }

  @Test
  public void testInvalidFinalWidth0() {
    try {
      direction = new Direction(startPosition, finalPosition, startWidth, 0, startHeight,
              finalHeight, startColor, finalColor, startTime, endTime);
      fail("Did not pass");
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid Scaling.", e.getMessage());
    }
  }

  @Test
  public void testInvalidNegativeFinalWidth() {
    try {
      direction = new Direction(startPosition, finalPosition, startWidth, -1, startHeight,
              finalHeight, startColor, finalColor, startTime, endTime);
      fail("Did not pass");
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid Scaling.", e.getMessage());
    }
  }

  @Test
  public void testInvalidStartHeight0() {
    try {
      direction = new Direction(startPosition, finalPosition, startWidth, finalWidth, 0,
              finalHeight, startColor, finalColor, startTime, endTime);
      fail("Did not pass");
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid Scaling.", e.getMessage());
    }
  }

  @Test
  public void testInvalidNegativeStartHeight() {
    try {
      direction = new Direction(startPosition, finalPosition, startWidth, finalWidth, -10,
              finalHeight, startColor, finalColor, startTime, endTime);
      fail("Did not pass");
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid Scaling.", e.getMessage());
    }
  }

  @Test
  public void testInvalidFinalHeight0() {
    try {
      direction = new Direction(startPosition, finalPosition, startWidth, finalWidth, startHeight,
              0, startColor, finalColor, startTime, endTime);
      fail("Did not pass");
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid Scaling.", e.getMessage());
    }
  }

  @Test
  public void testInvalidNegativeFinalHeight() {
    try {
      direction = new Direction(startPosition, finalPosition, startWidth, -2, startHeight,
              -5, startColor, finalColor, startTime, endTime);
      fail("Did not pass");
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid Scaling.", e.getMessage());
    }
  }

  @Test
  public void testNullFinalColor() {
    try {
      direction = new Direction(startPosition, finalPosition, startWidth, finalWidth, startHeight,
              finalHeight, startColor, null, startTime, endTime);
      fail("Did not pass");
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid Color - Cannot be null", e.getMessage());
    }
  }

  @Test
  public void startTimeNegative() {
    try {
      direction = new Direction(startPosition, finalPosition, startWidth, finalWidth, startHeight,
              finalHeight, startColor, finalColor, -1, endTime);
      fail("Did not pass");
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid Start/End Time- Negative time", e.getMessage());
    }
  }

  @Test
  public void endTimeNegative() {
    try {
      direction = new Direction(startPosition, finalPosition, startWidth, finalWidth, startHeight,
              finalHeight, startColor, finalColor, -100, -20);
      fail("Did not pass");
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid Start/End Time- Negative time", e.getMessage());
    }
  }

  @Test
  public void testStartGreatThanEndTime() {
    try {
      direction = new Direction(startPosition, finalPosition, startWidth, finalWidth, startHeight,
              finalHeight, startColor, finalColor, 10, 5);
      fail("Did not pass");
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid Start/End Time- Start cannot be greater than End",
              e.getMessage());
    }
  }

  @Test
  public void testToString() {
    assertEquals("0.0 10 10 3 11 20 120 244 20.0 20 30 5 10 255 0 120\n",
            direction.toString());
  }
}
