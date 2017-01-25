package com.nosoftskills.predcomposer.common;

import com.nosoftskills.predcomposer.model.Competition;
import com.nosoftskills.predcomposer.model.Game;
import com.nosoftskills.predcomposer.model.Prediction;
import com.nosoftskills.predcomposer.model.User;

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
        currentId = 1;
        Stream.of(TestData.class.getFields()).forEach(TestData::setId);
    }

    static void initialize() {
        user1 = new User("ivan", hashPassword("ivan"), "ivan@nosoftskills.com", "Ivan", "Ivanov", true);
        user2 = new User("koko", hashPassword("koko"), "koko@nosoftskills.com", "Koko", "Stefanov", false);

        game1 = new Game("Porto", "Roma", "1:1", LocalDateTime
                .of(2016, 8, 17, 21, 45), true);
        game2 = new Game("Roma", "Porto", "0:3", LocalDateTime.of(2016, 8,
                23, 21, 45), true);
        game3 = new Game("Paris Saint-Germain", "Arsenal", LocalDateTime.of(2016, 9, 13, 21, 45));
        game4 = new Game("Barcelona", "Celtic", LocalDateTime.of(2016, 9, 13, 21, 45));
        game5 = new Game("Real Madrid", "Sporting", LocalDateTime.of(2016, 9, 14, 21, 45));
        game6 = new Game("Juventus", "Sevilla", LocalDateTime.of(2015, 9, 14, 21, 45));

        prediction1 = new Prediction(user1, game2, "0:0");
        prediction2 = new Prediction(user2, game2, "0:2");
        prediction3 = new Prediction(user1, game3, "2:0");

        user1.getPredictions().addAll(Arrays.asList(prediction1, prediction3));
        user2.getPredictions().add(prediction2);

        game2.getPredictions().addAll(Arrays.asList(prediction1, prediction2));
        game3.getPredictions().add(prediction3);

        competition = new Competition("Champions League 2016-2017");
        competition.getGames().addAll(Arrays.asList(game1, game2, game3, game4, game5, game6));
    }

    private static long currentId;

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
