package com.nosoftskills.predcomposer.rest.resources;

import com.nosoftskills.predcomposer.game.GamesService;
import com.nosoftskills.predcomposer.model.Game;
import com.nosoftskills.predcomposer.model.Prediction;
import com.nosoftskills.predcomposer.model.User;
import com.nosoftskills.predcomposer.prediction.GameLockedException;
import com.nosoftskills.predcomposer.prediction.PredictionsService;
import com.nosoftskills.predcomposer.session.UserContext;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.Set;

/**
 * @author Ivan St. Ivanov
 */
@RequestScoped
@Path(PredictionResource.PREDICTION_RESOURCE_ROOT)
public class PredictionResource {

    public static final String PREDICTION_RESOURCE_ROOT = "/prediction";

    @Inject
    private PredictionsService predictionsService;

    @Inject
    private GamesService gamesService;

    @Inject
    private UserContext userContext;

    @GET
    @Produces("application/json")
    public Response getPredictions(@QueryParam("gameId") String gameId) {
        if (gameId == null) {
            User loggedUser = userContext.getLoggedUser();
            Set<Prediction> predictionsForUser = predictionsService
                    .getPredictionsForUser(loggedUser);
            return Response.ok(predictionsForUser).build();
        } else {
            Game game = gamesService.findGameById(Long.parseLong(gameId));
            Set<Prediction> predictionsForGame = predictionsService.getPredictionsForGame(game);
            return Response.ok(predictionsForGame).build();
        }
    }

    @GET
    @Path("{id}")
    @Produces("application/json")
    public Response findPredictionById(@PathParam("id") String id) {
        Prediction prediction = predictionsService.findPredictionById(Long.parseLong(id));
        return Response.ok(prediction).build();
    }

    @POST
    @Produces("application/json")
    public Response createOrUpdatePrediction(@FormParam("predictionId") String predictionId,
            @FormParam("gameId") String gameIdString,
            @FormParam("homeTeamScore") String homeTeamScore,
            @FormParam("awayTeamScore") String awayTeamScore) {
        Game game = gamesService.findGameById(Long.parseLong(gameIdString));
        User user = userContext.getLoggedUser();
        Prediction prediction = new Prediction(user, game, homeTeamScore + ":" + awayTeamScore);
        if (predictionId != null) {
            prediction.setId(Long.parseLong(predictionId));
        }
        try {
            Prediction storedPrediction = predictionsService.store(prediction);
            return Response.created(URI.create(PREDICTION_RESOURCE_ROOT + "/" + prediction.getId()))
                    .type(MediaType.APPLICATION_JSON)
                    .entity(storedPrediction)
                    .build();
        } catch (GameLockedException gle) {
            return Response.status(Response.Status.BAD_REQUEST).entity(gle.getMessage()).build();
        }
    }
}
