package com.nosoftskills.predcomposer.game;

import com.nosoftskills.predcomposer.model.Competition;
import com.nosoftskills.predcomposer.model.Game;
import com.nosoftskills.predcomposer.model.Prediction;
import com.nosoftskills.predcomposer.model.User;
import com.nosoftskills.predcomposer.prediction.PredictionsService;
import com.nosoftskills.predcomposer.session.UserContext;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ivan St. Ivanov
 */
@Named("gamesViewer")
@RequestScoped
public class ViewGamesBean {

    private Map<Long, Prediction> currentUserPredictions = new HashMap<>();

    @Inject
    private GamesService gamesService;

    @Inject
    private PredictionsService predictionsService;

    @Inject
    private UserContext userContext;

    public List<Game> getFutureGamesForCurrentCompetition() {
        Competition selectedCompetition = userContext.getSelectedCompetition();
        loadCurrentUserPredictions(selectedCompetition);
        return gamesService.getFutureGamesForCompetition(selectedCompetition);
    }

    public List<Game> getCompletedGamesForCurrentCompetition() {
        Competition selectedCompetition = userContext.getSelectedCompetition();
        loadCurrentUserPredictions(selectedCompetition);
        return gamesService.getCompletedGamesForCompetition(selectedCompetition);
    }

    private void loadCurrentUserPredictions(Competition selectedCompetition) {
        User loggedUser = userContext.getLoggedUser();
        List<Prediction> predictions = predictionsService.getPredictionsForUserAndCompetition(
                loggedUser, selectedCompetition);
        predictions.forEach(prediction -> currentUserPredictions.put(prediction.getForGame().getId(), prediction));
    }

    public Map<Long, Prediction> getCurrentUserPredictions() {
        return currentUserPredictions;
    }
}
