package cs3500.model.animator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.Timer;
import javax.swing.JScrollPane;

import cs3500.model.animator.model.ReadOnlyIAnimatorModel;

/**
 * Visual View class, returns the view of model in JSwing.
 */
public class VisualView extends JFrame implements ActionListener, IView {
  private VisualPanel panel;
  private Timer timer;

  /**
   * Constructor for VisualView.
   *
   * @param model the input Model
   * @param tempo the input Tempo (ticks/second)
   */
  public VisualView(ReadOnlyIAnimatorModel model, double tempo) {
    super();
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    if (tempo <= 0) {
      throw new IllegalArgumentException("Tempo must be positive non-zero number");
    }

    this.setTitle("ExCELlence Animator");

    this.setSize(model.getBoundWidth(), model.getBoundHeight());
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    this.panel = new VisualPanel(model.getCopyDirectionMapRebounded());

    JScrollPane scroll = new JScrollPane(panel);

    this.setLayout(new BorderLayout());
    this.add(scroll, BorderLayout.CENTER);

    panel.setPreferredSize(new Dimension(model.getBoundWidth(), model.getBoundHeight()));
    timer = new Timer((int) (1000 * (1 / tempo)), this);
    this.pack();
  }

  /**
   * Function to generate Visual View.
   */
  @Override
  public void createView() {
    this.setVisible(true);
    timer.start();
  }

  /**
   * Function to perform action.
   *
   * @param e the input ActionEvent
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    panel.animateShapes();
    panel.repaint();
    panel.revalidate();
  }
}
