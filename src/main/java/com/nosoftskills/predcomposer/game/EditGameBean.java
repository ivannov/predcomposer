package com.nosoftskills.predcomposer.game;

import com.nosoftskills.predcomposer.model.Game;
import com.nosoftskills.predcomposer.session.UserContext;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Ivan St. Ivanov
 */
@Named("gameEditor")
@ConversationScoped
public class EditGameBean implements Serializable {

    private Game theGame;

    @Inject
    private UserContext userContext;

    @Inject
    private GamesService gamesService;

    @Inject
    private Conversation conversation;

    private static final LocalDateTime NOW = LocalDateTime.now();

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
        int currentYear = NOW.getYear();
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
        } else {
            theGame = new Game();
            theGame.setCompetition(userContext.getSelectedCompetition());
            day = NOW.getDayOfMonth();
            month = NOW.getMonth();
            year = NOW.getYear();
            hours = 21;
            minutes = 45;
        }
        return "/admin/editGame";
    }

    public String saveNewGame() {
        LocalDateTime gameTime = LocalDateTime.of(year, month, day, hours, minutes);

        theGame.setGameTime(gameTime);

        gamesService.storeGame(theGame);
        conversation.end();
        return "/futureGames";
    }
}
