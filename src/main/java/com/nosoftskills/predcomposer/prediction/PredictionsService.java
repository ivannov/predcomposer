package com.nosoftskills.predcomposer.prediction;

import com.nosoftskills.predcomposer.model.Game;
import com.nosoftskills.predcomposer.model.Prediction;
import com.nosoftskills.predcomposer.model.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.Set;

/**
 * @author Ivan St. Ivanov
 */
@Stateless
public class PredictionsService implements Serializable {

    @PersistenceContext
    EntityManager entityManager;

    public void store(Prediction prediction) throws GameLockedException {
        final GameLockedException gameLockedException = new GameLockedException(
                "The game " + prediction.getForGame().getHomeTeam() + " - " +
                        prediction.getForGame().getAwayTeam() + " was locked before you submitted your proposal.");
        try {
            if (entityManager.merge(prediction.getForGame()).isLocked()) {
                throw gameLockedException;
            }
        } catch (OptimisticLockException ole) {
            throw gameLockedException;
        }

        if (prediction.getId() == null) {
            prediction.setByUser(entityManager.merge(prediction.getByUser()));
            entityManager.persist(prediction);
        } else {
            entityManager.merge(prediction);
        }
    }

    public Set<Prediction> getPredictionsForGame(Game game) {
        Game mergedGame = entityManager.merge(game);
        return mergedGame.getPredictions();
    }

    public Set<Prediction> getPredictionsForUser(User user) {
        User mergedUser = entityManager.find(User.class, user.getId());
        return mergedUser.getPredictions();
    }
}
