package com.nosoftskills.predcomposer.game;

import com.nosoftskills.predcomposer.common.BaseServiceTest;
import com.nosoftskills.predcomposer.model.Game;
import org.junit.Test;

import java.time.LocalDateTime;

import static com.nosoftskills.predcomposer.common.TestData.competition;
import static com.nosoftskills.predcomposer.common.TestData.game1;
import static com.nosoftskills.predcomposer.common.TestData.game2;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Ivan St. Ivanov
 */
public class GamesServiceTest extends BaseServiceTest {

    private GamesService gamesService;

    @Override
    protected void setupClassUnderTest() {
        this.gamesService = new GamesService();
        this.gamesService.entityManager = entityManager;
    }

    @Test
    public void shouldFindGameById() throws Exception {
        Game foundGame = gamesService.findGameById(game1.getId());
        assertNotNull(foundGame);
        assertEquals(game1, foundGame);
    }

    @Test
    public void shouldCreateGame() throws Exception {
        Game newGame = new Game("Manchester City", "Juventus", LocalDateTime.of(2015, 9, 15, 21, 45));
        Game storedGame = gamesService.storeGame(newGame, competition);
        assertNotNull(storedGame.getId());
        assertEquals(newGame, storedGame);
    }

    @Test
    public void shouldToggleGameLock() throws Exception {
        boolean locked = game2.isLocked();

        gamesService.toggleLockedMode(game2);
        assertNotEquals(locked, entityManager.find(Game.class, game2.getId()).isLocked());

        gamesService.toggleLockedMode(entityManager.find(Game.class, game2.getId()));
        assertEquals(locked, entityManager.find(Game.class, game2.getId()).isLocked());
    }

    @Test
    public void shouldUpdateGame() throws Exception {
        Game gameToUpdate = gamesService.findGameById(game2.getId());
        gameToUpdate.setResult("12:12");
        Game storedGame = gamesService.storeGame(gameToUpdate, competition);
        assertEquals(game2.getId(), storedGame.getId());

        Game updatedGame = gamesService.findGameById(game2.getId());
        assertEquals("12:12", updatedGame.getResult());
    }
}
