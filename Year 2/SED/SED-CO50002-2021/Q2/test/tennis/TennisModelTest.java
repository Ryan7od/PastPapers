package tennis;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Assert;
import org.junit.Test;

public class TennisModelTest {
    JUnitRuleMockery context = new JUnitRuleMockery();
    TennisObserver observer = context.mock(TennisObserver.class);

    @Test
    public void constantDueceAceDoesntEnd() {
        TennisModel model = new TennisModel();

        for (int i = 0; i < 100; i++) {
            Assert.assertFalse(model.playerOneScores());
            Assert.assertFalse(model.playerTwoScores());
        }
    }

    @Test
    public void correctScoreOnNormalGame() {
        TennisModel model = new TennisModel();

        model.playerTwoScores();
        Assert.assertEquals("Love - 15", model.score());
        model.playerTwoScores();
        Assert.assertEquals("Love - 30", model.score());
        model.playerOneScores();
        Assert.assertEquals("15 - 30", model.score());
        model.playerTwoScores();
        Assert.assertEquals("15 - 40", model.score());
        model.playerOneScores();
        Assert.assertEquals("30 - 40", model.score());
        model.playerTwoScores();
        Assert.assertEquals("Game Player 2", model.score());
    }

    @Test
    public void gameOverIdentifiedCorrectly() {
        TennisModel model1 = new TennisModel();

        Assert.assertFalse(model1.playerTwoScores());
        Assert.assertFalse(model1.playerTwoScores());
        Assert.assertFalse(model1.playerOneScores());
        Assert.assertFalse(model1.playerTwoScores());
        Assert.assertFalse(model1.playerOneScores());
        Assert.assertTrue(model1.playerTwoScores());

        TennisModel model2 = new TennisModel();

        for (int i = 0; i < 10; i++) {
            Assert.assertFalse(model2.playerOneScores());
            Assert.assertFalse(model2.playerTwoScores());
        }
        Assert.assertFalse(model2.playerOneScores());
        Assert.assertTrue(model2.playerOneScores());

        TennisModel model3 = new TennisModel();

        Assert.assertFalse(model3.playerOneScores());
        Assert.assertFalse(model3.playerOneScores());
        Assert.assertFalse(model3.playerOneScores());
        for (int i = 0; i < 100; i++) {
            Assert.assertTrue(model3.playerOneScores());
            Assert.assertTrue(model3.playerTwoScores());
        }
    }

    @Test
    public void scoreupdatedEveryPoint() {
        TennisModel model = new TennisModel();
        model.addObserver(observer);

        context.checking(new Expectations() {{
            exactly(200).of(observer).updateScore(with(any(String.class)));
        }});

        for (int i = 0; i < 100; i++) {
            model.playerOneScores();
            model.playerTwoScores();
        }
    }
}
