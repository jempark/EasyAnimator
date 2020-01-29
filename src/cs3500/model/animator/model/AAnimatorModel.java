package cs3500.model.animator.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import cs3500.model.animator.util.Posn;
import cs3500.model.shape.IShape;

/**
 * Abstract class for animator. The Animator Model stores shapes and all the directions enforced on
 * these shapes in a single animation.
 */
public abstract class AAnimatorModel implements IAnimatorModel {
  protected LinkedHashMap<IShape, ArrayList<Direction>> events;
  protected ArrayList<IShape> shapeList;
  protected ArrayList<String> existingShapeID;
  int boundX;
  int boundY;
  int boundWidth;
  int boundHeight;

  /**
   * Added since HW5. Constructor for abstract animator. Takes in bounding values and initializes
   * list of events and shapes.
   *
   * @param boundX      upper left x coordinate of bounding box
   * @param boundY      upper left y coordinate of bounding box
   * @param boundWidth  bounding box width
   * @param boundHeight bounding box height
   */
  public AAnimatorModel(int boundX, int boundY, int boundWidth, int boundHeight) {
    this.events = new LinkedHashMap<>();
    this.shapeList = new ArrayList<>();
    this.existingShapeID = new ArrayList<>();
    this.boundX = boundX;
    this.boundY = boundY;
    this.boundWidth = boundWidth;
    this.boundHeight = boundHeight;
  }


  /**
   * Added since HW6. Copy constructor for SimpleAnimator model that takes in an already existing
   * model, and makes a copy
   *
   * @param model the model to be copied
   */
  public AAnimatorModel(IAnimatorModel model) {
    this.events = model.getCopyDirectionMap();
    this.shapeList = model.getCopyShapeList();
    this.existingShapeID = model.getShapeIDList();
    this.boundX = model.getBoundX();
    this.boundY = model.getBoundY();
    this.boundWidth = model.getBoundWidth();
    this.boundHeight = model.getBoundHeight();
  }

  /**
   * Added since HW5. Default Constructor for abstract animator. Initializes list of events and
   * shapes.
   */
  public AAnimatorModel() {
    this(0, 0, 1000, 1000);
  }

  /**
   * Method to add a Shape to the animator.
   *
   * @param shape representation of a Shape (rectangle, eclipse, etc.)
   * @throws IllegalArgumentException if Shape added is null
   */
  @Override
  public void addShape(IShape... shape) {
    for (IShape s : shape) {
      if (s == null || existingShapeID.contains(s.getID())) {
        throw new IllegalArgumentException("Shape cannot be null and every shape must have unique" +
                "IDs.");
      }
      shapeList.add(s);
      events.put(s, new ArrayList<>());
      existingShapeID.add(s.getID());
    }
  }

  /**
   * Method to remove a Shape from the animator.
   *
   * @param shape representation of a Shape (rectangle, eclipse, etc.)
   */
  @Override
  public void removeShape(IShape... shape) {
    if (shapeList.isEmpty() || events.isEmpty()) {
      throw new IllegalArgumentException("Cannot remove from an empty List/Hash Map");
    }
    for (IShape s : shape) {
      if (!shapeList.contains(s)) {
        throw new IllegalArgumentException("Cannot remove nonexistent shape");
      }
      shapeList.remove(s);
      events.remove(s);
      existingShapeID.remove(s.getID());
    }
  }

  /**
   * Added since HW6. Returns the Shape in Animator based of the Shape ID user inputs.
   *
   * @param shapeID the given ID of the Shape
   * @return the Shape corresponding to that shape ID
   */
  public IShape getShapeWithID(String shapeID) {
    for (IShape s : events.keySet()) {
      if (s.getID().equals(shapeID)) {
        return s;
      }
    }
    throw new IllegalArgumentException("Shape ID does not exist");
  }

  /**
   * Added from Hw 6. Method to remove a Shape from the animator given an ID.
   *
   * @param shapeID The specific ID of the shape
   */
  public void removeShapeWithID(String shapeID) {
    IShape removedShape = getShapeWithID(shapeID);
    removeShape(removedShape);
  }

  /**
   * Method to add Directions for a given Shape to events List.
   *
   * @param shape     representation of a Shape (rectangle, eclipse, etc.)
   * @param direction representation of a Direction
   * @throws IllegalArgumentException if there are overlapping directions, added from hw5
   */
  @Override
  public void addDirection(IShape shape, Direction... direction) {
    ArrayList<Direction> tempDirection = new ArrayList<>();
    double prevEndTime = 0;
    for (Direction d : direction) {
      if (d == null) {
        throw new IllegalArgumentException("Direction cannot be null");
      }

      tempDirection.add(d);
    }

    tempDirection = sortDirectionList(tempDirection);

    for (int i = 0; i < tempDirection.size(); i++) {
      if (events.get(shape).isEmpty() || i == 0) {
        events.get(shape).add(tempDirection.get(i));
        prevEndTime = tempDirection.get(i).getEndTime();
      } else if (tempDirection.get(i).getStartTime() == prevEndTime) {
        events.get(shape).add(tempDirection.get(i));
        prevEndTime = tempDirection.get(i).getEndTime();
      } else {
        throw new IllegalArgumentException("Direction cannot have any gaps");
      }
    }
  }

  /**
   * Added since HW5. Helper method that takes in an ArrayList and sorts it by starting time.
   *
   * @param directionList list of directions
   * @return sorted list of directions
   */
  protected ArrayList<Direction> sortDirectionList(ArrayList<Direction> directionList) {
    Direction temp1;
    Direction temp2;
    for (int i = 0; i < directionList.size(); i++) {
      for (int y = i + 1; y < directionList.size(); y++) {
        if (directionList.get(i).getStartTime() > directionList.get(y).getStartTime()) {
          temp1 = directionList.get(i).getCopyDirection();
          temp2 = directionList.get(y).getCopyDirection();
          directionList.set(i, temp2);
          directionList.set(y, temp1);
        }
      }
    }
    return directionList;
  }

  /**
   * Method to remove Directions for a given Shape from event List.
   *
   * @param shape     representation of a Shape (rectangle, ellipse, etc.)
   * @param direction representation of a Direction
   */
  @Override
  public void removeDirection(IShape shape, Direction direction) {
    if (!events.containsKey(shape)) {
      throw new IllegalArgumentException("Cannot remove because shape doesn't exist.");
    }

    if (!events.get(shape).isEmpty() && events.get(shape).contains(direction)) {
      events.get(shape).remove(direction);
    } else {
      throw new IllegalArgumentException("Cannot remove because direction doesn't exist.");
    }
  }

  /**
   * Added since HW5. Returns a String representing the Animation.
   *
   * @return Animation as a String
   */
  @Override
  public String toString() {
    ArrayList<Direction> directionList;
    StringBuilder str = new StringBuilder();
    String shapeType;

    str.append("canvas ");
    str.append(getBoundX());
    str.append(" ");
    str.append(getBoundY());
    str.append(" ");
    str.append(getBoundWidth());
    str.append(" ");
    str.append(getBoundHeight());
    str.append("\n");

    for (int i = 0; i < shapeList.size(); i++) {
      directionList = events.get(shapeList.get(i));

      shapeType = shapeList.get(i).getShape();
      if (shapeType.equals("Rectangle")) {
        str.append("shape ");
        str.append(shapeList.get(i).getID());
        str.append(" rectangle\n");
        for (Direction d : directionList) {
          str.append("motion ");
          str.append(shapeList.get(i).getID());
          str.append(" ");
          str.append(d);
        }
      } else if (shapeType.equals("Ellipse")) {
        str.append("shape ");
        str.append(shapeList.get(i).getID());
        str.append(" ellipse\n");
        for (Direction d : directionList) {
          str.append("motion ");
          str.append(shapeList.get(i).getID());
          str.append(" ");
          str.append(d);
        }
      }
      if (i < shapeList.size() - 1) {
        str.append("\n");
      }
    }
    return str.toString();
  }

  /**
   * Added since HW 5. Creates a new copy of the Linked HashMap representing the Animator's HashMap
   * of events.
   *
   * @return the Linked HashMap of directions with the shapes being the key
   */
  @Override
  public LinkedHashMap<IShape, ArrayList<Direction>> getCopyDirectionMap() {
    LinkedHashMap<IShape, ArrayList<Direction>> copyEvents = new LinkedHashMap<>();

    for (IShape s : shapeList) {
      ArrayList<Direction> copyDirection = new ArrayList<>();
      for (Direction d : events.get(s)) {
        copyDirection.add(d.getCopyDirection());
      }
      copyEvents.put(s.getCopy(), copyDirection);
    }
    return copyEvents;
  }

  /**
   * Added since HW5. Creates a new copy of the Linked HashMap in a way that is rebounded so that
   * the points are correct relative to the canvas, which starts at (0,0) in SVG and Visual View.
   *
   * @return the Linked HashMap of directions with the shapes being the key
   */
  @Override
  public LinkedHashMap<IShape, ArrayList<Direction>> getCopyDirectionMapRebounded() {
    LinkedHashMap<IShape, ArrayList<Direction>> copyEvents = new LinkedHashMap<>();

    for (IShape s : events.keySet()) {
      ArrayList<Direction> copyDirection = new ArrayList<>();
      for (Direction d : events.get(s)) {
        copyDirection.add(reBound(d));
      }
      copyEvents.put(s.getCopy(), copyDirection);
    }
    return copyEvents;
  }

  /**
   * Added since HW5. Creates a new copy of the Shape Array List.
   *
   * @return a copy of an ArrayList of Shapes
   */
  @Override
  public ArrayList<IShape> getCopyShapeList() {
    ArrayList<IShape> copyShapeList = new ArrayList<>();
    for (IShape s : shapeList) {
      copyShapeList.add(s.getCopy());
    }
    return copyShapeList;
  }

  /**
   * Added since HW5. Helper function for getCopyDirectionMapRebounded() to reset Direction so
   * boundX and boundY coordinate is positive, respectively.
   *
   * @param d the input Direction that is being rebounded
   * @return the rebounded Direction
   */
  protected Direction reBound(Direction d) {
    Posn startPosition = d.getStartPosition();
    Posn finalPosition = d.getFinalPosition();

    int adjustX = 0 - boundX;
    int adjustY = 0 - boundY;

    startPosition.setX(startPosition.getX() + adjustX);
    finalPosition.setX(finalPosition.getX() + adjustX);

    startPosition.setY(startPosition.getY() + adjustY);
    finalPosition.setY(finalPosition.getY() + adjustY);

    return new Direction(startPosition, finalPosition, d.getStartWidth(), d.getFinalWidth(),
            d.getStartHeight(), d.getFinalHeight(), d.getStartColor(), d.getFinalColor(),
            d.getStartTime(), d.getEndTime());
  }

  /**
   * Added since HW5. Getter method for Animator Model to return the upper left X coordinate of the
   * canvas.
   *
   * @return the upper left X coordinate of the canvas.
   */
  public int getBoundX() {
    return boundX;
  }

  /**
   * Added since HW5. Getter method for Animator Model to return the upper left Y coordinate of the
   * canvas.
   *
   * @return the upper left Y coordinate of the canvas.
   */
  public int getBoundY() {
    return boundY;
  }

  /**
   * Added since HW5. Getter method for Animator Model to return the Width of the canvas.
   *
   * @return the upper left Width coordinate of the canvas.
   */
  public int getBoundWidth() {
    return boundWidth;
  }

  /**
   * Added since HW5. Getter method for Animator Model to return the Height of the canvas.
   *
   * @return the upper left Height coordinate of the canvas.
   */
  public int getBoundHeight() {
    return boundHeight;
  }

  /**
   * Added since HW6. Getter method for Animator Model to return list of existing Shape IDs.
   *
   * @return ArrayList of current existing shape IDs
   */
  public ArrayList<String> getShapeIDList() {
    return existingShapeID;
  }

  /**
   * Finds the time it takes for the entire animation to complete.
   *
   * @return the last possible tick in the animation.
   */
  public double totalDuration() {
    double total = 0;
    for (IShape s : events.keySet()) {
      for (Direction d : events.get(s)) {
        if (d.getEndTime() > total) {
          total = d.getEndTime();
        }
      }
    }
    return total;
  }

  /**
   * Added since HW6. Method to add KeyFrame to model. If Directions do not exist for the given
   * shape, extend KeyFrame 1 second with no changes to position, size, color.
   *
   * @param shapeID ID of the Shape to be changed
   * @param k       The KeyFrame to be added
   */
  public void addKeyFrame(String shapeID, KeyFrame k) {
    IShape editedShape = getShapeWithID(shapeID);

    Direction original;
    Direction first;
    Direction second;
    KeyFrame begin;
    KeyFrame end;

    if (events.get(editedShape).isEmpty()) {
      end = new KeyFrame(k.getPosition(), k.getWidth(), k.getHeight(), k.getColor(),
              k.getTime() + 1);
      addDirection(editedShape, makeDirection(k, end));
    }

    original = getClosestDirection(k.getTime(), editedShape);
    begin = new KeyFrame(original.getStartPosition(), original.getStartWidth(),
            original.getStartHeight(), original.getStartColor(), original.getStartTime());
    end = new KeyFrame(original.getFinalPosition(), original.getFinalWidth(),
            original.getFinalHeight(), original.getFinalColor(), original.getEndTime());

    if (k.getTime() < begin.getTime()) {
      first = makeDirection(k, begin);
      addDirection(editedShape, first);
    } else if (k.getTime() > end.getTime()) {
      second = makeDirection(end, k);
      addDirection(editedShape, second);
    } else if (k.getTime() == begin.getTime()) {
      first = makeDirection(k, end);
      removeDirection(editedShape, original);
      addDirection(editedShape, first);
    } else if (k.getTime() == end.getTime()) {
      first = makeDirection(begin, k);
      removeDirection(editedShape, original);
      addDirection(editedShape, first);
    } else {
      first = makeDirection(begin, k);
      second = makeDirection(k, end);
      removeDirection(editedShape, original);
      addDirection(editedShape, first);
      addDirection(editedShape, second);
    }
  }

  /**
   * Added since HW6. Method to remove a KeyFrame from a shape in Animation.
   *
   * @param shapeID the ID of the shape which is to be edited
   * @param kTime   the time at the KeyFrame of the shape that is to be deleted.
   */
  public void deleteKeyFrame(String shapeID, double kTime) {
    IShape editedShape = getShapeWithID(shapeID);

    Direction d = getClosestDirection(kTime, editedShape);
    KeyFrame start;
    KeyFrame end;

    if (d.getStartTime() == kTime) {
      Direction before = getClosestDirection(kTime - 1, editedShape);
      //check if keyframe is first to exist in shape
      if (before == d) {
        removeDirection(editedShape, d);
      } else {
        start = new KeyFrame(before.getStartPosition(), before.getStartWidth(),
                before.getStartHeight(), before.getStartColor(), before.getStartTime());
        end = new KeyFrame(d.getFinalPosition(), d.getFinalWidth(), d.getFinalHeight(),
                d.getFinalColor(), d.getEndTime());
        removeDirection(editedShape, d);
        removeDirection(editedShape, before);
        addDirection(editedShape, makeDirection(start, end));
      }
    } else if (d.getEndTime() == kTime) {
      Direction after = getClosestDirection((kTime + 1), editedShape);
      if (after == d) {
        removeDirection(editedShape, d);
      } else {
        start = new KeyFrame(d.getStartPosition(), d.getStartWidth(), d.getStartHeight(),
                d.getStartColor(), d.getStartTime());
        end = new KeyFrame(after.getFinalPosition(), after.getFinalWidth(), after.getFinalHeight(),
                after.getFinalColor(), after.getEndTime());
        removeDirection(editedShape, d);
        removeDirection(editedShape, after);
        addDirection(editedShape, makeDirection(start, end));
      }
    }
  }

  /**
   * Added since HW6. Helper class to find the closest Direction to a keyframe in a given time.
   *
   * @param kTime The time at which the keyframe is at.
   * @param shape The Shape to be changed.
   */
  protected Direction getClosestDirection(double kTime, IShape shape) {
    ArrayList<Direction> directionList = events.get(shape);
    int i = 0;
    for (Direction d : directionList) {
      if (i == 0 && kTime < d.getStartTime()) {
        return d;
      } else if (kTime >= d.getStartTime() && kTime <= d.getEndTime()) {
        return d;
      } else if (i == directionList.size() - 1 && kTime > directionList.get(i).getEndTime()) {
        return d;
      }
      i++;
    }
    throw new IllegalArgumentException("Time does not exist");
  }

  /**
   * Added since HW6. Helper class that takes in two keyframes and turns them into a Direction.
   *
   * @param start keyframe representing initial state of Direction
   * @param end   keyframe representing final state of Direction
   */
  protected Direction makeDirection(KeyFrame start, KeyFrame end) {
    return new Direction(start.getPosition(), end.getPosition(),
            start.getWidth(), end.getWidth(),
            start.getHeight(), end.getHeight(),
            start.getColor(), end.getColor(),
            start.getTime(), end.getTime());
  }
}