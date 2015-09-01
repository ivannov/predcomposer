package com.nosoftskills.predcomposer.prediction;

import com.nosoftskills.predcomposer.model.Game;
import com.nosoftskills.predcomposer.model.Prediction;
import com.nosoftskills.predcomposer.session.UserContext;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * @author Ivan St. Ivanov
 */
@Named("predictionEditor")
@ConversationScoped
public class EditPredictionBean implements Serializable {

    private Game predictedGame;
    private String homeTeamPredictedGoals;
    private String awayTeamPredictedGoals;
    private Prediction prediction;

    @Inject
    private UserContext userContext;
    @Inject
    private PredictionsService predictionsService;

    @Inject
    private Conversation conversation;

    public String getHomeTeam() {
        return predictedGame.getHomeTeam();
    }

    public String getAwayTeam() {
        return predictedGame.getAwayTeam();
    }

    public String getHomeTeamPredictedGoals() {
        return homeTeamPredictedGoals;
    }

    public void setHomeTeamPredictedGoals(String homeTeamPredictedGoals) {
        this.homeTeamPredictedGoals = homeTeamPredictedGoals;
    }

    public String getAwayTeamPredictedGoals() {
        return awayTeamPredictedGoals;
    }

    public void setAwayTeamPredictedGoals(String awayTeamPredictedGoals) {
        this.awayTeamPredictedGoals = awayTeamPredictedGoals;
    }

    public String requestPrediction(Game predictedGame, Prediction prediction) {
        conversation.begin();
        this.predictedGame = predictedGame;

        if (prediction != null) {
            this.prediction = prediction;
            String[] scores = prediction.getPredictedResult().split(":");
            this.homeTeamPredictedGoals = scores[0];
            this.awayTeamPredictedGoals = scores[1];
        }

        return "editPrediction";
    }

    public String submitPrediction() {
        if (prediction == null) {
            prediction = new Prediction();
            prediction.setForGame(predictedGame);
            prediction.setByUser(userContext.getLoggedUser());
        }
        prediction.setPredictedResult(homeTeamPredictedGoals + ":" + awayTeamPredictedGoals);
        predictionsService.store(prediction);
        conversation.end();
        return "futureGames";
    }
}
