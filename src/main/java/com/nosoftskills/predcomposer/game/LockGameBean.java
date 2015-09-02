package com.nosoftskills.predcomposer.game;

import com.nosoftskills.predcomposer.model.Game;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author Ivan St. Ivanov
 */
@Named("gameLocker")
@ApplicationScoped
public class LockGameBean {

    @Inject
    private GamesService gamesService;

    @Inject
    private Event<Game> gameEvent;

    public String toggleLockMode(Game game) {
        Game lockedGame = gamesService.toggleLockedMode(game);
        gameEvent.fire(lockedGame);
        return "/futureGames.xhtml";
    }
}
