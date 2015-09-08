package com.nosoftskills.predcomposer.game;

import com.nosoftskills.predcomposer.alternatives.UserContextAlternative;
import com.nosoftskills.predcomposer.common.TestData;
import com.nosoftskills.predcomposer.competition.CompetitionsService;
import com.nosoftskills.predcomposer.model.Competition;
import com.nosoftskills.predcomposer.model.Game;
import com.nosoftskills.predcomposer.prediction.GameLockedException;
import com.nosoftskills.predcomposer.session.UserContext;
import com.nosoftskills.predcomposer.user.PasswordHashUtil;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.io.File;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Ivan St. Ivanov
 */
@RunWith(Arquillian.class)
public class GamesServiceIntegrationTest {

    private static Competition testCompetition;
    private static Game testGame;

    @Deployment
    public static WebArchive createDeployment() {
        WebArchive webArchive = ShrinkWrap.create(WebArchive.class, "predcomposer-test.war")
                .addClass(GamesService.class)
                .addClass(CompetitionsService.class)
                .addClass(GameLockedException.class)
                .addClasses(UserContextAlternative.class, UserContext.class, TestData.class,
                        PasswordHashUtil.class)
                .addPackage(Game.class.getPackage())
                .addAsResource(new File("src/main/resources/META-INF/persistence.xml"),
                        "META-INF/persistence.xml")
                .addAsWebInfResource("test-beans.xml", "beans.xml");
        System.out.println(webArchive.toString(true));
        return webArchive;
    }

    @Inject
    public GamesService gamesService;

    @Inject
    public CompetitionsService competitionsService;

    @Test
    @InSequence(1)
    public void shouldCreateGame() throws Exception {
        testCompetition = new Competition("Champions League 2015/2016", "Ils sont les meilleurs");
        competitionsService.storeCompetition(testCompetition);

        testGame = new Game("Manchester City", "Juventus", LocalDateTime.of(2015, 9, 15, 21, 45));
        Game storedGame = gamesService.storeGame(testGame, testCompetition);

        assertNotNull(storedGame.getId());
        assertEquals(testGame, storedGame);
    }

    @Test
    @InSequence(2)
    public void shouldFindGameById() throws Exception {
        Game foundGame = gamesService.findGameById(testGame.getId());
        assertNotNull(foundGame);
        assertEquals(testGame, foundGame);
    }

    @Test
    @InSequence(3)
    public void shouldLockGame() throws Exception {
        gamesService.toggleLockedMode(testGame);
        assertTrue(gamesService.findGameById(testGame.getId()).isLocked());
    }

    @Test
    @InSequence(4)
    public void shouldChangeTheResult() throws Exception {
        Game gameToChange = gamesService.findGameById(testGame.getId());
        gameToChange.setResult("3:2");
        gamesService.storeGame(gameToChange, testCompetition);
        assertEquals("3:2", gamesService.findGameById(testGame.getId()).getResult());
    }
}
