package com.nosoftskills.predcomposer.test;

import com.nosoftskills.predcomposer.competition.CompetitionsService;
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



    @PersistenceContext
    private EntityManager entityManager;

    @PostConstruct
    public void insertTestData() {

        TypedQuery<Competition> query = entityManager.createNamedQuery("findCompetitionByName", Competition.class);
        query.setParameter("competitionName", CompetitionsService.DEFAULT_COMPETITION_NAME);

        try {
            query.getSingleResult();
            System.out.println("Data was already inserted");
            return;
        } catch (NoResultException nre) {
            System.out.printf("Application starting for the first time. Inserting data");
        }

        User user1 = new User("ivan", hashPassword("ivan"), "ivan@nosoftskills.com", "Ivan", "Ivanov", true);
        User user2 = new User("koko", hashPassword("koko"), "koko@nosoftskills.com", "Koko", "Stefanov", false);
        User user3 = new User("bobo", hashPassword("bobo"), "bobo@nosoftskills.com", "Boyan", "Stefanov", false);

        Game game1 = new Game("Paris Saint-Germain", "Ludogorets", "2:2", LocalDateTime.of(2016, 12, 6, 21, 45), true);
        Game game2 = new Game("PSV", "Rostov", "0:0", LocalDateTime.of(2016, 12, 6, 21, 45), true);
        Game game3 = new Game("Bayern", "Atletico Madrid", "1:0", LocalDateTime.of(2016, 12, 6, 21, 45), true);
        Game game4 = new Game("Manchester City", "Celtic", "1:1", LocalDateTime.of(2016, 12, 6, 21, 45), true);
        Game game5 = new Game("Barcelona", "Monchengladbach", "4:0", LocalDateTime.of(2016, 12, 6, 21, 45), true);
        Game game6 = new Game("Benfica", "Napoli", "1:2", LocalDateTime.of(2016, 12, 6, 21, 45), true);
        Game game7 = new Game("Dynamo Kiyv", "Besiktas", "6:0", LocalDateTime.of(2016, 12, 6, 21, 45), true);
        Game game8 = new Game("Basel", "Arsenal", "1:4", LocalDateTime.of(2016, 12, 6, 21, 45), true);


        Prediction prediction1 = new Prediction(user1, game2, "0:0");
        Prediction prediction2 = new Prediction(user2, game2, "0:2");
        Prediction prediction3 = new Prediction(user1, game3, "1:1");
        Prediction prediction4 = new Prediction(user2, game3, "2:0");
        Prediction prediction5 = new Prediction(user3, game3, "2:1");
        Prediction prediction6 = new Prediction(user1, game5, "1:2");
        Prediction prediction7 = new Prediction(user2, game5, "4:2");
        Prediction prediction8 = new Prediction(user3, game5, "1:1");
        Prediction prediction9 = new Prediction(user2, game7, "2:1");
        Prediction prediction10 = new Prediction(user3, game7, "1:0");

        user1.getPredictions().addAll(Arrays.asList(prediction1, prediction3, prediction6));
        user2.getPredictions().addAll(Arrays.asList(prediction2, prediction4, prediction7, prediction9));
        user3.getPredictions().addAll(Arrays.asList(prediction5, prediction8, prediction10));

        entityManager.persist(user1);
        entityManager.persist(user2);
        entityManager.persist(user3);

        game2.getPredictions().addAll(Arrays.asList(prediction1, prediction2));
        game3.getPredictions().addAll(Arrays.asList(prediction3, prediction4, prediction5));
        game5.getPredictions().addAll(Arrays.asList(prediction6, prediction7, prediction8));
        game7.getPredictions().addAll(Arrays.asList(prediction9, prediction10));

        Competition defaultCompetition = new Competition(CompetitionsService.DEFAULT_COMPETITION_NAME);
        defaultCompetition.getGames().addAll(
                Arrays.asList(game1, game2, game3, game4, game5, game6, game7, game8));
        entityManager.persist(defaultCompetition);
    }
}
