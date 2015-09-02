package com.nosoftskills.predcomposer.game;

import com.nosoftskills.predcomposer.model.Game;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;

/**
 * @author Ivan St. Ivanov
 */
@Singleton
public class AutomaticGameLocker {

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private Event<Game> gameEvent;

    @Schedule(hour = "*", minute = "10,25,40,55")
    public void lockRecentGames() {
        final LocalDateTime now = LocalDateTime.now();
        final LocalDateTime fiveMinutesLater = now.plusMinutes(5);
        TypedQuery<Game> recentGamesQuery = entityManager.createNamedQuery("getRecentGames",
                Game.class);
        recentGamesQuery.setParameter("kickoffTime", fiveMinutesLater);
        recentGamesQuery.getResultList().forEach(this::lockGame);
    }

    private void lockGame(Game game) {
        game.setLocked(true);
        gameEvent.fire(game);
    }
}
