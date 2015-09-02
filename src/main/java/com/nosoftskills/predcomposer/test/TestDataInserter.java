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
import java.util.Arrays;

import static com.nosoftskills.predcomposer.user.PasswordHashUtil.hashPassword;

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
        User user1 = new User("ivan", hashPassword("ivan"), "ivan@example.com", "Ivan", "Ivanov", true);
        entityManager.persist(user1);
        User user2 = new User("koko", hashPassword("koko"), "koko@example.com", "Koko", "Stefanov", false);
        entityManager.persist(user2);

        Game game1 = new Game("Manchester United", "Club Brugge", "3:1", LocalDateTime.of(2015, 8, 18, 21, 45), true);
        Game game2 = new Game("Club Brugge", "Manchester United", "0:4", LocalDateTime.of(2015, 8, 26, 21, 45), true);
        Game game3 = new Game("Paris SG", "Malmo", LocalDateTime.of(2015, 9, 15, 21, 45));
        Game game4 = new Game("Real Madrid", "Shakhtar Donetsk", LocalDateTime.of(2015, 9, 15, 21, 45));
        Game game5 = new Game("Leverkusen", "BATE", LocalDateTime.of(2015, 9, 16, 21, 45));
        Game game6 = new Game("Roma", "Barcelona", LocalDateTime.of(2015, 9, 16, 21, 45));

        DEFAULT_COMPETITION.getGames().addAll(Arrays.asList(game1, game2, game3, game4, game5, game6));
        entityManager.persist(DEFAULT_COMPETITION);

        entityManager.persist(new Prediction(user1, game2, "0:0"));
        entityManager.persist(new Prediction(user2, game2, "0:2"));
        entityManager.persist(new Prediction(user1, game3, "2:0"));
    }
}
