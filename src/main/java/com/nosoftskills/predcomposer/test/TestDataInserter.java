package com.nosoftskills.predcomposer.test;

import com.nosoftskills.predcomposer.model.Competition;
import com.nosoftskills.predcomposer.model.Game;
import com.nosoftskills.predcomposer.model.Prediction;
import com.nosoftskills.predcomposer.model.User;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.Arrays;

import static com.nosoftskills.predcomposer.user.PasswordHashUtil.hashPassword;

/**
 * @author Ivan St. Ivanov
 */
@Singleton
@Startup
public class TestDataInserter {

    public static final String DEFAULT_COMPETITION_NAME = "Champions League 2015-2016";
    public static Competition defaultCompetition = null;

    @PersistenceContext
    private EntityManager entityManager;

    @PostConstruct
    public void insertTestData() {

        TypedQuery<Competition> query = entityManager.createNamedQuery("findCompetitionByName", Competition.class);
        query.setParameter("competitionName", DEFAULT_COMPETITION_NAME);

        try {
            defaultCompetition = query.getSingleResult();
            System.out.println("Data was already inserted");
            return;
        } catch (NoResultException nre) {
            System.out.printf("Application starting for the first time. Inserting data");
        }

        User user1 = new User("ivan", hashPassword("ivan"), "ivan@example.com", "Ivan", "Ivanov", true);
        User user2 = new User("koko", hashPassword("koko"), "koko@example.com", "Koko", "Stefanov", false);

        Game game1 = new Game("Manchester United", "Club Brugge", "3:1", LocalDateTime.of(2015, 8, 18, 21, 45), true);
        Game game2 = new Game("Club Brugge", "Manchester United", "0:4", LocalDateTime.of(2015, 8, 26, 21, 45), true);
        Game game3 = new Game("Paris SG", "Malmo", LocalDateTime.of(2015, 9, 15, 21, 45));
        Game game4 = new Game("Real Madrid", "Shakhtar Donetsk", LocalDateTime.of(2015, 9, 15, 21, 45));
        Game game5 = new Game("Leverkusen", "BATE", LocalDateTime.of(2015, 9, 16, 21, 45));
        Game game6 = new Game("Roma", "Barcelona", LocalDateTime.of(2015, 9, 16, 21, 45));


        Prediction prediction1 = new Prediction(user1, game2, "0:0");
        Prediction prediction2 = new Prediction(user2, game2, "0:2");
        Prediction prediction3 = new Prediction(user1, game3, "2:0");

        user1.getPredictions().addAll(Arrays.asList(prediction1, prediction3));
        user1.getPredictions().add(prediction2);

        entityManager.persist(user1);
        entityManager.persist(user2);

        game2.getPredictions().addAll(Arrays.asList(prediction1, prediction2));
        game3.getPredictions().add(prediction3);

        defaultCompetition = new Competition(DEFAULT_COMPETITION_NAME);
        defaultCompetition.getGames().addAll(
                Arrays.asList(game1, game2, game3, game4, game5, game6));
        entityManager.persist(defaultCompetition);
    }
}
