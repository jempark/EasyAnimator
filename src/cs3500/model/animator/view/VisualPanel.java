package cs3500.model.animator.view;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.swing.JPanel;

import cs3500.model.animator.model.Direction;
import cs3500.model.shape.IShape;

/**
 * Helper class for VisualView, generates Shapes at each tick.
 */
public class VisualPanel extends JPanel {
  private LinkedHashMap<IShape, ArrayList<Direction>> map;
  private int tick;

  /**
   * Constructor for VisualPanel.
   *
   * @param map input argument for Animator Model, has list of shapes and directions
   * @throws IllegalArgumentException if input is null
   */
  public VisualPanel(LinkedHashMap<IShape, ArrayList<Direction>> map)
          throws IllegalArgumentException {
    super();

    if (map == null) {
      throw new IllegalArgumentException("Error - null map");
    }
    this.map = map;
    tick = 0;
  }

  /**
   * Overrides built in paint component to produce and tween shapes.
   *
   * @param g Graphics input
   * @throws IllegalArgumentException if Graphics input is null
   */
  protected void paintComponent(Graphics g) throws IllegalArgumentException {
    if (g == null) {
      throw new IllegalArgumentException("Error - null Graphics");
    }

    super.paintComponent(g);
    Graphics2D g2D = (Graphics2D) g;
    g2D.setColor(Color.BLACK);

    for (IShape s : map.keySet()) {
      for (Direction d : map.get(s)) {
        if (tick >= d.getStartTime() && tick <= d.getEndTime()) {
          String shapeType = s.getShape();
          double currXPosition = tween(d.getStartPosition().getX(),
                  d.getFinalPosition().getX(),
                  d.getStartTime(), d.getEndTime());
          double currYPosition = tween(d.getStartPosition().getY(),
                  d.getFinalPosition().getY(),
                  d.getStartTime(), d.getEndTime());
          double currWidth = tween(d.getStartWidth(),
                  d.getFinalWidth(),
                  d.getStartTime(), d.getEndTime());
          double currHeight = tween(d.getStartHeight(),
                  d.getFinalHeight(),
                  d.getStartTime(), d.getEndTime());
          Color currColor = tweenColor(d.getStartColor(),
                  d.getFinalColor(),
                  d.getStartTime(), d.getEndTime());
          draw(shapeType, currXPosition, currYPosition, currWidth, currHeight, currColor, g2D);
        }
      }
    }
  }

  /**
   * Helper function for paintComponent to tween any parameters.
   *
   * @param startState start State of parameter
   * @param endState   end State of parameter
   * @param startTime  start time of Direction parameter is in
   * @param endTime    end time of Direction parameter is in
   * @return the tween value at time tick (double)
   */
  private double tween(int startState, int endState, double startTime, double endTime) {
    return startState * 1.0 * (endTime - tick) / (endTime - startTime)
            + endState * 1.0 * (tick * 1.0 - startTime) / (endTime - startTime);
  }

  /**
   * Helper function for paintComponent to tween Colors.
   *
   * @param startColor the Start Color of parameter
   * @param endColor   the End Color of parameter
   * @param startTime  the Start Time of parameter
   * @param endTime    the End Time of parameter
   * @return the tween Color at time tick (Color)
   */
  private Color tweenColor(Color startColor, Color endColor, double startTime, double endTime) {
    int currR = (int) Math.round(tween(startColor.getRed(), endColor.getRed(), startTime, endTime));
    int currG = (int) Math.round(tween(startColor.getGreen(), endColor.getGreen(), startTime,
            endTime));
    int currB = (int) Math.round(tween(startColor.getBlue(), endColor.getBlue(), startTime,
            endTime));
    return new Color(currR, currG, currB);
  }

  /**
   * Helper function for paintComponent to draw given Shapes in jSwing.
   *
   * @param shapeType     the type of Shape
   * @param currXPosition the current X position of the Shape
   * @param currYPosition the current Y position of the Shape
   * @param currWidth     the current Width of the Shape
   * @param currHeight    the current Height of the Shape
   * @param currColor     the current Color of the Shape
   * @param g2D           the Graphic2D object
   */
  void draw(String shapeType, double currXPosition, double currYPosition,
            double currWidth, double currHeight, Color currColor, Graphics2D g2D) {
    if (g2D == null) {
      throw new IllegalArgumentException("Error - Graphic is null");
    }
    if (shapeType.equals("Rectangle")) {
      g2D.setColor(currColor);
      g2D.fillRect((int) currXPosition, (int) currYPosition,
              (int) currWidth, (int) currHeight);
    }
    if (shapeType.equals("Ellipse")) {
      g2D.setColor(currColor);
      g2D.fillOval((int) currXPosition, (int) currYPosition,
              (int) currWidth, (int) currHeight);
    }
  }

  /**
   * Function to animate shapes, by ticking forward.
   */
  public void animateShapes() {
    tick++;
  }

  /**
   * Function to restart the animation by setting the tick to 0.
   */
  public void restartAnimation() {
    tick = 0;
  }

  /**
   * Function to return the current tick of the animation.
   *
   * @return the current tick of the animation.
   */
  public int getTick() {
    return tick;
  }

  /**
   * Function to set tick to a specified int (for Scrubber panel).
   *
   * @param inputTick the tick to set the animation to
   */
  public void setTick(int inputTick) {
    tick = inputTick;
  }
}