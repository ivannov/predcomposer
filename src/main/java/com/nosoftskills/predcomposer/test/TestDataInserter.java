package com.nosoftskills.predcomposer.test;

import com.nosoftskills.predcomposer.model.Competition;
import com.nosoftskills.predcomposer.model.Game;
import com.nosoftskills.predcomposer.model.Prediction;
import com.nosoftskills.predcomposer.model.User;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;

/**
 * @author Ivan St. Ivanov
 */
@Singleton
@Startup
public class TestDataInserter {

    public static final Competition DEFAULT_COMPETITION = new Competition("Champions League 2015-2016");

    @PersistenceContext
    private EntityManager entityManager;

    @PostConstruct
    public void insertTestData() {
        User user1 = new User("ivan", "ivan", "ivan@example.com", "Ivan", "Ivanov");
        entityManager.persist(user1);

        entityManager.persist(DEFAULT_COMPETITION);

        entityManager.persist(new Game(DEFAULT_COMPETITION, "Manchester United", "Club Brugge", "3:1", LocalDateTime.of(2015, 8, 18, 21, 45), true));
        entityManager.persist(new Game(DEFAULT_COMPETITION, "Club Brugge", "Manchester United", "0:4", LocalDateTime.of(2015, 8, 26, 21, 45), true));
        Game game1 = new Game(DEFAULT_COMPETITION, "Paris SG", "Malmo",
                LocalDateTime.of(2015, 9, 15, 21, 45));
        entityManager.persist(game1);
        entityManager.persist(new Game(DEFAULT_COMPETITION, "Real Madrid", "Shakhtar Donetsk", LocalDateTime.of(2015, 9, 15, 21, 45)));
        entityManager.persist(new Game(DEFAULT_COMPETITION, "Leverkusen", "BATE", LocalDateTime.of(2015, 9, 16, 21, 45)));
        entityManager.persist(new Game(DEFAULT_COMPETITION, "Roma", "Barcelona", LocalDateTime.of(2015, 9, 16, 21, 45)));

        entityManager.persist(new Prediction(user1, game1, "2:0"));
    }
}
