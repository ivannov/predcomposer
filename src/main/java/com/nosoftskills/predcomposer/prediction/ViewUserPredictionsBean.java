package com.nosoftskills.predcomposer.prediction;

import com.nosoftskills.predcomposer.model.Prediction;
import com.nosoftskills.predcomposer.session.UserContext;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Ivan St. Ivanov
 */
@Named("userPredictionsViewer")
@SessionScoped
public class ViewUserPredictionsBean implements Serializable {

    private Map<Long, Prediction> userPredictions = new HashMap<>();

    @Inject
    private UserContext userContext;

    @Inject
    private PredictionsService predictionService;

    @PostConstruct
    public void loadCurrentUserPredictions() {
        Set<Prediction> predictions = predictionService.getPredictionsForUser(userContext.getLoggedUser());
        predictions.forEach(this::addPrediction);
    }

    public Map<Long, Prediction> getUserPredictions() {
        return userPredictions;
    }

    public void predictionChanged(@Observes Prediction prediction) {
        addPrediction(prediction);
    }

    private void addPrediction(Prediction prediction) {
        userPredictions.put(prediction.getForGame().getId(), prediction);
    }
}
