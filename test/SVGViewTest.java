import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.io.IOException;

import cs3500.model.animator.model.Direction;
import cs3500.model.animator.model.IAnimatorModel;
import cs3500.model.animator.model.SimpleAnimatorModel;
import cs3500.model.animator.view.SVGView;
import cs3500.model.shape.Ellipse;
import cs3500.model.shape.IShape;
import cs3500.model.shape.Rectangle;
import cs3500.model.animator.util.Posn;

import static junit.framework.TestCase.assertEquals;

/**
 * Tests for the SVG View.
 */
public class SVGViewTest {
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
    new SVGView(null, ap, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullAppendable() {
    new SVGView(animator, null, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalid0Tempo() {
    new SVGView(animator, ap, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidNegTempo() {
    new SVGView(animator, ap, -1);
  }

  @Test
  public void testShapesNoDirection() throws IOException {
    animator.addShape(r, e);

    SVGView svg = new SVGView(animator, ap, 1);
    svg.createView();
    assertEquals("<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"1000\" height=\"1000\""
            + " version=\"1.1\">\n" + "</svg>", ap.toString());
  }

  @Test
  public void testGenerateViewOnEmptyAnimator() throws IOException {
    SVGView svgView = new SVGView(animator, ap, 10);
    svgView.createView();
    assertEquals("<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"1000\" height=\"1000\" "
            + "version=\"1.1\">\n" + "</svg>", ap.toString());
  }

  @Test
  public void testSVGMovement0() throws IOException {
    animator.addShape(r, e);
    animator.addDirection(r, recDirection0, recDirection1, recDirection2,
            recDirection3, recDirection4);
    animator.addDirection(e, ellDirection0, ellDirection1, ellDirection2,
            ellDirection3, ellDirection4);

    StringBuilder ap = new StringBuilder();
    SVGView svg = new SVGView(animator, ap, 15);
    svg.outputSVG(null);
    assertEquals(
            "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"1000\" height=\"1000\" " +
                    "version=\"1.1\">\n<rect xmlns=\"http://www.w3.org/2000/svg\" id=\"rec\" " +
                    "x=\"200\" y=\"200\" width=\"50\" height=\"100\" fill=\"rgb(255,0,0)\" " +
                    "visibility=\"hidden\">\n<set attributeType=\"xml\" begin=\"66.666667ms\"" +
                    " attributeName=\"visibility\" to=\"visible\"/>\n<set attributeType=\"xml\"" +
                    " begin=\"666.666667ms\" attributeName=\"visibility\" to=\"hidden\"/>" +
                    "\n</rect>\n<rect xmlns=\"http://www.w3.org/2000/svg\" id=\"rec\" x=\"200\"" +
                    " y=\"200\" width=\"50\" height=\"100\" fill=\"rgb(255,0,0)\" visibility=" +
                    "\"hidden\">\n<set attributeType=\"xml\" begin=\"666.666667ms\" " +
                    "attributeName=\"visibility\" to=\"visible\"/>\n" +
                    "<set attributeType=\"xml\" begin=\"3333.333333ms\" " +
                    "attributeName=\"visibility\" to=\"hidden\"/>\n" +
                    "<animate xmlns=\"http://www.w3.org/2000/svg\"" +
                    " attributeType=\"xml\" begin=\"666.666667ms\" dur=\"2666.666667ms\"" +
                    " attributeName=\"x\" from=\"200\" to=\"300\" fill=\"freeze\"/>\n" +
                    "<animate xmlns=\"http://www.w3.org/2000/svg\" attributeType=\"xml\"" +
                    " begin=\"666.666667ms\" dur=\"2666.666667ms\" attributeName=\"y\" " +
                    "from=\"200\" to=\"300\" fill=\"freeze\"/>\n</rect>\n" +
                    "<rect xmlns=\"http://www.w3.org/2000/svg\" id=\"rec\" x=\"300\" y=\"300\" " +
                    "width=\"50\" height=\"100\" fill=\"rgb(255,0,0)\" visibility=\"hidden\">\n" +
                    "<set attributeType=\"xml\" begin=\"3333.333333ms\" attributeName=\"" +
                    "visibility\" to=\"visible\"/>\n" +
                    "<set attributeType=\"xml\" begin=\"3400.000000ms\" attributeName=\"" +
                    "visibility\" to=\"hidden\"/>\n" +
                    "</rect>\n" +
                    "<rect xmlns=\"http://www.w3.org/2000/svg\" id=\"rec\" x=\"300\" y=\"300\"" +
                    " width=\"50\" height=\"100\" fill=\"rgb(255,0,0)\" visibility=\"hidden\">\n" +
                    "<set attributeType=\"xml\" begin=\"3400.000000ms\" attributeName=\"" +
                    "visibility\" to=\"visible\"/>\n" +
                    "<set attributeType=\"xml\" begin=\"4666.666667ms\" attributeName=\"" +
                    "visibility\" to=\"hidden\"/>\n" +
                    "<animate xmlns=\"http://www.w3.org/2000/svg\" attributeType=\"xml\" " +
                    "begin=\"3400.000000ms\" dur=\"1266.666667ms\" attributeName=\"width\" " +
                    "from=\"50\" to=\"25\" fill=\"freeze\"/>\n" +
                    "</rect>\n" +
                    "<rect xmlns=\"http://www.w3.org/2000/svg\" id=\"rec\" x=\"300\" y=\"300\" " +
                    "width=\"25\" height=\"100\" fill=\"rgb(255,0,0)\" visibility=\"hidden\">\n" +
                    "<set attributeType=\"xml\" begin=\"4666.666667ms\" attributeName=\"" +
                    "visibility\" to=\"visible\"/>\n" +
                    "<set attributeType=\"xml\" begin=\"6666.666667ms\" attributeName=\"" +
                    "visibility\" to=\"hidden\"/>\n" +
                    "<animate xmlns=\"http://www.w3.org/2000/svg\" attributeType=\"xml\"" +
                    " begin=\"4666.666667ms\" dur=\"2000.000000ms\" attributeName=\"x\"" +
                    " from=\"300\" to=\"200\" fill=\"freeze\"/>\n" +
                    "<animate xmlns=\"http://www.w3.org/2000/svg\" attributeType=\"xml\"" +
                    " begin=\"4666.666667ms\" dur=\"2000.000000ms\" attributeName=\"y\" " +
                    "from=\"300\" to=\"200\" fill=\"freeze\"/>\n" +
                    "</rect>\n" +
                    "<ellipse xmlns=\"http://www.w3.org/2000/svg\" id=\"elli\" cx=\"500\" " +
                    "cy=\"100\" rx=\"60.000000\" ry=\"30.000000\" fill=\"rgb(0,0,255)\" " +
                    "visibility=\"hidden\">\n" +
                    "<set attributeType=\"xml\" begin=\"400.000000ms\" attributeName=\"" +
                    "visibility\" to=\"visible\"/>\n" +
                    "<set attributeType=\"xml\" begin=\"1333.333333ms\" attributeName=\"" +
                    "visibility\" to=\"hidden\"/>\n" +
                    "</ellipse>\n" +
                    "<ellipse xmlns=\"http://www.w3.org/2000/svg\" id=\"elli\" cx=\"500\" " +
                    "cy=\"100\" rx=\"60.000000\" ry=\"30.000000\" fill=\"rgb(0,0,255)\" " +
                    "visibility=\"hidden\">\n" +
                    "<set attributeType=\"xml\" begin=\"1333.333333ms\" attributeName=\"" +
                    "visibility\" to=\"visible\"/>\n" +
                    "<set attributeType=\"xml\" begin=\"3333.333333ms\" attributeName=\"" +
                    "visibility\" to=\"hidden\"/>\n" +
                    "<animate xmlns=\"http://www.w3.org/2000/svg\" attributeType=\"xml\"" +
                    " begin=\"1333.333333ms\" dur=\"2000.000000ms\" attributeName=\"cy\"" +
                    " from=\"100\" to=\"280\" fill=\"freeze\"/>\n" +
                    "</ellipse>\n" +
                    "<ellipse xmlns=\"http://www.w3.org/2000/svg\" id=\"elli\" cx=\"500\"" +
                    " cy=\"280\" rx=\"60.000000\" ry=\"30.000000\" fill=\"rgb(0,0,255)\" " +
                    "visibility=\"hidden\">\n" +
                    "<set attributeType=\"xml\" begin=\"3333.333333ms\" attributeName=\"" +
                    "visibility\" to=\"visible\"/>\n" +
                    "<set attributeType=\"xml\" begin=\"4666.666667ms\" attributeName=\"" +
                    "visibility\" to=\"hidden\"/>\n" +
                    "<animate xmlns=\"http://www.w3.org/2000/svg\" attributeType=\"xml\" " +
                    "begin=\"3333.333333ms\" dur=\"1333.333333ms\" attributeName=\"cy\"" +
                    " from=\"280\" to=\"400\" fill=\"freeze\"/>\n" +
                    "<animate xmlns=\"http://www.w3.org/2000/svg\" attributeType=\"xml\" " +
                    "begin=\"3333.333333ms\" dur=\"1333.333333ms\" attributeName=\"fill\" " +
                    "from=\"rgb(0,0,255)\" to=\"rgb(0,170,85)\" fill=\"freeze\"/>\n" +
                    "</ellipse>\n" +
                    "<ellipse xmlns=\"http://www.w3.org/2000/svg\" id=\"elli\" cx=\"500\" " +
                    "cy=\"400\" rx=\"60.000000\" ry=\"30.000000\" fill=\"rgb(0,170,85)\" " +
                    "visibility=\"hidden\">\n" +
                    "<set attributeType=\"xml\" begin=\"4666.666667ms\" attributeName=\"" +
                    "visibility\" to=\"visible\"/>\n" +
                    "<set attributeType=\"xml\" begin=\"5333.333333ms\" attributeName=\"" +
                    "visibility\" to=\"hidden\"/>\n" +
                    "<animate xmlns=\"http://www.w3.org/2000/svg\" attributeType=\"xml\"" +
                    " begin=\"4666.666667ms\" dur=\"666.666667ms\" attributeName=\"fill\" " +
                    "from=\"rgb(0,170,85)\" to=\"rgb(0,255,0)\" fill=\"freeze\"/>\n" +
                    "</ellipse>\n" +
                    "<ellipse xmlns=\"http://www.w3.org/2000/svg\" id=\"elli\" cx=\"500\" " +
                    "cy=\"400\" rx=\"60.000000\" ry=\"30.000000\" fill=\"rgb(0,255,0)\" " +
                    "visibility=\"hidden\">\n" +
                    "<set attributeType=\"xml\" begin=\"5333.333333ms\" attributeName=\"" +
                    "visibility\" to=\"visible\"/>\n" +
                    "<set attributeType=\"xml\" begin=\"6666.666667ms\" attributeName=\"" +
                    "visibility\" to=\"hidden\"/>\n" +
                    "</ellipse>\n" +
                    "</svg>", ap.toString());
  }

  @Test
  public void testGenerateViewAfterRemoveShapeAndDirections() throws IOException {
    animator.addShape(r, e);
    animator.addDirection(r, recDirection0, recDirection1, recDirection2, recDirection3,
            recDirection4);
    animator.addDirection(e, ellDirection0, ellDirection1, ellDirection2, ellDirection3,
            ellDirection4);
    animator.removeShape(r);

    SVGView svgView = new SVGView(animator, ap, 10);
    svgView.createView();

    assertEquals("<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"1000\" " +
            "height=\"1000\" version=\"1.1\">\n" +
            "<ellipse xmlns=\"http://www.w3.org/2000/svg\" id=\"elli\" cx=\"500\" " +
            "cy=\"100\" rx=\"60.000000\" ry=\"30.000000\" fill=\"rgb(0,0,255)\" visibility=" +
            "\"hidden\">\n" +
            "<set attributeType=\"xml\" begin=\"600.000000ms\" attributeName=\"visibility\"" +
            " to=\"visible\"/>\n" +
            "<set attributeType=\"xml\" begin=\"2000.000000ms\" attributeName=\"visibility\" " +
            "to=\"hidden\"/>\n" +
            "</ellipse>\n" +
            "<ellipse xmlns=\"http://www.w3.org/2000/svg\" id=\"elli\" cx=\"500\" cy=\"100\"" +
            " rx=\"60.000000\" ry=\"30.000000\" fill=\"rgb(0,0,255)\" visibility=\"hidden\">\n" +
            "<set attributeType=\"xml\" begin=\"2000.000000ms\" attributeName=\"visibility\" " +
            "to=\"visible\"/>\n" +
            "<set attributeType=\"xml\" begin=\"5000.000000ms\" attributeName=\"visibility\" " +
            "to=\"hidden\"/>\n" +
            "<animate xmlns=\"http://www.w3.org/2000/svg\" attributeType=\"xml\" begin=\"" +
            "2000.000000ms\" dur=\"3000.000000ms\" attributeName=\"cy\" from=\"100\" to=\"280\" " +
            "fill=\"freeze\"/>\n" +
            "</ellipse>\n" +
            "<ellipse xmlns=\"http://www.w3.org/2000/svg\" id=\"elli\" cx=\"500\" cy=\"280\" " +
            "rx=\"60.000000\" ry=\"30.000000\" fill=\"rgb(0,0,255)\" visibility=\"hidden\">\n" +
            "<set attributeType=\"xml\" begin=\"5000.000000ms\" attributeName=\"visibility\"" +
            " to=\"visible\"/>\n" +
            "<set attributeType=\"xml\" begin=\"7000.000000ms\" attributeName=\"visibility\"" +
            " to=\"hidden\"/>\n" +
            "<animate xmlns=\"http://www.w3.org/2000/svg\" attributeType=\"xml\" " +
            "begin=\"5000.000000ms\" dur=\"2000.000000ms\" attributeName=\"cy\"" +
            " from=\"280\" to=\"400\" fill=\"freeze\"/>\n" +
            "<animate xmlns=\"http://www.w3.org/2000/svg\" attributeType=\"xml\" " +
            "begin=\"5000.000000ms\" dur=\"2000.000000ms\" attributeName=\"fill\" " +
            "from=\"rgb(0,0,255)\" to=\"rgb(0,170,85)\" fill=\"freeze\"/>\n" +
            "</ellipse>\n" +
            "<ellipse xmlns=\"http://www.w3.org/2000/svg\" id=\"elli\" cx=\"500\" " +
            "cy=\"400\" rx=\"60.000000\" ry=\"30.000000\" fill=\"rgb(0,170,85)\"" +
            " visibility=\"hidden\">\n" +
            "<set attributeType=\"xml\" begin=\"7000.000000ms\" attributeName=\"" +
            "visibility\" to=\"visible\"/>\n" +
            "<set attributeType=\"xml\" begin=\"8000.000000ms\" attributeName=\"" +
            "visibility\" to=\"hidden\"/>\n" +
            "<animate xmlns=\"http://www.w3.org/2000/svg\" attributeType=\"xml\" " +
            "begin=\"7000.000000ms\" dur=\"1000.000000ms\" attributeName=\"fill\" " +
            "from=\"rgb(0,170,85)\" to=\"rgb(0,255,0)\" fill=\"freeze\"/>\n" +
            "</ellipse>\n" +
            "<ellipse xmlns=\"http://www.w3.org/2000/svg\" id=\"elli\" cx=\"500\" " +
            "cy=\"400\" rx=\"60.000000\" ry=\"30.000000\" fill=\"rgb(0,255,0)\" " +
            "visibility=\"hidden\">\n" +
            "<set attributeType=\"xml\" begin=\"8000.000000ms\" attributeName=\"" +
            "visibility\" to=\"visible\"/>\n" +
            "<set attributeType=\"xml\" begin=\"10000.000000ms\" attributeName=\"" +
            "visibility\" to=\"hidden\"/>\n" +
            "</ellipse>\n" +
            "</svg>", ap.toString());
  }
}