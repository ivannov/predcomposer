package com.nosoftskills.predcomposer.rankings;

import com.nosoftskills.predcomposer.model.Competition;
import com.nosoftskills.predcomposer.model.User;

/**
 * @author Ivan St. Ivanov
 */
public class Score implements Comparable<Score> {

    private User user;
    private Integer points;

    public Score(User user, int points) {
        this.user = user;
        this.points = points;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    @Override
    public int compareTo(Score score) {
        return score.getPoints() - this.points;
    }

    @Override public String toString() {
        return "Score{" +
                "user=" + user.getUserName() +
                ", points=" + points +
                '}';
    }
}
