package Q2;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class SimpleStats {

  private final List<Integer> numbers = new ArrayList<>();
  private int max;
  private double mean;

  private void display() {

    JFrame frame = new JFrame("Simple Stats");
    frame.setSize(250, 350);

    JPanel panel = new JPanel();

    JTextField currentMax = new JTextField(11);
    JTextField currentMean = new JTextField(11);

    panel.add(new JLabel("Max: value "));
    panel.add(currentMax);
    panel.add(new JLabel("Mean: value "));
    panel.add(currentMean);

    for (int i = 1; i <= 12; i++) {
      final int n = i;
      JButton button = new JButton(String.valueOf(i));
      button.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          numbers.add(n);
          max = Math.max(max, n);
          mean = numbers.stream().mapToInt(val -> val).average().orElse(0.0);
          currentMax.setText(String.valueOf(max));
          currentMean.setText(String.valueOf(mean));
        }
      });
      panel.add(button);
    }

    frame.getContentPane().add(panel);

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  }

  public static void main(String[] args) {
    new SimpleStats().display();
  }

}
