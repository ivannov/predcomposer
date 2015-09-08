package com.nosoftskills.predcomposer.score;

import com.nosoftskills.predcomposer.common.TestData;
import com.nosoftskills.predcomposer.model.Game;
import com.nosoftskills.predcomposer.model.Prediction;
import com.nosoftskills.predcomposer.rankings.ScoreEvaluator;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

/**
 * @author Ivan St. Ivanov
 */
public class ScoreEvaluatorTest {

    private Game predictedGame;

    @Before
    public void setUp() throws Exception {
        predictedGame = new Game("teamA", "teamB", LocalDateTime.now());
    }

    @Test
    public void shouldCalculatePointsCorrectlyHomeWin() throws Exception {
        ScoreEvaluator scoreEvaluator = new ScoreEvaluator();
        predictedGame.setResult("3:1");
        Prediction prediction1 = new Prediction(TestData.user1, predictedGame, "3:0");
        Prediction prediction2 = new Prediction(TestData.user1, predictedGame, "3:1");
        Prediction prediction3 = new Prediction(TestData.user1, predictedGame, "2:0");
        Prediction prediction4 = new Prediction(TestData.user1, predictedGame, "3:3");
        Prediction prediction5 = new Prediction(TestData.user1, predictedGame, "0:1");
        assertEquals(ScoreEvaluator.POINTS_FOR_SIGN, scoreEvaluator.calculatePointsForPrediction(prediction1));
        assertEquals(ScoreEvaluator.POINTS_FOR_RESULT, scoreEvaluator.calculatePointsForPrediction(prediction2));
        assertEquals(ScoreEvaluator.POINTS_FOR_SIGN, scoreEvaluator.calculatePointsForPrediction(prediction3));
        assertEquals(0, scoreEvaluator.calculatePointsForPrediction(prediction4));
        assertEquals(0, scoreEvaluator.calculatePointsForPrediction(prediction5));
    }

    @Test
    public void shouldCalculatePointsCorrectlyDraw() throws Exception {
        ScoreEvaluator scoreEvaluator = new ScoreEvaluator();
        predictedGame.setResult("1:1");
        Prediction prediction1 = new Prediction(TestData.user1, predictedGame, "1:0");
        Prediction prediction2 = new Prediction(TestData.user1, predictedGame, "3:1");
        Prediction prediction3 = new Prediction(TestData.user1, predictedGame, "0:0");
        Prediction prediction4 = new Prediction(TestData.user1, predictedGame, "1:1");
        Prediction prediction5 = new Prediction(TestData.user1, predictedGame, "0:1");
        assertEquals(0, scoreEvaluator.calculatePointsForPrediction(prediction1));
        assertEquals(0, scoreEvaluator.calculatePointsForPrediction(prediction2));
        assertEquals(ScoreEvaluator.POINTS_FOR_SIGN, scoreEvaluator.calculatePointsForPrediction(prediction3));
        assertEquals(ScoreEvaluator.POINTS_FOR_RESULT, scoreEvaluator.calculatePointsForPrediction(prediction4));
        assertEquals(0, scoreEvaluator.calculatePointsForPrediction(prediction5));
    }

    @Test
    public void shouldCalculatePointsAwayWin() throws Exception {
        ScoreEvaluator scoreEvaluator = new ScoreEvaluator();
        predictedGame.setResult("1:2");
        Prediction prediction1 = new Prediction(TestData.user1, predictedGame, "1:0");
        Prediction prediction2 = new Prediction(TestData.user1, predictedGame, "1:1");
        Prediction prediction3 = new Prediction(TestData.user1, predictedGame, "0:2");
        Prediction prediction4 = new Prediction(TestData.user1, predictedGame, "1:2");
        Prediction prediction5 = new Prediction(TestData.user1, predictedGame, "0:4");
        assertEquals(0, scoreEvaluator.calculatePointsForPrediction(prediction1));
        assertEquals(0, scoreEvaluator.calculatePointsForPrediction(prediction2));
        assertEquals(ScoreEvaluator.POINTS_FOR_SIGN, scoreEvaluator.calculatePointsForPrediction(prediction3));
        assertEquals(ScoreEvaluator.POINTS_FOR_RESULT, scoreEvaluator.calculatePointsForPrediction(prediction4));
        assertEquals(ScoreEvaluator.POINTS_FOR_SIGN, scoreEvaluator.calculatePointsForPrediction(prediction5));
    }
}
