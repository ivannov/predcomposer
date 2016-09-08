package com.nosoftskills.predcomposer.rest.resources;

import com.nosoftskills.predcomposer.game.GamesService;
import com.nosoftskills.predcomposer.model.Competition;
import com.nosoftskills.predcomposer.model.Game;
import com.nosoftskills.predcomposer.session.UserContext;

import javax.inject.Inject;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.time.LocalDateTime;

/**
 * @author Ivan St. Ivanov
 */
@Path(GameResource.GAME_RESOURCE_ROOT)
public class GameResource {

    public static final String GAME_RESOURCE_ROOT = "/game";

    @Inject
    private GamesService gamesService;

    @Inject
    private UserContext userContext;

    @Inject
    private Competition activeCompetition;

    @GET
    @Path("{id}")
    @Produces("application/json")
    public Response findGameById(@PathParam("id") String id) {
        Game game = gamesService.findGameById(Long.parseLong(id));
        return Response.ok(game).build();
    }

    @POST
    @Produces("application/json")
    public Response createOrUpdateGame(@FormParam("gameId") Long gameId,
            @FormParam("homeTeam") String homeTeam,
            @FormParam("awayTeam") String awayTeam,
            @FormParam("homeTeamScore") String homeTeamScore,
            @FormParam("awayTeamScore") String awayTeamScore,
            @FormParam("day") Integer day,
            @FormParam("month") Integer month,
            @FormParam("year") Integer year,
            @FormParam("hours") Integer hours,
            @FormParam("minutes") Integer minutes,
            @FormParam("locked") Boolean locked) {
        if (!userContext.getLoggedUser().getIsAdmin()) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        Game game = new Game(homeTeam, awayTeam, LocalDateTime.of(year, month, day, hours, minutes));
        game.setId(gameId);
        if (homeTeamScore != null && awayTeamScore != null) {
            game.setResult(homeTeamScore + ":" + awayTeamScore);
        }
        game.setLocked(locked);
        Game storedGame = gamesService.storeGame(game, activeCompetition);
        return Response.created(URI.create(GAME_RESOURCE_ROOT + "/" + storedGame.getId()))
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(storedGame)
                .build();
    }

}
