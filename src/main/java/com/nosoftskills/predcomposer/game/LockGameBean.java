package com.nosoftskills.predcomposer.game;

import com.nosoftskills.predcomposer.model.Game;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author Ivan St. Ivanov
 */
@Named("gameLocker")
@RequestScoped
public class LockGameBean {

    @Inject
    private GamesService gamesService;

    public String toggleLockMode(Game game) {
        gamesService.toggleLockedMode(game);
        return "/futureGames.xhtml";
    }
}
