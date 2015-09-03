package com.nosoftskills.predcomposer.common;

import com.nosoftskills.predcomposer.model.Competition;
import com.nosoftskills.predcomposer.model.Game;
import com.nosoftskills.predcomposer.model.Prediction;
import com.nosoftskills.predcomposer.model.User;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.Arrays;

import static com.nosoftskills.predcomposer.user.PasswordHashUtil.hashPassword;

/**
 * @author Ivan St. Ivanov
 */
public abstract class BaseServiceTest {

    protected static EntityManager entityManager;

    protected User user1;
    protected User user2;
    protected Game game1;
    protected Game game2;
    protected Game game3;
    protected Game game4;
    protected Game game5;
    protected Game game6;
    protected Competition competition;
    protected Prediction prediction1;
    protected Prediction prediction2;
    protected Prediction prediction3;

    @BeforeClass
    public static void setupTestObjects() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("predcomposer-test");
        entityManager = emf.createEntityManager();
    }

    @Before
    public void setUp() throws Exception {
        setupClassUnderTest();
        entityManager.getTransaction().begin();
        insertTestData();
        entityManager.flush();
    }

    private void insertTestData() {
        user1 = new User("ivan", hashPassword("ivan"), "ivan@example.com", "Ivan", "Ivanov", true);
        user2 = new User("koko", hashPassword("koko"), "koko@example.com", "Koko", "Stefanov", false);

        game1 = new Game("Manchester United", "Club Brugge", "3:1", LocalDateTime
                .of(2015, 8, 18, 21, 45), true);
        game2 = new Game("Club Brugge", "Manchester United", "0:4", LocalDateTime.of(2015, 8,
                26, 21, 45), true);
        game3 = new Game("Paris SG", "Malmo", LocalDateTime.of(2015, 9, 15, 21, 45));
        game4 = new Game("Real Madrid", "Shakhtar Donetsk", LocalDateTime.of(2015, 9, 15, 21, 45));
        game5 = new Game("Leverkusen", "BATE", LocalDateTime.of(2015, 9, 16, 21, 45));
        game6 = new Game("Roma", "Barcelona", LocalDateTime.of(2015, 9, 16, 21, 45));

        prediction1 = new Prediction(user1, game2, "0:0");
        prediction2 = new Prediction(user2, game2, "0:2");
        prediction3 = new Prediction(user1, game3, "2:0");

        user1.getPredictions().addAll(Arrays.asList(prediction1, prediction3));
        entityManager.persist(user1);
        user2.getPredictions().add(prediction2);
        entityManager.persist(user2);

        game2.getPredictions().addAll(Arrays.asList(prediction1, prediction2));
        game3.getPredictions().add(prediction3);

        competition = new Competition("Champions League 2015-2016");
        competition.getGames().addAll(Arrays.asList(game1, game2, game3, game4, game5, game6));
        entityManager.persist(competition);
    }

    @After
    public void tearDown() throws Exception {
        entityManager.getTransaction().rollback();
    }

    @AfterClass
    public static void closeEntityManager() throws Exception {
        entityManager.close();
    }

    protected abstract void setupClassUnderTest();
}
