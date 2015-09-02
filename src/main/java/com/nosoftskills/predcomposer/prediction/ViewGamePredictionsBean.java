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
@Named("gamePredictionsViewer")
@RequestScoped
public class ViewGamePredictionsBean {

    private Game game;

    private Set<Prediction> predictions;

    @Inject
    private PredictionsService predictionsService;

    public String showGamePredictions(Game game) {
        this.game = game;
        this.predictions = predictionsService.getPredictionsForGame(game);
        return "/gamePredictions";
    }

    public Game getGame() {
        return game;
    }

    public Set<Prediction> getPredictions() {
        return predictions;
    }
}
