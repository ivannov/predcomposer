package com.nosoftskills.predcomposer.competition;

import com.nosoftskills.predcomposer.model.Competition;
import com.nosoftskills.predcomposer.model.Game;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Set;

/**
 * @author Ivan St. Ivanov
 */
@Stateless
public class CompetitionsService {

    @PersistenceContext
    EntityManager entityManager;

    public Competition findCompetitionById(Long competitionId) {
        return entityManager.find(Competition.class, competitionId);
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
