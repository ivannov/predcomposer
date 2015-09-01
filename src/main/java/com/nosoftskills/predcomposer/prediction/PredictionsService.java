package com.nosoftskills.predcomposer.prediction;

import com.nosoftskills.predcomposer.model.Competition;
import com.nosoftskills.predcomposer.model.Game;
import com.nosoftskills.predcomposer.model.Prediction;
import com.nosoftskills.predcomposer.model.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * @author Ivan St. Ivanov
 */
@Stateless
public class PredictionsService implements Serializable {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Prediction> getPredictionsForUserAndCompetition(User user, Competition competition) {
        TypedQuery<Prediction> predictionsQuery = entityManager
                .createNamedQuery("getPredictionsForUserAndCompetition", Prediction.class);
        predictionsQuery.setParameter("user", user);
        predictionsQuery.setParameter("competition", competition);
        return predictionsQuery.getResultList();
    }

    public void store(Prediction prediction) {
        if (prediction.getId() == null) {
            entityManager.persist(prediction);
        } else {
            entityManager.merge(prediction);
        }
    }

    public Set<Prediction> getPredictionsForGame(Game game) {
        Game mergedGame = entityManager.merge(game);
        return mergedGame.getPredictions();
    }
}
