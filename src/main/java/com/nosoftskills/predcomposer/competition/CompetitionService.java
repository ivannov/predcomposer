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
public class CompetitionService {

    @PersistenceContext
    private EntityManager entityManager;

    public Set<Game> getGamesForCompetition(Competition selectedCompetition) {
        Competition mergedCompetition = entityManager.merge(selectedCompetition);
        return mergedCompetition.getGames();
    }
}
