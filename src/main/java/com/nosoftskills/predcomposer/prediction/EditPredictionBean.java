package com.nosoftskills.predcomposer.prediction;

import com.nosoftskills.predcomposer.model.Game;
import com.nosoftskills.predcomposer.model.Prediction;
import com.nosoftskills.predcomposer.session.UserContext;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.event.Event;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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
    private Prediction thePrediction;

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

    public String showPredictionForm(Game predictedGame, Prediction prediction) {
        conversation.begin();
        this.predictedGame = predictedGame;

        if (prediction != null) {
            this.thePrediction = prediction;
            String[] scores = prediction.getPredictedResult().split(":");
            this.homeTeamPredictedGoals = scores[0];
            this.awayTeamPredictedGoals = scores[1];
        }

        return "/editPrediction";
    }

    @Inject
    private Event<Prediction> predictionEvent;

    public String submitPrediction() {
        if (thePrediction == null) {
            thePrediction = new Prediction();
            thePrediction.setForGame(predictedGame);
            thePrediction.setByUser(userContext.getLoggedUser());
        }
        thePrediction.setPredictedResult(homeTeamPredictedGoals + ":" + awayTeamPredictedGoals);
        try {
            predictionsService.store(thePrediction);
            predictionEvent.fire(thePrediction);
        } catch (GameLockedException gle) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, gle.getMessage(), gle.getMessage()));
        }
        conversation.end();
        return "/futureGames";
    }
}
