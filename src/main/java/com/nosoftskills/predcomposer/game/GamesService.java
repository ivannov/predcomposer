package com.nosoftskills.predcomposer.game;

import com.nosoftskills.predcomposer.model.Competition;
import com.nosoftskills.predcomposer.model.Game;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;

/**
 * @author Ivan St. Ivanov
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class GamesService {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Game> getFutureGamesForCompetition(Competition competition) {
        TypedQuery<Game> gamesQuery = entityManager
                .createNamedQuery("getFutureGamesForCompetition", Game.class);
        gamesQuery.setParameter("competition", competition);
        gamesQuery.setParameter("after", LocalDate.now().atStartOfDay());
        return gamesQuery.getResultList();
    }

    public List<Game> getCompletedGamesForCompetition(Competition competition) {
        TypedQuery<Game> gamesQuery = entityManager
                .createNamedQuery("getCompletedGamesForCompetition", Game.class);
        gamesQuery.setParameter("competition", competition);
        return gamesQuery.getResultList();
    }
}
