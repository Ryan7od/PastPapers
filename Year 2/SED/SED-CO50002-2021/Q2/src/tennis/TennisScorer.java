package tennis;

import javax.swing.*;

public class TennisScorer {

  private int playerOneScore = 0;
  private int playerTwoScore = 0;

  private final String[] scoreNames = {"Love", "15", "30", "40"};

  public static void main(String[] args) {
    new TennisScorer().display();
  }

  private void display() {

    JFrame window = new JFrame("Tennis");
    window.setSize(400, 150);

    JButton playerOneScores = new JButton("Player One Scores");
    JButton playerTwoScores = new JButton("Player Two Scores");

    JTextField scoreDisplay = new JTextField(20);
    scoreDisplay.setHorizontalAlignment(JTextField.CENTER);
    scoreDisplay.setEditable(false);

    playerOneScores.addActionListener(
            e -> {
              playerOneWinsPoint();
              scoreDisplay.setText(score());
              if (gameHasEnded()) {
                playerOneScores.setEnabled(false);
                playerTwoScores.setEnabled(false);
              }
            });

    playerTwoScores.addActionListener(
            e -> {
              playerTwoWinsPoint();
              scoreDisplay.setText(score());
              if (gameHasEnded()) {
                playerOneScores.setEnabled(false);
                playerTwoScores.setEnabled(false);
              }
            });

    JPanel panel = new JPanel();
    panel.add(playerOneScores);
    panel.add(playerTwoScores);
    panel.add(scoreDisplay);

    window.add(panel);

    window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    window.setVisible(true);

  }

  private String score() {

    if (playerOneScore > 2 && playerTwoScore > 2) {
      int difference = playerOneScore - playerTwoScore;
      switch (difference) {
        case 0:
          return "Deuce";
        case 1:
          return "Advantage Player 1";
        case -1:
          return "Advantage Player 2";
        case 2:
          return "Game Player 1";
        case -2:
          return "Game Player 2";
      }
    }

    if (playerOneScore > 3) {
      return "Game Player 1";
    }
    if (playerTwoScore > 3) {
      return "Game Player 2";
    }
    if (playerOneScore == playerTwoScore) {
      return scoreNames[playerOneScore] + " all";
    }
    return scoreNames[playerOneScore] + " - " + scoreNames[playerTwoScore];
  }

  private void playerOneWinsPoint() {
    playerOneScore++;
  }

  private void playerTwoWinsPoint() {
    playerTwoScore++;
  }

  private boolean gameHasEnded() {
    return score().contains("Game");
  }

}