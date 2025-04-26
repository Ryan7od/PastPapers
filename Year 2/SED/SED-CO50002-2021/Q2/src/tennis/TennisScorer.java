package tennis;

import javax.swing.*;

public class TennisScorer implements TennisObserver {

  private final TennisModel model = new TennisModel();
  private JButton playerOneScores = new JButton("Player One Scores");
  private JButton playerTwoScores = new JButton("Player Two Scores");
  public JTextField scoreDisplay = new JTextField(20);

  public static void main(String[] args) {
    new TennisScorer().display();
  }

  private void display() {
    model.addObserver(this);

    JFrame window = new JFrame("Tennis");
    window.setSize(400, 150);

    scoreDisplay.setHorizontalAlignment(JTextField.CENTER);
    scoreDisplay.setEditable(false);

    playerOneScores.addActionListener(
            e -> {
              model.playerOneScores();
            });

    playerTwoScores.addActionListener(
            e -> {
              model.playerTwoScores();
            });

    JPanel panel = new JPanel();
    panel.add(playerOneScores);
    panel.add(playerTwoScores);
    panel.add(scoreDisplay);

    window.add(panel);

    window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    window.setVisible(true);
  }

  public void updateGameOver() {
      playerOneScores.setEnabled(false);
      playerTwoScores.setEnabled(false);
  }

  public void updateScore(String score) {
      scoreDisplay.setText(score);
  }
}