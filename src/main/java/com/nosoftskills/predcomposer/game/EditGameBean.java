package com.nosoftskills.predcomposer.game;

import com.nosoftskills.predcomposer.model.Game;
import com.nosoftskills.predcomposer.session.UserContext;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author Ivan St. Ivanov
 */
@Named("gameEditor")
@ConversationScoped
public class EditGameBean implements Serializable {

    private static final long serialVersionUID = -8375226295834348516L;

    private Game theGame;

    @Inject
    private UserContext userContext;

    @Inject
    private GamesService gamesService;

    @Inject
    private Conversation conversation;

    public String getHomeTeam() {
        return theGame.getHomeTeam();
    }

    public void setHomeTeam(String homeTeam) {
        theGame.setHomeTeam(homeTeam);
    }

    public String getAwayTeam() {
        return theGame.getAwayTeam();
    }

    public void setAwayTeam(String awayTeam) {
        theGame.setAwayTeam(awayTeam);
    }

    private String homeTeamScore;

    public String getHomeTeamScore() {
        return homeTeamScore;
    }

    public void setHomeTeamScore(String homeTeamScore) {
        this.homeTeamScore = homeTeamScore;
    }

    private String awayTeamScore;

    public String getAwayTeamScore() {
        return awayTeamScore;
    }

    public void setAwayTeamScore(String awayTeamScore) {
        this.awayTeamScore = awayTeamScore;
    }

    private int day;

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    private Month month;

    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    private int year;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    private int hours;

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    private int minutes;

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    private static final Map<String, Month> monthValues;

    static {
        monthValues = new LinkedHashMap<>(12);
        monthValues.put("January", Month.JANUARY);
        monthValues.put("February", Month.FEBRUARY);
        monthValues.put("March", Month.MARCH);
        monthValues.put("April", Month.APRIL);
        monthValues.put("May", Month.MAY);
        monthValues.put("June", Month.JUNE);
        monthValues.put("July", Month.JULY);
        monthValues.put("August", Month.AUGUST);
        monthValues.put("September", Month.SEPTEMBER);
        monthValues.put("October", Month.OCTOBER);
        monthValues.put("November", Month.NOVEMBER);
        monthValues.put("December", Month.DECEMBER);
    }

    public Map<String, Month> getMonthValues() {
        return monthValues;
    }

    private static final Map<String, Integer> yearValues;
    static {
        yearValues = new LinkedHashMap<>(4);
        int currentYear = LocalDate.now().getYear();
        yearValues.put(Integer.toString(currentYear - 1), currentYear - 1);
        yearValues.put(Integer.toString(currentYear), currentYear);
        yearValues.put(Integer.toString(currentYear + 1), currentYear + 1);
        yearValues.put(Integer.toString(currentYear + 2), currentYear + 2);
    }

    public Map<String, Integer> getYearValues() {
        return yearValues;
    }

    public String showEditGameForm() {
        return showEditGameForm(null);
    }

    public String showEditGameForm(Game gameToEdit) {
        conversation.begin();
        if (gameToEdit != null) {
            theGame = gameToEdit;
            day = gameToEdit.getGameTime().getDayOfMonth();
            month = gameToEdit.getGameTime().getMonth();
            year = gameToEdit.getGameTime().getYear();
            hours = gameToEdit.getGameTime().getHour();
            minutes = gameToEdit.getGameTime().getMinute();
            if (gameToEdit.getResult() != null) {
                String[] goals = gameToEdit.getResult().split(":");
                homeTeamScore = goals[0];
                awayTeamScore = goals[1];
            }
        } else {
            final LocalDate now = LocalDate.now();
            theGame = new Game();
            day = now.getDayOfMonth();
            month = now.getMonth();
            year = now.getYear();
            hours = 21;
            minutes = 45;
        }
        return "/admin/editGame";
    }

    @Inject
    private Event<Game> gameEvent;

    public String saveNewGame() {
        LocalDateTime gameTime = LocalDateTime.of(year, month, day, hours, minutes);

        theGame.setGameTime(gameTime);
        if (!empty(homeTeamScore, awayTeamScore)) {
            theGame.setResult(homeTeamScore + ":" + awayTeamScore);
        }

        gamesService.storeGame(theGame, userContext.getSelectedCompetition());
        gameEvent.fire(theGame);
        conversation.end();
        return "/futureGames?faces-redirect=true";
    }

    private boolean empty(String... parameters) {
        long nonEmptyParametersCount = Stream
                .of(parameters)
                .filter(parameter -> parameter != null && parameter.length() > 0)
                .count();
        return nonEmptyParametersCount == 0;
    }
}
