package com.nosoftskills.predcomposer.prediction;

import com.nosoftskills.predcomposer.common.TestData;
import com.nosoftskills.predcomposer.game.GamesService;
import com.nosoftskills.predcomposer.model.Game;
import com.nosoftskills.predcomposer.model.Prediction;
import com.nosoftskills.predcomposer.model.User;
import com.nosoftskills.predcomposer.user.PasswordHashUtil;
import com.nosoftskills.predcomposer.user.UsersService;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.io.File;
import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Ivan St. Ivanov
 */
@RunWith(Arquillian.class)
public class PredictionsServiceIntegrationTest {

    private Prediction testPrediction;
    private User testUser = new User("ivan", "ivan", "ivan@example.com");
    private Game testGame = new Game("Manchester City", "Juventus", LocalDateTime.of(2015, 9, 15, 21, 45));

    @Deployment
    public static WebArchive createDeployment() {
        WebArchive webArchive = ShrinkWrap.create(WebArchive.class, "predcomposer-test.war")
                .addClass(PredictionsService.class)
                .addClass(GameLockedException.class)
                .addClass(GamesService.class)
                .addClass(UsersService.class)
                .addClass(PasswordHashUtil.class)
                .addPackage(Prediction.class.getPackage())
                .addClass(TestData.class)
                .addAsResource(new File("src/main/resources/META-INF/persistence.xml"),
                        "META-INF/persistence.xml");
        System.out.println(webArchive.toString(true));
        return webArchive;
    }

    @Inject
    private PredictionsService predictionsService;

    @Inject
    private GamesService gamesService;

    @Inject
    private UsersService usersService;

    @Before
    public void createTestPrediction() throws Exception {
        usersService.storeUser(testUser);
        gamesService.storeGame(testGame);
        testPrediction = new Prediction(testUser, testGame, "2:2");
        predictionsService.store(testPrediction);
    }

    @Test
    public void shouldCreatePrediction() throws Exception {
        assertNotNull(testPrediction.getId());
    }

    @Test
    public void shouldGetCreatedPredictionForGame() throws Exception {
        Set<Prediction> predictionsForGame = predictionsService.getPredictionsForGame(testPrediction.getForGame());
        assertEquals(1, predictionsForGame.size());
        assertTrue(predictionsForGame.contains(testPrediction));
    }

    @Test
    @Ignore
    public void shouldGetCreatedPredictionForUser() throws Exception {
        Set<Prediction> predictionsForUser = predictionsService.getPredictionsForUser(testUser);
        assertEquals(1, predictionsForUser.size());
        assertTrue(predictionsForUser.contains(testPrediction));
    }

    @Test
    public void shouldUpdatePrediction() throws Exception {
        testPrediction.setPredictedResult("3:3");
        predictionsService.store(testPrediction);
        Prediction predictionInDb = predictionsService.findPredictionById(testPrediction.getId());
        assertEquals("3:3", predictionInDb.getPredictedResult());
    }
}
