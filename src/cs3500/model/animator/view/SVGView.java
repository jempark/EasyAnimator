package cs3500.model.animator.view;

import java.awt.Color;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import cs3500.model.animator.model.Direction;
import cs3500.model.animator.model.ReadOnlyIAnimatorModel;
import cs3500.model.shape.IShape;

/**
 * Represents the SVG view of the Animator model and creates an SVG file that could display the
 * animation in any SVG Viewer that supports the animation.
 */
public class SVGView implements ISVGView {
  private ReadOnlyIAnimatorModel model;
  private Appendable ap;
  private double tempo;

  /**
   * Constructs an SVG View given a model, an appendable, and a tempo.
   *
   * @param model the animator model
   * @param ap    the appendable object
   * @param tempo the speed at which the model should operate - is one tick/second
   * @throws IllegalArgumentException if the model or Appendable object is null or the tempo is less
   *                                  than or equal to 0
   */
  public SVGView(ReadOnlyIAnimatorModel model, Appendable ap, double tempo) {
    if (model == null || ap == null) {
      throw new IllegalArgumentException("Null model or Appendable");
    } else if (tempo <= 0) {
      throw new IllegalArgumentException("Tempo cannot be equal to or less than 0");
    }
    this.model = model;
    this.ap = ap;
    this.tempo = tempo;
  }

  /**
   * Creates the output for the view- takes the model and displays it.
   *
   * @throws IOException if the input or output is invalid.
   */
  @Override
  public void createView() throws IOException {
    outputSVG(null);
  }

  /**
   * Generates a file that represents the data in the model in SVG format.
   *
   * @param fileName where the SVG will be saved
   * @throws IOException when there is an issue with writing to the file
   */
  public void outputSVG(String fileName) throws IOException {
    LinkedHashMap<IShape, ArrayList<Direction>> map;
    map = model.getCopyDirectionMapRebounded();

    ap.append("<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"" +
            model.getBoundWidth() +
            "\" "
            +
            "height=\"" +
            model.getBoundHeight() +
            "\" version=\"1.1\">\n");

    for (IShape s : map.keySet()) {
      /**
       * Case when the shape is a rectangle.
       */
      if (s.getShape().equals("Rectangle")) {
        ArrayList<Direction> direction = map.get(s);
        for (Direction d : direction) {
          ap.append(String.format("<rect xmlns=\"http://www.w3.org/2000/svg\" id=\"%s\" x=\"%d\"" +
                          " y=\"%d\" width=\"%d\" height=\"%d\" fill=\"rgb(%d,%d,%d)\" " +
                          "visibility=\"hidden\">\n",
                  s.getID(),
                  d.getStartPosition().getX(),
                  d.getStartPosition().getY(),
                  d.getStartWidth(),
                  d.getStartHeight(),
                  d.getStartColor().getRed(),
                  d.getStartColor().getGreen(),
                  d.getStartColor().getBlue()));

          ap.append(String.format("<set attributeType=\"xml\" " +
                          "begin=\"%fms\" attributeName=\"visibility\" " +
                          "to=\"visible\"/>\n",
                  d.getStartTime() / tempo * 1000));

          ap.append(String.format("<set attributeType=\"xml\" " +
                          "begin=\"%fms\" attributeName=\"visibility\" " +
                          "to=\"hidden\"/>\n",
                  d.getEndTime() / tempo * 1000));
          parseDirectionRect(d);
          ap.append(String.format("</rect>\n"));
        }
      }
      /**
       * Case when the shape is a ellipse.
       */
      else if (s.getShape().equals("Ellipse")) {
        ArrayList<Direction> direction = map.get(s);
        for (Direction d : direction) {
          ap.append(String.format("<ellipse xmlns=\"http://www.w3.org/2000/svg\"" +
                          " id=\"%s\" cx=\"%d\"" +
                          " cy=\"%d\" rx=\"%f\" ry=\"%f\" fill=\"rgb(%d,%d,%d)\"" +
                          " visibility=\"hidden\">\n",
                  s.getID(),
                  (d.getStartPosition().getX() + d.getStartWidth() / 2),
                  (d.getStartPosition().getY() + d.getStartHeight() / 2),
                  d.getStartWidth() / 2.0,
                  d.getStartHeight() / 2.0,
                  d.getStartColor().getRed(),
                  d.getStartColor().getGreen(),
                  d.getStartColor().getBlue()));

          ap.append(String.format("<set attributeType=\"xml\" " +
                          "begin=\"%fms\" attributeName=\"visibility\" " +
                          "to=\"visible\"/>\n",
                  d.getStartTime() / tempo * 1000));

          ap.append(String.format("<set attributeType=\"xml\" " +
                          "begin=\"%fms\" attributeName=\"visibility\" " +
                          "to=\"hidden\"/>\n",
                  d.getEndTime() / tempo * 1000));
          parseDirectionCircle(d);
          ap.append("</ellipse>\n");
        }
      }
      /**
       * Shouldn't get to this case but throws an error if it does.
       */
      else {
        throw new IllegalArgumentException("Invalid shape");
      }
    }
    ap.append("</svg>");

    if (fileName != null) {
      BufferedWriter fileWriter = new BufferedWriter(new FileWriter(new File(fileName)));
      fileWriter.write(ap.toString());
      fileWriter.flush();
      fileWriter.close();
      ap = new StringBuilder();
    }
  }

  /**
   * Parses and appends the direction for the rectangle to the appendable. To do this, this method
   * calls different helper methods depending on the if-statement cases.
   *
   * @param d direction to be appended
   * @throws IOException occurs when it cannot be written to appendable
   */
  private void parseDirectionRect(Direction d) throws IOException {
    /**
     * Checks if the X position changes.
     */
    if (d.getStartPosition().getX() != d.getFinalPosition().getX()) {
      parseRecPositionX(d.getStartPosition().getX(), d.getFinalPosition().getX(),
              d.getStartTime(), d.getEndTime());
    }
    /**
     * Checks if the Y position changes.
     */
    if (d.getStartPosition().getY() != d.getFinalPosition().getY()) {
      parseRecPositionY(d.getStartPosition().getY(), d.getFinalPosition().getY(),
              d.getStartTime(), d.getEndTime());
    }
    /**
     * Checks if the width changes.
     */
    if (d.getStartWidth() != d.getFinalWidth()) {
      parseRecWidth(d.getStartWidth(), d.getFinalWidth(), d.getStartTime(), d.getEndTime());
    }
    /**
     * Checks if the height changes.
     */
    if (d.getStartHeight() != d.getFinalHeight()) {
      parseRecHeight(d.getStartHeight(), d.getFinalHeight(), d.getStartTime(), d.getEndTime());
    }
    /**
     * Checks if the color changes.
     */
    if ((!(d.getStartColor().equals(d.getFinalColor())))) {
      parseColor(d.getStartColor(), d.getFinalColor(), d.getStartTime(), d.getEndTime());
    }
  }

  /**
   * Parses and appends the direction for the Circle to the appendable. To do this, the method calls
   * different helper methods depending on the if-statement cases.
   *
   * @param d direction to be appended
   * @throws IOException occurs when it cannot be written to appendable
   */
  private void parseDirectionCircle(Direction d) throws IOException {
    /**
     * Checks if the X position changes.
     */
    if (d.getStartPosition().getX() != d.getFinalPosition().getX()) {
      parseCircPositionX((d.getStartPosition().getX() + d.getStartWidth() / 2),
              (d.getFinalPosition().getX() + d.getFinalWidth() / 2),
              d.getStartTime(), d.getEndTime());
    }
    /**
     * Checks if the Y position changes.
     */
    if (d.getStartPosition().getY() != d.getFinalPosition().getY()) {
      parseCircPositionY((d.getStartPosition().getY() + d.getStartHeight() / 2),
              (d.getFinalPosition().getY() + d.getFinalHeight() / 2),
              d.getStartTime(), d.getEndTime());
    }
    /**
     * Checks if the width changes.
     */
    if (d.getStartWidth() != d.getFinalWidth()) {
      parseCircWidth(d.getStartWidth(), d.getFinalWidth(), d.getStartTime(), d.getEndTime());
    }
    /**
     * Checks if the height changes.
     */
    if (d.getStartHeight() != d.getFinalHeight()) {
      parseCircHeight(d.getStartHeight(), d.getFinalHeight(), d.getStartTime(), d.getEndTime());
    }
    /**
     * Checks if the color changes.
     */
    if (!(d.getStartColor().equals(d.getFinalColor()))) {
      parseColor(d.getStartColor(), d.getFinalColor(), d.getStartTime(), d.getEndTime());
    }
  }

  /**
   * Takes in the starting and ending x coordinates and the start and end times to properly append
   * the change in direction of a Rectangle onto the svg file.
   *
   * @param startX    the starting x coordinate of the Direction
   * @param finalX    the final x coordinate of the Direction
   * @param startTime the start time of the Direction
   * @param endTime   the end time of the Direction
   */
  private void parseRecPositionX(int startX, int finalX, double startTime, double endTime)
          throws IOException {
    ap.append(String.format("<animate xmlns=\"http://www.w3.org/2000/svg\"" +
                    " attributeType=\"xml\" " +
                    "begin=\"%fms\"" +
                    " dur=\"%fms\" attributeName=\"x\" from=\"%d\"" +
                    " to=\"%d\" fill=\"freeze\"/>\n",
            (startTime / tempo) * 1000, (endTime - startTime) / tempo * 1000, startX, finalX));
  }

  /**
   * Takes in the starting and ending x coordinates and the start and end times to properly append
   * the change in direction of a Circle onto the svg file.
   *
   * @param startX    the starting x coordinate of the Direction
   * @param finalX    the final x coordinate of the Direction
   * @param startTime the start time of the Direction
   * @param endTime   the end time of the Direction
   */
  private void parseCircPositionX(int startX, int finalX, double startTime, double endTime)
          throws IOException {
    ap.append(String.format("<animate xmlns=\"http://www.w3.org/2000/svg\"" +
                    " attributeType=\"xml\" " +
                    "begin=\"%fms\"" +
                    " dur=\"%fms\" attributeName=\"cx\" from=\"%d\"" +
                    " to=\"%d\" fill=\"freeze\"/>\n",
            (startTime / tempo) * 1000,
            (endTime - startTime) / tempo * 1000,
            startX,
            finalX));
  }

  /**
   * Takes in the starting and ending y coordinates and the start and end times to properly append
   * the change in direction of a Rectangle onto the svg file.
   *
   * @param startY    the starting y coordinate of the Direction
   * @param finalY    the final y coordinate of the Direction
   * @param startTime the start time of the Direction
   * @param endTime   the end time of the Direction
   */
  private void parseRecPositionY(int startY, int finalY, double startTime, double endTime)
          throws IOException {
    ap.append(String.format("<animate xmlns=\"http://www.w3.org/2000/svg\" " +
                    "attributeType=\"xml\" " +
                    "begin=\"%fms\" dur=\"%fms\"" +
                    " attributeName=\"y\" from=\"%d\" " +
                    "to=\"%d\" fill=\"freeze\"/>\n",
            (startTime / tempo) * 1000, ((endTime - startTime) / tempo) * 1000, startY, finalY));
  }

  /**
   * Takes in the starting and ending y coordinates and the start and end times to properly append
   * the change in direction of a Circle onto the svg file.
   *
   * @param startY    the starting y coordinate of the Direction
   * @param finalY    the final y coordinate of the Direction
   * @param startTime the start time of the Direction
   * @param endTime   the end time of the Direction
   */
  private void parseCircPositionY(int startY, int finalY, double startTime, double endTime)
          throws IOException {
    ap.append(String.format("<animate xmlns=\"http://www.w3.org/2000/svg\" " +
                    "attributeType=\"xml\" " +
                    "begin=\"%fms\" dur=\"%fms\"" +
                    " attributeName=\"cy\" from=\"%d\" " +
                    "to=\"%d\" fill=\"freeze\"/>\n",
            (startTime / tempo) * 1000, ((endTime - startTime) / tempo) * 1000, startY, finalY));
  }

  /**
   * Takes in the starting and ending width and the start and end times to properly append the
   * change in direction of a Rectangle onto the svg file.
   *
   * @param startW    the starting width coordinate of the Direction
   * @param finalW    the final width coordinate of the Direction
   * @param startTime the start time of the Direction
   * @param endTime   the end time of the Direction
   */
  private void parseRecWidth(int startW, int finalW, double startTime, double endTime)
          throws IOException {
    ap.append(String.format("<animate xmlns=\"http://www.w3.org/2000/svg\" " +
                    "attributeType=\"xml\" " +
                    "begin=\"%fms\" " +
                    "dur=\"%fms\" attributeName=\"width\" from=\"%d\"" +
                    " to=\"%d\" fill=\"freeze\"/>\n",
            (startTime / tempo) * 1000, ((endTime - startTime) / tempo) * 1000, startW, finalW));
  }

  /**
   * Takes in the starting and ending width and the start and end times to properly append the
   * change in direction of a Circle onto the svg file.
   *
   * @param startW    the starting width coordinate of the Direction
   * @param finalW    the final width coordinate of the Direction
   * @param startTime the start time of the Direction
   * @param endTime   the end time of the Direction
   */
  private void parseCircWidth(int startW, int finalW, double startTime, double endTime)
          throws IOException {
    ap.append(String.format("<animate xmlns=\"http://www.w3.org/2000/svg\" " +
                    "attributeType=\"xml\" " +
                    "begin=\"%fms\" " +
                    "dur=\"%fms\" attributeName=\"rx\" from=\"%f\"" +
                    " to=\"%f\" fill=\"freeze\"/>\n",
            (startTime / tempo) * 1000, ((endTime - startTime) / tempo) * 1000,
            startW / 2.0, finalW / 2.0));
  }

  /**
   * Takes in the starting and ending height and the start and end times to properly append the
   * change in direction of a Rectangle onto the svg file.
   *
   * @param startH    the starting height of the Direction
   * @param finalH    the final height of the Direction
   * @param startTime the start time of the Direction
   * @param endTime   the end time of the Direction
   */
  private void parseRecHeight(int startH, int finalH, double startTime, double endTime)
          throws IOException {
    ap.append(String.format("<animate xmlns=\"http://www.w3.org/2000/svg\"" +
                    " attributeType=\"xml\" " +
                    "begin=\"%fms\" " +
                    "dur=\"%fms\" attributeName=\"height\"" +
                    " from=\"%d\" to=\"%d\" fill=\"freeze\"/>\n",
            (startTime / tempo) * 1000, ((endTime - startTime) / tempo) * 1000, startH, finalH));
  }

  /**
   * Takes in the starting and ending height and the start and end times to properly append the
   * change in direction of a Circle onto the svg file.
   *
   * @param startH    the starting height of the Direction
   * @param finalH    the final height of the Direction
   * @param startTime the start time of the Direction
   * @param endTime   the end time of the Direction
   */
  private void parseCircHeight(int startH, int finalH, double startTime, double endTime)
          throws IOException {
    ap.append(String.format("<animate xmlns=\"http://www.w3.org/2000/svg\"" +
                    " attributeType=\"xml\" " +
                    "begin=\"%fms\" " +
                    "dur=\"%fms\" attributeName=\"ry\"" +
                    " from=\"%f\" to=\"%f\" fill=\"freeze\"/>\n",
            (startTime / tempo) * 1000, ((endTime - startTime) / tempo) * 1000,
            startH / 2.0, finalH / 2.0));
  }

  /**
   * Takes in the starting and ending color and the start and end times to properly append the
   * change in direction onto the svg file.
   *
   * @param startColor the starting Color of the Direction
   * @param finalColor the final Color of the Direction
   * @param startTime  the start time of the Direction
   * @param endTime    the end time of the Direction
   */
  private void parseColor(Color startColor, Color finalColor, double startTime, double endTime)
          throws IOException {
    ap.append(String.format("<animate xmlns=\"http://www.w3.org/2000/svg\"" +
                    " attributeType=\"xml\" " +
                    "begin=\"%fms\" dur=\"%fms\" " +
                    "attributeName=\"fill\" from=\"%s\" to=\"%s\"" +
                    " fill=\"freeze\"/>\n",
            (startTime / tempo) * 1000,
            ((endTime - startTime) / tempo) * 1000,
            String.format("rgb(%d,%d,%d)",
                    startColor.getRed(),
                    startColor.getGreen(),
                    startColor.getBlue()),
            String.format("rgb(%d,%d,%d)",
                    finalColor.getRed(),
                    finalColor.getGreen(),
                    finalColor.getBlue())));
  }
}