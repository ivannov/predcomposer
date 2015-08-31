package com.nosoftskills.predcomposer.game;

import com.nosoftskills.predcomposer.model.Competition;
import com.nosoftskills.predcomposer.model.Game;
import com.nosoftskills.predcomposer.model.Prediction;
import com.nosoftskills.predcomposer.model.User;
import com.nosoftskills.predcomposer.prediction.PredictionBusinessBean;
import com.nosoftskills.predcomposer.user.UserContext;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ivan St. Ivanov
 */
@Named("futureGames")
@RequestScoped
public class FutureGamesBean {

    @Inject
    private GamesBusinessBean gamesBusinessBean;

    @Inject
    private UserContext userContext;

    private Map<Long, Prediction> currentUserPredictions = new HashMap<>();

    @Inject
    private PredictionBusinessBean predictionsBusinessBean;

    public List<Game> getFutureGamesForCurrentCompetition() {
        User loggedUser = userContext.getLoggedUser();
        Competition selectedCompetition = userContext.getSelectedCompetition();
        List<Prediction> predictions = predictionsBusinessBean.getPredictionsForUserAndCompetition(
                loggedUser, selectedCompetition);
        predictions.forEach(prediction -> currentUserPredictions.put(prediction.getForGame().getId(), prediction));
        return gamesBusinessBean.getFutureGamesForCompetition(selectedCompetition);
    }

    public Map<Long, Prediction> getCurrentUserPredictions() {
        return currentUserPredictions;
    }
}
