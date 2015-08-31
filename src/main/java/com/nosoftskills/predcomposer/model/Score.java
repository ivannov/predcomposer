package com.nosoftskills.predcomposer.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Version;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Score implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Version
	private int version;

	@OneToOne
	private Competition competition;

	@OneToOne
	private User forUser;

    private int points;

    public Score() {
    }

    public Score(Competition competition, User forUser) {
        this(competition, forUser, 0);
    }

    public Score(Competition competition, User forUser, int points) {
        this.competition = competition;
        this.forUser = forUser;
        this.points = points;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Competition getCompetition() {
        return competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

    public User getForUser() {
        return forUser;
    }

    public void setForUser(User forUser) {
        this.forUser = forUser;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return "Score{" +
                "id=" + id +
                ", competition=" + competition +
                ", forUser=" + forUser +
                ", points=" + points +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Score))
            return false;
        Score score = (Score) o;
        return Objects.equals(points, score.points) &&
                Objects.equals(competition, score.competition) &&
                Objects.equals(forUser, score.forUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(competition, forUser, points);
    }
}