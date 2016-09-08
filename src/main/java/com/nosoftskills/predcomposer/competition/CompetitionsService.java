package com.nosoftskills.predcomposer.competition;

import com.nosoftskills.predcomposer.model.Competition;
import com.nosoftskills.predcomposer.model.Game;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.Set;

/**
 * @author Ivan St. Ivanov
 */
@Stateless
public class CompetitionsService implements Serializable {

    private static final long serialVersionUID = 7432416155835050214L;

    @PersistenceContext
    EntityManager entityManager;

    private static Competition activeCompetition;

    public Competition findCompetitionById(Long competitionId) {
        return entityManager.find(Competition.class, competitionId);
    }


    public Competition findActiveCompetition() {
        if (activeCompetition == null) {
            activeCompetition = entityManager.createQuery("SELECT c FROM Competition c", Competition.class).getSingleResult();
        }
        return activeCompetition;
    }

    public Competition storeCompetition(Competition competition) {
        if (competition.getId() == null) {
            entityManager.persist(competition);
            return competition;
        } else {
            return entityManager.merge(competition);
        }
    }

    public Set<Game> getGamesForCompetition(Competition selectedCompetition) {
        Competition mergedCompetition = entityManager.merge(selectedCompetition);
        return mergedCompetition.getGames();
    }
}
