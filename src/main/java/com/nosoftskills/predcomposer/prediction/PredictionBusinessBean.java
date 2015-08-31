package com.nosoftskills.predcomposer.prediction;

import com.nosoftskills.predcomposer.model.Competition;
import com.nosoftskills.predcomposer.model.Prediction;
import com.nosoftskills.predcomposer.model.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.List;

/**
 * @author Ivan St. Ivanov
 */
@Stateless
public class PredictionBusinessBean implements Serializable {

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
}
