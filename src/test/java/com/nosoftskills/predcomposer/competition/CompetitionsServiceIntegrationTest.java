package com.nosoftskills.predcomposer.competition;

import com.nosoftskills.predcomposer.model.Competition;
import com.nosoftskills.predcomposer.model.Game;
import com.nosoftskills.predcomposer.model.Prediction;
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
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Ivan St. Ivanov
 */
@RunWith(Arquillian.class)
public class CompetitionsServiceIntegrationTest {

    private static Competition testCompetition;
    private static Game testGame;

    @Deployment
    public static WebArchive createDeployment() {
        WebArchive webArchive = ShrinkWrap.create(WebArchive.class, "predcomposer-test.war")
                .addClass(CompetitionsService.class)
                .addPackage(Prediction.class.getPackage())
                .addAsResource(new File("src/test/resources/META-INF/persistence-scenarios.xml"),
                        "META-INF/persistence.xml");
        System.out.println(webArchive.toString(true));
        return webArchive;
    }

    @Inject
    private CompetitionsService competitionsService;

    @Test
    @InSequence(1)
    public void shouldCreateCompetition() throws Exception {
        testCompetition = new Competition("Champions League 2016/2017", "These are the champions");
        testGame = new Game("Manchester City", "Juventus", LocalDateTime.of(2016, 9, 15, 21, 45));
        testCompetition.getGames().add(testGame);
        Competition persistedCompetition = competitionsService.storeCompetition(testCompetition);
        assertNotNull(persistedCompetition.getId());
        assertEquals(testCompetition, persistedCompetition);
    }

    @Test
    @InSequence(2)
    public void shouldFindCompetitionById() throws Exception {
        Competition competition = competitionsService.findCompetitionById(testCompetition.getId());
        assertNotNull(competition);
        assertEquals(testCompetition, competition);
    }

    @Test
    @InSequence(3)
    public void shouldGetAllGamesForCompetition() throws Exception {
        Set<Game> gamesForCompetition = competitionsService.getGamesForCompetition(testCompetition);
        assertNotNull(gamesForCompetition);
        assertEquals(1, gamesForCompetition.size());
        assertTrue(gamesForCompetition.contains(testGame));
    }
}
