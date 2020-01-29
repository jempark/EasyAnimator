package cs3500.model.animator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.FlowLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JTabbedPane;
import javax.swing.JFrame;
import javax.swing.Timer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;

import cs3500.model.animator.model.IAnimatorModel;
import cs3500.model.animator.model.KeyFrame;
import cs3500.model.animator.util.Posn;

/**
 * Represents an Interactive View for the animator model and plays the Animation as a Visual View.
 * Allows the user to do actions such as play, pause, loop, increase/decrease speed. Also allows
 * users to add or remove shapes and key frames. User can save their masterpieces after edit as an
 * svg or text file as well. Takes in the animator model, the original speed at which it is player,
 * and the controller for the interactive view.
 */
public class InteractiveView extends JFrame implements IInteractiveView {
  private VisualPanel panel;
  private IAnimatorModel model;
  private double tempo;
  private Timer timer;
  ActionListener listener;

  private boolean pause;
  private boolean restart;
  private boolean loop;
  private boolean speedUp;
  private boolean speedDown;

  private JPanel viewPanel;
  private JPanel deleteShapesPanel;
  private JPanel keyFramesPanel;
  private JPanel addShapesPanel;
  private JPanel saveAnimationPanel;
  private JPanel scrubberPanel;

  private JTextField inputShapeID;
  private JTextField inputKFTime;
  private JTextField inputKFPosx;
  private JTextField inputKFPosy;
  private JTextField inputKFWidth;
  private JTextField inputKFHeight;
  private JTextField inputKFRed;
  private JTextField inputKFGreen;
  private JTextField inputKFBlue;
  private JTextField outputFile;

  private JComboBox<String> shapeIDDropDown1;
  private JComboBox<String> shapeIDDropDown2;
  private JComboBox<String> existingShapeTypesDropDown;
  private JComboBox<String> saveTypesDropDown;

  private JSlider tickAt;

  ArrayList<String> shapeIDList;
  String[] shapeIDArr;

  /**
   * Constructor for the Interactive View that takes in an IAnimatorModel model, speed of the base
   * animation, and an ActionListener that is the controller.
   *
   * @param model    the input Model
   * @param tempo    the input Tempo (ticks/second)
   * @param listener the controller
   * @throws IllegalArgumentException if the model passed in is null or the tempo is invalid
   */
  public InteractiveView(IAnimatorModel model, double tempo, ActionListener listener) {
    super();
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    if (tempo <= 0) {
      throw new IllegalArgumentException("Tempo must be positive non-zero number");
    }

    this.model = model;
    this.pause = false;
    this.restart = false;
    this.loop = false;
    this.speedUp = false;
    this.speedDown = false;
    this.tempo = tempo;
    this.listener = listener;

    timer = new Timer((int) (1000 * (1 / tempo)), this);

    this.setTitle("ExCELlence Interactive Animator");
    this.setSize(model.getBoundWidth() + 500, model.getBoundHeight());
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    this.buildUI();
  }

  /**
   * Function to generate the Interactive View.
   */
  @Override
  public void createView() {
    this.setVisible(true);
    timer.start();
  }

  /**
   * Builds the UI for the Interactive View in which the shapes will be displayed.
   */
  private void buildUI() {
    JPanel editPanel;
    this.panel = new VisualPanel(model.getCopyDirectionMapRebounded());

    JScrollPane scroll = new JScrollPane(panel);

    this.setLayout(new BorderLayout());
    this.add(scroll, BorderLayout.WEST);

    panel.setPreferredSize(new Dimension(model.getBoundWidth(), model.getBoundHeight()));

    JPanel flowPanel = new JPanel(new FlowLayout());

    viewPanel = new JPanel();
    viewPanel.setLayout(new GridLayout(5, 1));
    flowPanel.add(viewPanel);
    this.add(flowPanel, new BorderLayout().EAST);

    editPanel = new JPanel();
    editPanel.setLayout(new BoxLayout(editPanel, BoxLayout.Y_AXIS));
    this.add(editPanel, new BorderLayout().SOUTH);

    shapeIDList = model.getShapeIDList();
    shapeIDArr = shapeIDList.toArray(new String[shapeIDList.size()]);

    addShapesPanel = new JPanel();

    deleteShapesPanel = new JPanel();
    editPanel.add(deleteShapesPanel);

    keyFramesPanel = new JPanel();
    editPanel.add(keyFramesPanel);

    scrubberPanel = new JPanel();
    editPanel.add(scrubberPanel);

    JTabbedPane tabbedPane = new JTabbedPane();
    tabbedPane.add(addShapesPanel, "Add Shape");
    tabbedPane.add(deleteShapesPanel, "Delete Shape");
    tabbedPane.add(keyFramesPanel, "Edit Shapes");
    tabbedPane.add(scrubberPanel, "Scrub through Animation");
    editPanel.add(tabbedPane);

    saveAnimationPanel = new JPanel();
    saveAnimationPanel.setBackground(Color.lightGray);
    editPanel.add(saveAnimationPanel);

    this.addViewButtons();
    this.setupDeleteShapesView();
    this.setupEditShapesView();
    this.setupAddShapesView();
    this.setupSaveAnimation();
    this.setupScrubberPanel();

    this.pack();
  }

  /**
   * Method to stop the model for the Interactive View once the model has changed.
   */
  @Override
  public void stopView() {
    this.setVisible(false);
    timer.stop();
  }

  /**
   * Adds the buttons to perform different actions to the UI on the right side of the animation that
   * is being displayed. Helper class for buildUI.
   */
  private void addViewButtons() {
    JButton playPauseButton;
    JButton loopButton;
    JButton restartButton;
    JButton increaseSpeedButton;
    JButton decreaseSpeedButton;

    loopButton = new JButton("Loop");
    loopButton.addActionListener((ActionEvent e) -> loop = !loop);
    viewPanel.add(loopButton);

    restartButton = new JButton("Restart");
    restartButton.addActionListener((ActionEvent e) -> restart = true);
    viewPanel.add(restartButton);

    playPauseButton = new JButton("Play/Pause");
    playPauseButton.addActionListener((ActionEvent e) -> pause = !pause);
    viewPanel.add(playPauseButton);

    increaseSpeedButton = new JButton("Increase Speed");
    increaseSpeedButton.addActionListener((ActionEvent e) -> speedUp = true);
    viewPanel.add(increaseSpeedButton);

    decreaseSpeedButton = new JButton("Decrease Speed");
    decreaseSpeedButton.addActionListener((ActionEvent e) -> speedDown = true);
    viewPanel.add(decreaseSpeedButton);
  }

  /**
   * Helper for buildUI, adds all UI elements for deleting a Shape.
   */
  private void setupDeleteShapesView() {
    JButton deleteShapeButton;
    JLabel shapeIDLabel = new JLabel("Shape ID");
    deleteShapesPanel.add(shapeIDLabel);

    shapeIDDropDown1 = new JComboBox<>(shapeIDArr);
    deleteShapesPanel.add(shapeIDDropDown1);

    deleteShapeButton = new JButton("Delete Shape");
    deleteShapeButton.addActionListener(listener);
    deleteShapeButton.setActionCommand("Delete Shape");
    deleteShapesPanel.add(deleteShapeButton);
  }

  /**
   * Helper for buildUI, sets up the UI components for editing a shape at a given time.
   */
  private void setupEditShapesView() {
    JButton addKeyFrameButton;
    JButton deleteKeyFrameButton;

    shapeIDDropDown2 = new JComboBox<>(shapeIDArr);
    deleteShapesPanel.add(shapeIDDropDown2);

    keyFramesPanel.add(shapeIDDropDown2);

    JLabel timeLabel = new JLabel("Time");
    keyFramesPanel.add(timeLabel);

    inputKFTime = new JTextField(5);
    inputKFTime.setBackground(Color.PINK);
    keyFramesPanel.add(inputKFTime);

    JLabel xLabel = new JLabel("X");
    keyFramesPanel.add(xLabel);

    inputKFPosx = new JTextField(5);
    keyFramesPanel.add(inputKFPosx);

    JLabel yLabel = new JLabel("Y");
    keyFramesPanel.add(yLabel);

    inputKFPosy = new JTextField(5);
    keyFramesPanel.add(inputKFPosy);

    JLabel widthLabel = new JLabel("W");
    keyFramesPanel.add(widthLabel);

    inputKFWidth = new JTextField(5);
    keyFramesPanel.add(inputKFWidth);

    JLabel heightLabel = new JLabel("H");
    keyFramesPanel.add(heightLabel);

    inputKFHeight = new JTextField(5);
    keyFramesPanel.add(inputKFHeight);

    JLabel rLabel = new JLabel("R");
    keyFramesPanel.add(rLabel);

    inputKFRed = new JTextField(5);
    keyFramesPanel.add(inputKFRed);

    JLabel gLabel = new JLabel("G");
    keyFramesPanel.add(gLabel);

    inputKFGreen = new JTextField(5);
    keyFramesPanel.add(inputKFGreen);

    JLabel bLabel = new JLabel("B");
    keyFramesPanel.add(bLabel);

    inputKFBlue = new JTextField(5);
    keyFramesPanel.add(inputKFBlue);

    addKeyFrameButton = new JButton("Add Keyframe");
    addKeyFrameButton.addActionListener(listener);
    addKeyFrameButton.setActionCommand("Add Keyframe");
    keyFramesPanel.add(addKeyFrameButton);

    deleteKeyFrameButton = new JButton("Delete Keyframe");
    deleteKeyFrameButton.addActionListener(listener);
    deleteKeyFrameButton.setActionCommand("Delete Keyframe");
    deleteKeyFrameButton.setBackground(Color.PINK);
    keyFramesPanel.add(deleteKeyFrameButton);
  }

  /**
   * Helper for buildUI, adds all UI elements for adding a Shape.
   */
  private void setupAddShapesView() {
    JButton addShapeButton;

    JLabel shapeTypeLabel = new JLabel("Type");
    addShapesPanel.add(shapeTypeLabel);

    String[] shapeTypesArray = {"Rectangle", "Ellipse"};
    existingShapeTypesDropDown = new JComboBox<>(shapeTypesArray);
    addShapesPanel.add(existingShapeTypesDropDown);

    JLabel newShapeID = new JLabel("ID");
    addShapesPanel.add(newShapeID);

    inputShapeID = new JTextField(10);
    addShapesPanel.add(inputShapeID);

    addShapeButton = new JButton("Add Shape");
    addShapeButton.addActionListener(listener);
    addShapeButton.setActionCommand("Add Shape");
    addShapesPanel.add(addShapeButton);
  }

  /**
   * Helper for buildUI, adds all UI elements for scrubberPanel.
   */
  private void setupScrubberPanel() {
    tickAt = new JSlider(JSlider.HORIZONTAL,
            0, (int) model.totalDuration(), panel.getTick());
    tickAt.addChangeListener((ChangeEvent e) ->
            panel.setTick(((JSlider) e.getSource()).getValue()));
    scrubberPanel.add(tickAt);
  }

  /**
   * Helper for buildUI, adds all UI elements for saving the masterpiece that the user created.
   */
  private void setupSaveAnimation() {
    JButton saveAnimationButton;

    JLabel saveTypeLabel = new JLabel("Save as type:");
    saveAnimationPanel.add(saveTypeLabel);

    String[] saveTypesArray = {"SVG", "Text"};
    saveTypesDropDown = new JComboBox<>(saveTypesArray);
    saveAnimationPanel.add(saveTypesDropDown);

    JLabel outputFileLabel = new JLabel("Output File");
    saveAnimationPanel.add(outputFileLabel);

    outputFile = new JTextField(60);
    saveAnimationPanel.add(outputFile);

    saveAnimationButton = new JButton("Save Animation");
    saveAnimationButton.addActionListener(listener);
    saveAnimationButton.setActionCommand("Save Animation");
    saveAnimationPanel.add(saveAnimationButton);
  }

  /**
   * Helper function that does the action that is being done when a user presses the button on the
   * UI. Updates the boolean value to the appropriate value to indicate that the button is no longer
   * being pressed.
   *
   * @param e the input ActionEvent
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    /**
     * If the loop button is pressed.
     */
    if (loop) {
      if (panel.getTick() >= model.totalDuration()) {
        panel.restartAnimation();
      }
    }

    /**
     * If the speed up button is pressed.
     */
    if (speedUp) {
      tempo = tempo * 2;
      speedUp = false;
      this.changeSpeed();
    }

    /**
     * If the speed down button is pressed.
     */
    if (speedDown) {
      tempo = tempo / 2;
      speedDown = false;
      this.changeSpeed();
    }

    /**
     * If the restart button is pressed and the pause button isn't pressed.
     */
    if (restart) {
      panel.restartAnimation();
      pause = false;
      restart = false;
    } else if (!pause) {
      panel.animateShapes();
    }

    tickAt.setValue(panel.getTick());
    panel.repaint();
    panel.revalidate();
  }

  /**
   * Helper that is called when the speed of the animation is changed.
   */
  private void changeSpeed() {
    this.timer.stop();
    this.timer = new Timer((int) (1000 * (1 / tempo)), this);
    this.timer.start();
  }

  /**
   * Gets the deleted shape ID that the user selected.
   *
   * @return a String of the shape ID
   */
  @Override
  public String getDeletedShapeID() {
    return (String) shapeIDDropDown1.getSelectedItem();
  }

  /**
   * Gets the selected shape ID that the user selected.
   *
   * @return a String of the shape ID
   */
  @Override
  public String getSelectedShapeID() {
    return (String) shapeIDDropDown2.getSelectedItem();
  }

  /**
   * Gets the shape type that the user selected.
   *
   * @return the String of the shape Type
   */
  @Override
  public String getNewShapeType() {
    return (String) existingShapeTypesDropDown.getSelectedItem();
  }

  /**
   * Gets the input ID of the shape that the user added.
   *
   * @return the String of the input ID
   */
  @Override
  public String getNewShapeID() {
    return inputShapeID.getText();
  }

  /**
   * Get the Keyframe that the user has created by compiling all of the test input in the UI.
   *
   * @return keyframe to be added
   */
  @Override
  public KeyFrame getUserKeyFrame() {
    Posn position = new Posn(Integer.parseInt(inputKFPosx.getText()) + model.getBoundX(),
            Integer.parseInt(inputKFPosy.getText()) + model.getBoundY());
    Color color = new Color(
            Integer.parseInt(inputKFRed.getText()),
            Integer.parseInt(inputKFGreen.getText()),
            Integer.parseInt(inputKFBlue.getText()));
    KeyFrame key = new KeyFrame(position,
            Integer.parseInt(inputKFWidth.getText()),
            Integer.parseInt(inputKFHeight.getText()),
            color,
            getKeyFrameTime()
    );
    return key;
  }

  /**
   * Gets the time of the key frame.
   *
   * @return a double representing the time of the keyframe
   */
  @Override
  public double getKeyFrameTime() {
    return (double) Integer.parseInt(inputKFTime.getText());
  }

  /**
   * Gets the user defined name of the Output File at which the animation is to be saved.
   *
   * @return the defined name of the Output File as a String
   */
  @Override
  public String getOutputFile() {
    return outputFile.getText();
  }

  /**
   * Gets the format in which the animation would be saved as (SVG, Text).
   *
   * @return the format as a String.
   */
  @Override
  public String getSaveType() {
    return saveTypesDropDown.getSelectedItem().toString();
  }
}