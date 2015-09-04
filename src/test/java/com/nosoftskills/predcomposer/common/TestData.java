package com.nosoftskills.predcomposer.common;

import com.nosoftskills.predcomposer.model.Competition;
import com.nosoftskills.predcomposer.model.Game;
import com.nosoftskills.predcomposer.model.Prediction;
import com.nosoftskills.predcomposer.model.User;

import javax.persistence.Entity;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.stream.Stream;

import static com.nosoftskills.predcomposer.user.PasswordHashUtil.hashPassword;

/**
 * @author Ivan St. Ivanov
 */
public final class TestData {

    public static User user1;
    public static User user2;
    public static Game game1;
    public static Game game2;
    public static Game game3;
    public static Game game4;
    public static Game game5;
    public static Game game6;
    public static Competition competition;
    public static Prediction prediction1;
    public static Prediction prediction2;
    public static Prediction prediction3;

    static {
        initialize();
    }

    public static void initialize() {
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
        user2.getPredictions().add(prediction2);

        game2.getPredictions().addAll(Arrays.asList(prediction1, prediction2));
        game3.getPredictions().add(prediction3);

        competition = new Competition("Champions League 2015-2016");
        competition.getGames().addAll(Arrays.asList(game1, game2, game3, game4, game5, game6));
    }

    private static long currentId;

    public static void generateEntityIds() {
        currentId = 1;
        Stream.of(TestData.class.getFields()).forEach(TestData::setId);
    }

    private static void setId(Field field) {
        try {
            Object entityInstance = field.get(null);
            Field idField = entityInstance.getClass().getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(entityInstance, currentId++);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
