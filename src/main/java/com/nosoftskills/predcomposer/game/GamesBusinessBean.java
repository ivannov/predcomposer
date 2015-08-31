package com.nosoftskills.predcomposer.game;

import com.nosoftskills.predcomposer.model.Game;
import com.nosoftskills.predcomposer.user.UserContext;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;

/**
 * @author Ivan St. Ivanov
 */
@Named("game")
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class GamesBusinessBean {

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private UserContext userContext;

    public List<Game> getAllGamesForCurrentCompetition() {
        TypedQuery<Game> gamesQuery = entityManager
                .createNamedQuery("getAllFutureGamesForCompetition", Game.class);
        gamesQuery.setParameter("competition", userContext.getSelectedCompetition());
        gamesQuery.setParameter("after", LocalDate.now().atStartOfDay());
        return gamesQuery.getResultList();
    }
}
