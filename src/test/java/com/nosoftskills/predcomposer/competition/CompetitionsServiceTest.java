package com.nosoftskills.predcomposer.competition;

import com.nosoftskills.predcomposer.common.BaseServiceTest;
import com.nosoftskills.predcomposer.model.Competition;
import com.nosoftskills.predcomposer.model.Game;
import org.junit.Test;

import java.util.Set;

import static com.nosoftskills.predcomposer.common.TestData.competition;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Ivan St. Ivanov
 */
public class CompetitionsServiceTest extends BaseServiceTest {

    private CompetitionsService competitionsService;

    @Override
    protected void setupClassUnderTest() {
        this.competitionsService = new CompetitionsService();
        competitionsService.entityManager = entityManager;
    }

    @Test
    public void shouldReturnAllGamesForCompetition() throws Exception {
        Set<Game> gamesForCompetition = competitionsService.getGamesForCompetition(competition);
        assertEquals(6, gamesForCompetition.size());
    }

    @Test
    public void shouldFindCompetitionById() throws Exception {
        Competition foundCompetition = competitionsService.findCompetitionById(competition.getId());
        assertNotNull(foundCompetition);
        assertEquals(foundCompetition, competition);
    }

    @Test
    public void shouldStoreCompetition() throws Exception {
        Competition newCompetition = new Competition("Premiership 2015/2016", "The English Premier League");
        Competition storedCompetition = competitionsService.storeCompetition(newCompetition);
        assertNotNull(storedCompetition.getId());

        assertEquals(newCompetition, entityManager.find(Competition.class, storedCompetition.getId()));
    }
}
