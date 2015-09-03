package com.nosoftskills.predcomposer.prediction;

import com.nosoftskills.predcomposer.model.Prediction;
import com.nosoftskills.predcomposer.common.BaseServiceTest;
import org.junit.Test;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Ivan St. Ivanov
 */
public class PredictionsServiceTest extends BaseServiceTest {

    private PredictionsService predictionsService;

    @Override
    protected void setupClassUnderTest() {
        predictionsService = new PredictionsService();
        predictionsService.entityManager = entityManager;
    }

    @Test
    public void shouldReturnAllUserPredictions() throws Exception {
        Set<Prediction> predictionsForUser = predictionsService.getPredictionsForUser(user1);
        assertEquals(2, predictionsForUser.size());
        assertTrue(predictionsForUser.contains(prediction1));
        assertTrue(predictionsForUser.contains(prediction3));
    }

    @Test
    public void shouldReturnAllGamePredictions() throws Exception {
        Set<Prediction> predictionsForGame = predictionsService.getPredictionsForGame(game2);
        assertEquals(2, predictionsForGame.size());
        assertTrue(predictionsForGame.contains(prediction1));
        assertTrue(predictionsForGame.contains(prediction2));
    }

    @Test
    public void shouldStoreNewPrediction() throws Exception {
        Prediction newPrediction = new Prediction(user1, game4, "3:3");
        predictionsService.store(newPrediction);
        List<Prediction> predictionsForUser = entityManager.createQuery("SELECT p FROM Prediction p WHERE p.byUser.id = " + user1.getId(), Prediction.class).getResultList();
        assertEquals(3, predictionsForUser.size());
        assertTrue(predictionsForUser.contains(newPrediction));
    }

    @Test
    public void shouldUpdatePrediction() throws Exception {
        prediction3.setPredictedResult("5:3");
        predictionsService.store(prediction3);
        List<Prediction> predictions = entityManager.createQuery("SELECT p FROM Prediction p", Prediction.class).getResultList();
        assertEquals(3, predictions.size());
        Prediction prediction = entityManager.find(Prediction.class, prediction3.getId());
        assertEquals("5:3", prediction.getPredictedResult());
    }

    @Test(expected = GameLockedException.class)
    public void shouldNotAllowChangingPredictionOfLockedGame() throws Exception {
        prediction1.setPredictedResult("2:4");
        predictionsService.store(prediction1);
    }
}
