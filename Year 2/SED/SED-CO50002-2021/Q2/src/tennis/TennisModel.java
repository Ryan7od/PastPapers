package tennis;

import java.util.ArrayList;
import java.util.List;

public class TennisModel {
    private int playerOneScore = 0;
    private int playerTwoScore = 0;

    private List<TennisObserver> observers = new ArrayList<>();

    private final String[] scoreNames = {"Love", "15", "30", "40"};

    public void addObserver(TennisObserver observer) {
        observers.add(observer);
    }

    private void updateObserversGameOver() {
        if (gameHasEnded()) {
            for (TennisObserver observer : observers) {
                observer.updateGameOver();
            }
        }
    }

    private void updateObserversScore() {
        for (TennisObserver observer : observers) {
            observer.updateScore(score());
        }
    }

    private void updateObservers() {
        updateObserversScore();
        updateObserversGameOver();
    }

    public boolean playerOneScores() {
        playerOneScore++;
        updateObservers();
        return gameHasEnded();
    }

    public boolean playerTwoScores() {
        playerTwoScore++;
        updateObservers();
        return gameHasEnded();
    }

    private boolean gameHasEnded() {
        return score().contains("Game");
    }

    public String score() {

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
}
