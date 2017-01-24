package com.nosoftskills.predcomposer.competition;

import com.nosoftskills.predcomposer.model.Competition;
import com.nosoftskills.predcomposer.model.Game;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.Set;

/**
 * @author Ivan St. Ivanov
 */
@Stateless
public class CompetitionsService implements Serializable {

    public static final String DEFAULT_COMPETITION_NAME = "Champions League 2016-2017";

    private static final long serialVersionUID = 7432416155835050214L;

    @PersistenceContext
    EntityManager entityManager;

    private static Competition activeCompetition;

    public Competition findCompetitionById(Long competitionId) {
        return entityManager.find(Competition.class, competitionId);
    }


    public Competition findActiveCompetition() {
        if (activeCompetition == null) {
            TypedQuery<Competition> competitionQuery = entityManager.createQuery("SELECT c FROM Competition c WHERE c.name = :defaultCompetition", Competition.class);
            competitionQuery.setParameter("defaultCompetition", DEFAULT_COMPETITION_NAME);
            activeCompetition = competitionQuery.getSingleResult();
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
