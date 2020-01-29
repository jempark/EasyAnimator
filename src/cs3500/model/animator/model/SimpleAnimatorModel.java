package cs3500.model.animator.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import cs3500.model.animator.util.AnimationBuilder;

import cs3500.model.animator.util.Posn;
import cs3500.model.shape.Ellipse;
import cs3500.model.shape.IShape;
import cs3500.model.shape.Rectangle;

/**
 * A very simple animator class that extends all of the methods from the Abstract animator class.
 */
public class SimpleAnimatorModel extends AAnimatorModel {

  /**
   * Added since HW5. Constructor for SimpleAnimatorModel that takes in the upper left coordinates,
   * height, and width of the Animator canvas.
   */
  public SimpleAnimatorModel(int boundX, int boundY, int boundWidth, int boundHeight) {
    super(boundX, boundY, boundWidth, boundHeight);
  }

  /**
   * Added since HW5. Default Constructor for SimpleAnimatorModel.
   */
  public SimpleAnimatorModel() {
    super();
  }

  /**
   * Added since HW6. Copy constructor for SimpleAnimator model that takes in an already existing
   * model, and makes a copy.
   *
   * @param model the model to be copied
   */
  public SimpleAnimatorModel(IAnimatorModel model) {
    super(model);
  }

  /**
   * Builder class, implements AnimationBuilder in a way in which IAnimatorModel can understand.
   * Thus, allowing code to work seamlessly with given starter code and input files.
   */
  public static final class Builder implements AnimationBuilder<IAnimatorModel> {
    ArrayList<IShape> shapeList;
    LinkedHashMap<IShape, ArrayList<KeyFrame>> frameHashMap;
    int boundX;
    int boundY;
    int boundWidth;
    int boundHeight;

    /**
     * Builder method to initialize the builder. Contains variables shapeList: the list of Shapes
     * the Animation will hold. frameHashMap: the list of KeyFrames the Shapes will hold (to be
     * turned into Directions). boundX and boundY: the upper left coordinates of the Animator
     * canvas. boundWidth and boundHeight: the width and height of the Animator canvas.
     */
    public Builder() {
      shapeList = new ArrayList<>();
      frameHashMap = new LinkedHashMap<>();
      boundX = 0;
      boundY = 0;
      boundWidth = 1000;
      boundHeight = 1000;
    }

    /**
     * Constructs a final document.
     *
     * @return the newly constructed document
     */
    public IAnimatorModel build() {
      IAnimatorModel model = new SimpleAnimatorModel(boundX, boundY, boundWidth, boundHeight);

      sortKeyFrames();

      LinkedHashMap<IShape, ArrayList<Direction>> events = new LinkedHashMap<>();

      for (IShape s : frameHashMap.keySet()) {
        events.put(s, makeDirectionList(frameHashMap.get(s)));
      }

      for (IShape s : events.keySet()) {
        model.addShape(s);
        for (Direction d : events.get(s)) {
          model.addDirection(s, d);
        }
      }

      return model;
    }

    /**
     * Helper method for build(), sorts KeyFrames for all Shapes in order of ascending Time and
     * deletes repeating KeyFrames.
     */
    void sortKeyFrames() {
      LinkedHashMap<IShape, ArrayList<KeyFrame>> sortedFrameHashMap = new LinkedHashMap<>();
      LinkedHashMap<IShape, ArrayList<KeyFrame>> uniqueFrameHashMap = new LinkedHashMap<>();
      for (IShape s : frameHashMap.keySet()) {
        sortedFrameHashMap.put(s, sortFrame(frameHashMap.get(s)));

        int i = 0;
        KeyFrame prevKeyFrame = null;

        ArrayList<KeyFrame> duplicatesRemoved = new ArrayList<>();

        for (KeyFrame k : sortedFrameHashMap.get(s)) {
          if (i > 0) {
            if (!k.equals(prevKeyFrame)) {
              duplicatesRemoved.add(k);
            }
          } else {
            duplicatesRemoved.add(k);
          }
          prevKeyFrame = k;
          i++;
        }
        uniqueFrameHashMap.put(s, duplicatesRemoved);
      }
      frameHashMap = uniqueFrameHashMap;
    }

    /**
     * Helper method for sortKeyFrames() -> build(), sorts an array of KeyFrames in order of
     * ascending Time.
     *
     * @param k ArrayList of KeyFrames
     * @return the sorted ArrayList of KeyFrames
     */
    ArrayList<KeyFrame> sortFrame(ArrayList<KeyFrame> k) {
      KeyFrame temp1;
      KeyFrame temp2;
      for (int i = 0; i < k.size(); i++) {
        for (int y = i + 1; y < k.size(); y++) {
          if (k.get(i).getTime() > k.get(y).getTime()) {
            temp1 = k.get(i).getCopyKeyFrame();
            temp2 = k.get(y).getCopyKeyFrame();
            k.set(i, temp2);
            k.set(y, temp1);
          }
        }
      }
      return k;
    }

    /**
     * Helper method for build() method, turns ArrayList of KeyFrames into ArrayList of Directions.
     *
     * @param keys an ArrayList of sorted KeyFrames
     * @return an ArrayList of Directions corresponding to KeyFrames
     */
    ArrayList<Direction> makeDirectionList(ArrayList<KeyFrame> keys) {
      ArrayList<Direction> directionList = new ArrayList<>();
      int i = 0;
      KeyFrame prevKeyFrame = null;

      for (KeyFrame k : keys) {
        if (i > 0) {
          directionList.add(makeDirection(prevKeyFrame, k));
        }
        prevKeyFrame = k;
        i++;
      }
      return directionList;
    }

    /**
     * Helper class for makeDirectionList() -> build(), creates a Direction out of two KeyFrames.
     *
     * @param start initialKeyFrame
     * @param end   finalKeyFrame
     * @return a Direction, with initial values corresponding to start KeyFrame, and final values
     *         corresponding to end KeyFrame
     */
    protected Direction makeDirection(KeyFrame start, KeyFrame end) {
      return new Direction(start.getPosition(), end.getPosition(),
              start.getWidth(), end.getWidth(),
              start.getHeight(), end.getHeight(),
              start.getColor(), end.getColor(),
              start.getTime(), end.getTime());
    }

    /**
     * Specify the bounding box to be used for the animation.
     *
     * @param x      The leftmost x value
     * @param y      The topmost y value
     * @param width  The width of the bounding box
     * @param height The height of the bounding box
     * @return This {@link AnimationBuilder}
     */
    public AnimationBuilder<IAnimatorModel> setBounds(int x, int y, int width, int height) {
      boundX = x;
      boundY = y;
      boundWidth = width;
      boundHeight = height;
      return this;
    }

    /**
     * Adds a new shape to the growing document.
     *
     * @param name The unique name of the shape to be added. No shape with this name should already
     *             exist.
     * @param type The type of shape (e.g. "ellipse", "rectangle") to be added. The set of supported
     *             shapes is unspecified, but should include "ellipse" and "rectangle" as a
     *             minimum.
     * @return This {@link AnimationBuilder}
     */
    public AnimationBuilder<IAnimatorModel> declareShape(String name, String type) {
      IShape shape;
      if (type.equals("ellipse")) {
        shape = new Ellipse(name);
      } else if (type.equals("rectangle")) {
        shape = new Rectangle(name);
      } else {
        throw new IllegalArgumentException("Error - not a valid Shape input");
      }
      shapeList.add(shape);
      frameHashMap.put(shape, new ArrayList<>());
      return this;
    }

    /**
     * Adds a transformation to the growing document.
     *
     * @param name The name of the shape (added with {@link AnimationBuilder#declareShape})
     * @param t1   The start time of this transformation
     * @param x1   The initial x-position of the shape
     * @param y1   The initial y-position of the shape
     * @param w1   The initial width of the shape
     * @param h1   The initial height of the shape
     * @param r1   The initial red color-value of the shape
     * @param g1   The initial green color-value of the shape
     * @param b1   The initial blue color-value of the shape
     * @param t2   The end time of this transformation
     * @param x2   The final x-position of the shape
     * @param y2   The final y-position of the shape
     * @param w2   The final width of the shape
     * @param h2   The final height of the shape
     * @param r2   The final red color-value of the shape
     * @param g2   The final green color-value of the shape
     * @param b2   The final blue color-value of the shape
     * @return This {@link AnimationBuilder}
     */
    public AnimationBuilder<IAnimatorModel> addMotion(String name, int t1, int x1,
                                                      int y1, int w1, int h1,
                                                      int r1, int g1, int b1,
                                                      int t2, int x2, int y2,
                                                      int w2, int h2, int r2, int g2, int b2) {
      Posn startPos = new Posn(x1, y1);
      Posn finalPos = new Posn(x2, y2);
      Color startColor = new Color(r1, g1, b1);
      Color finalColor = new Color(r2, g2, b2);
      KeyFrame start = new KeyFrame(startPos, w1, h1, startColor, t1);
      KeyFrame end = new KeyFrame(finalPos, w2, h2, finalColor, t2);

      for (IShape s : frameHashMap.keySet()) {
        if (s.getID().equals(name)) {
          frameHashMap.get(s).add(start);
          frameHashMap.get(s).add(end);
          return this;
        }
      }
      throw new IllegalArgumentException("Error - no such shape ID");
    }

    /**
     * Adds an individual keyframe to the growing document.
     *
     * @param name The name of the shape (added with {@link AnimationBuilder#declareShape})
     * @param t    The time for this keyframe
     * @param x    The x-position of the shape
     * @param y    The y-position of the shape
     * @param w    The width of the shape
     * @param h    The height of the shape
     * @param r    The red color-value of the shape
     * @param g    The green color-value of the shape
     * @param b    The blue color-value of the shape
     * @return This {@link AnimationBuilder}
     */
    public AnimationBuilder<IAnimatorModel> addKeyframe(String name, int t, int x, int y, int w,
                                                        int h, int r, int g, int b) {
      Posn pos = new Posn(x, y);
      Color color = new Color(r, g, b);

      KeyFrame key = new KeyFrame(pos, w, h, color, t);

      for (IShape s : frameHashMap.keySet()) {
        if (s.getID().equals(name)) {
          frameHashMap.get(s).add(key);
          return this;
        }
      }
      throw new IllegalArgumentException("Error - no such shape ID");
    }
  }
}

