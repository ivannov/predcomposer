package com.nosoftskills.predcomposer.rankings;

import com.nosoftskills.predcomposer.model.Game;
import com.nosoftskills.predcomposer.model.Prediction;

import javax.enterprise.context.ApplicationScoped;
import java.util.stream.Stream;

/**
 * @author Ivan St. Ivanov
 */
@ApplicationScoped
public class ScoreEvaluator {

    public static final int POINTS_FOR_RESULT = 5;
    public static final int POINTS_FOR_SIGN = 3;

    public int calculatePointsForPrediction(Prediction prediction) {
        String predictedResult = prediction.getPredictedResult();
        Game game = prediction.getForGame();
        if (game.getResult() != null && predictedResult != null) {
            int[] predictedGoals = convert(predictedResult);
            int[] actualGoals = convert(game.getResult());

            if (predictedGoals[0] == actualGoals[0] && predictedGoals[1] == actualGoals[1]) {
                return POINTS_FOR_RESULT;
            } else if (Integer.signum(predictedGoals[0] - predictedGoals[1]) ==
                    Integer.signum(actualGoals[0] - actualGoals[1])) {
                return POINTS_FOR_SIGN;
            }
        }
        return 0;
    }

    private int[] convert(String result) {
        return Stream.of(result.split(":")).mapToInt(Integer::parseInt).toArray();
    }
}
