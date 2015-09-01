package com.nosoftskills.predcomposer.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Version;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@NamedQueries({
        @NamedQuery(name = "getFutureGamesForCompetition",
                query = "SELECT g FROM Game g WHERE g.competition = :competition AND g.gameTime >= :after ORDER BY g.gameTime"),
        @NamedQuery(name = "getCompletedGamesForCompetition",
                query = "SELECT g FROM Game g WHERE g.competition = :competition AND g.result IS NOT NULL")
})
public class Game implements Serializable {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(
            "dd MMM, HH:mm");

    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Version
	private int version;

    @ManyToOne
    private Competition competition;

	@Column(nullable = false)
	private String homeTeam;

	@Column(nullable = false)
	private String awayTeam;

	@Column
	private String result;

	@Column(nullable = false)
	private LocalDateTime gameTime;

	@OneToMany(mappedBy = "forGame", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Prediction> predictions = new HashSet<>();

	private boolean locked = false;

    public Game() {
    }

    public Game(Competition competition, String homeTeam, String awayTeam,
            LocalDateTime gameTime) {
        this(competition, homeTeam, awayTeam, null, gameTime, false);
    }

    public Game(Competition competition, String homeTeam, String awayTeam, String result,
            LocalDateTime gameTime, boolean locked) {
        this.competition = competition;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.result = result;
        this.gameTime = gameTime;
        this.locked = locked;
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

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public LocalDateTime getGameTime() {
        return gameTime;
    }

    public String getGameTimeFormatted() {
        return gameTime.format(DATE_TIME_FORMATTER);
    }

    public void setGameTime(LocalDateTime gameTime) {
        this.gameTime = gameTime;
    }

    public Set<Prediction> getPredictions() {
        return predictions;
    }

    public void setPredictions(
            Set<Prediction> predictions) {
        this.predictions = predictions;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", competition=" + competition +
                ", homeTeam='" + homeTeam + '\'' +
                ", awayTeam='" + awayTeam + '\'' +
                ", result='" + result + '\'' +
                ", gameTime=" + gameTime +
                ", predictions=" + predictions +
                ", locked=" + locked +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Game))
            return false;
        Game game = (Game) o;
        return Objects.equals(competition, game.competition) &&
                Objects.equals(homeTeam, game.homeTeam) &&
                Objects.equals(awayTeam, game.awayTeam) &&
                Objects.equals(gameTime, game.gameTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(competition, homeTeam, awayTeam, gameTime);
    }
}