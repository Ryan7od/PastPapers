package Q2;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class SimpleStats implements SimpleStatsInterface {
  static JTextField currentMax = new JTextField(11);
  static JTextField currentMean = new JTextField(11);

  private void display() {
    final StatsEngine model = new StatsEngine();
    model.addObserver(this);

    JFrame frame = new JFrame("Simple Stats");
    frame.setSize(250, 350);

    JPanel panel = new JPanel();

    addLabels(panel);
    addButtons(model, panel);

    frame.getContentPane().add(panel);

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  }

  private static void addButtons(StatsEngine model, JPanel panel) {
    for (int i = 1; i <= 12; i++) {
      final int n = i;
      JButton button = new JButton(String.valueOf(i));
      button.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          model.action(n);
        }
      });
      panel.add(button);
    }
  }
  private static void addLabels(JPanel panel) {
    panel.add(new JLabel("Max: value "));
    panel.add(currentMax);
    panel.add(new JLabel("Mean: value "));
    panel.add(currentMean);
  }

  public void updateValues(int max, double mean) {
    currentMax.setText(String.valueOf(max));
    currentMean.setText(String.valueOf(mean));
  }

  public static void main(String[] args) {
    new SimpleStats().display();
  }

}

class StatsEngine {
  private final List<Integer> numbers = new ArrayList<>();
  private final List<SimpleStatsInterface> observers = new ArrayList<>();
  private int max;
  private double mean;

  public void action(int n) {
    numbers.add(n);
    max = Math.max(max, n);
    mean = numbers.stream().mapToInt(val -> val).average().orElse(0.0);
    notifyObservers();
  }

  private void notifyObservers() {
    for (SimpleStatsInterface observer : observers) {
      observer.updateValues(max, mean);
    }
  }
  public void addObserver(SimpleStatsInterface observer) {
    observers.add(observer);
  }

  public int getMax() { return max; }
  public double getMean() { return mean; }
  public List<Integer> getNumbers() { return numbers; }
}
