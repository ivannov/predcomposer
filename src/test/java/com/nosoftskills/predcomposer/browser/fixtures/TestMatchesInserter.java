/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.nosoftskills.predcomposer.browser.fixtures;

import com.nosoftskills.predcomposer.model.Competition;
import com.nosoftskills.predcomposer.model.Game;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.Arrays;

@Singleton
@Startup
public class TestMatchesInserter {

    @PersistenceContext
    private EntityManager entityManager;

    @PostConstruct
    public void insertCompletedMatched() {
        Competition competition = new Competition("Champions League");

        Game game1 = new Game("Milan", "Real Madrid", "5:0", LocalDateTime.now().minusDays(1), true);
        Game game2 = new Game("Barcelona", "Manchester United", "3:1", LocalDateTime.now().minusDays(2), true);
        Game game3 = new Game("CSKA", "Bayern Munchen", "4:3", LocalDateTime.now().minusDays(3), true);
        Game game4 = new Game("Antwerp", "Levski", LocalDateTime.now().plusDays(2));
        Game game5 = new Game("Roma", "Liverpool", LocalDateTime.now().plusDays(3));

        competition.getGames().addAll(Arrays.asList(game1, game2, game3, game4, game5));

        entityManager.persist(competition);
    }
}
