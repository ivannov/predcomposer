package com.nosoftskills.predcomposer.game;

import com.nosoftskills.predcomposer.competition.CompetitionService;
import com.nosoftskills.predcomposer.model.Competition;
import com.nosoftskills.predcomposer.model.Game;
import com.nosoftskills.predcomposer.session.UserContext;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author Ivan St. Ivanov
 */
@Named("gamesViewer")
@ApplicationScoped
public class ViewGamesBean {

    private Map<Long, Game> gamesMap = new TreeMap<>(Comparator.reverseOrder());

    @Inject
    private UserContext userContext;

    @Inject
    private CompetitionService competitionService;

    @PostConstruct
    public void loadGames() {
        Competition selectedCompetition = userContext.getSelectedCompetition();
        Set<Game> games = competitionService.getGamesForCompetition(selectedCompetition);
        games.forEach(game -> gamesMap.put(game.getId(), game));
    }

    public List<Game> getFutureGames() {
        return getGames(game -> game.getGameTime().isAfter(LocalDate.now().atStartOfDay()));
    }

    public List<Game> getCompletedGames() {
        return getGames(game -> game.getGameTime().isBefore(LocalDate.now().atStartOfDay()));
    }

    private List<Game> getGames(Predicate<Game> gamePredicate) {
        return gamesMap.values()
                .stream()
                .filter(gamePredicate)
                .collect(Collectors.toList());
    }

    public void gameChanged(@Observes Game game) {
        gamesMap.put(game.getId(), game);
    }
}
