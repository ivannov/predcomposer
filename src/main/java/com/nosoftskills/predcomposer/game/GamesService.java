package com.nosoftskills.predcomposer.game;

import com.nosoftskills.predcomposer.model.Game;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;

/**
 * @author Ivan St. Ivanov
 */
@Stateless
public class GamesService implements Serializable {

    @PersistenceContext
    private EntityManager entityManager;

    public Game toggleLockedMode(Game game) {
        Game changedGame = entityManager.merge(game);
        changedGame.setLocked(!game.isLocked());
        return changedGame;
    }

    public Game storeGame(Game game) {
        if (game.getId() == null) {
            entityManager.persist(game);
            return game;
        } else {
            return entityManager.merge(game);
        }
    }
}
