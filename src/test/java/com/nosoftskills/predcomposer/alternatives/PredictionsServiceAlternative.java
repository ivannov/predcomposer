package com.nosoftskills.predcomposer.alternatives;

import com.nosoftskills.predcomposer.model.Game;
import com.nosoftskills.predcomposer.model.Prediction;
import com.nosoftskills.predcomposer.model.User;
import com.nosoftskills.predcomposer.prediction.PredictionsService;

import javax.enterprise.inject.Alternative;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static com.nosoftskills.predcomposer.common.TestData.game1;
import static com.nosoftskills.predcomposer.common.TestData.game2;
import static com.nosoftskills.predcomposer.common.TestData.prediction1;
import static com.nosoftskills.predcomposer.common.TestData.prediction2;
import static com.nosoftskills.predcomposer.common.TestData.prediction3;

/**
 * @author Ivan St. Ivanov
 */
@Alternative
public class PredictionsServiceAlternative extends PredictionsService {

    private static Set<Prediction> ivansPredictions = new HashSet<>();
    private static Set<Prediction> kokosPredictions = new HashSet<>();

    private static Set<Prediction> game3Predictions = new HashSet<>();
    private static Set<Prediction> game2Predictions = new HashSet<>();

    static {
        ivansPredictions.addAll(Arrays.asList(prediction1, prediction3));
        kokosPredictions.add(prediction2);

        game2Predictions.addAll(Arrays.asList(prediction1, prediction2));
        game3Predictions.add(prediction3);
    }

    @Override
    public Set<Prediction> getPredictionsForUser(User user) {
        switch (user.getUserName()) {
        case "ivan":
            return ivansPredictions;
        case "koko":
            return kokosPredictions;
        default:
            return null;
        }
    }

    @Override
    public Set<Prediction> getPredictionsForGame(Game game) {
        if (game.equals(game1)) {
            return game3Predictions;
        } else if (game.equals(game2)) {
            return game2Predictions;
        } else {
            return null;
        }
    }
}
