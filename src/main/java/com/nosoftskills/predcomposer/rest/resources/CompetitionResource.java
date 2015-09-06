package com.nosoftskills.predcomposer.rest.resources;

import com.nosoftskills.predcomposer.competition.CompetitionsService;
import com.nosoftskills.predcomposer.model.Competition;

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

/**
 * @author Ivan St. Ivanov
 */
@Path(CompetitionResource.COMPETITION_RESOURCE_ROOT)
public class CompetitionResource {

    public static final String COMPETITION_RESOURCE_ROOT = "/competition";

    @Inject
    private CompetitionsService competitionsService;

    @GET
    @Path("{id}")
    @Produces("application/json")
    public Response findCompetitionById(@PathParam("id") String id) {
        Competition competition = competitionsService.findCompetitionById(Long.parseLong(id));
        return Response.ok(competition).build();
    }

    @POST
    @Produces("application/json")
    public Response createCompetition(@FormParam("name") String name,
            @FormParam("description") String description) {
        Competition competition = new Competition(name, description);
        Competition storedCompetition = competitionsService.storeCompetition(competition);
        return Response.created(URI.create(COMPETITION_RESOURCE_ROOT + "/" + competition.getId()))
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(storedCompetition)
                .build();
    }
}
