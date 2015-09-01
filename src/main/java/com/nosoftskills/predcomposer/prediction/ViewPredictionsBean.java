package com.nosoftskills.predcomposer.prediction;

import com.nosoftskills.predcomposer.model.Game;
import com.nosoftskills.predcomposer.model.Prediction;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Set;

/**
 * @author Ivan St. Ivanov
 */
@Named("predictionsViewer")
@RequestScoped
public class ViewPredictionsBean {

    private Game game;
    private Set<Prediction> userPredictions;

    @Inject
    private PredictionsService predictionsService;

    public String getGamePredictions(Game game) {
        this.game = game;
        this.userPredictions = predictionsService.getPredictionsForGame(game);
        return "gamePredictions";
    }

    public Game getGame() {
        return game;
    }

    public Set<Prediction> getUserPredictions() {
        return userPredictions;
    }
}
