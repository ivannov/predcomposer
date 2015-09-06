package com.nosoftskills.predcomposer.rest.resources;

import com.nosoftskills.predcomposer.competition.CompetitionsService;
import com.nosoftskills.predcomposer.model.Competition;
import com.nosoftskills.predcomposer.rest.RestConfiguration;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.json.JsonObject;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.net.URL;

import static com.nosoftskills.predcomposer.rest.RestUtilities.getIdFromLocationHeader;
import static com.nosoftskills.predcomposer.rest.RestUtilities.readJsonContent;
import static org.junit.Assert.assertEquals;

/**
 * @author Ivan St. Ivanov
 */
@RunWith(Arquillian.class)
@RunAsClient
public class CompetitionResourceTest {

    private static final String COMPETITION_NAME = "Champions League 2015";
    private static final String COMPETITION_DESCRIPTION = "Ils sont les meilleurs";

    @Deployment
    public static WebArchive createDeployment() {
        WebArchive webArchive = ShrinkWrap.create(WebArchive.class, "predcomposer-test.war")
                .addClass(CompetitionsService.class)
                .addClasses(CompetitionResource.class, RestConfiguration.class)
                .addPackage(Competition.class.getPackage())
                .addAsResource(new File("src/main/resources/META-INF/persistence.xml"),
                        "META-INF/persistence.xml");
        System.out.println(webArchive.toString(true));
        return webArchive;
    }

    private static Long competitionId;

    @Test
    @InSequence(1)
    public void shouldCreateCompetition(@ArquillianResource URL base) throws Exception {
        URL url = new URL(base, "rest/competition");
        WebTarget target = ClientBuilder.newClient().target(url.toExternalForm());
        Form newCompetitionForm = new Form();
        newCompetitionForm.param("name", COMPETITION_NAME);
        newCompetitionForm.param("description", COMPETITION_DESCRIPTION);
        Response response = target.request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(newCompetitionForm, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
        assertEquals(201, response.getStatus());
        competitionId = getIdFromLocationHeader(response);
    }

    @Test
    @InSequence(2)
    public void shouldFindCreatedCompetitionById(@ArquillianResource URL base) throws Exception {
        URL url = new URL(base, "rest/competition/" + competitionId);
        WebTarget target = ClientBuilder.newClient().target(url.toExternalForm());
        Response response = target.request(MediaType.APPLICATION_JSON_TYPE).get();
        assertEquals(200, response.getStatus());
        JsonObject jsonObject = readJsonContent(response);
        assertEquals(competitionId.intValue(), jsonObject.getInt("id"));
        assertEquals(COMPETITION_NAME, jsonObject.getString("name"));
        assertEquals(COMPETITION_DESCRIPTION, jsonObject.getString("description"));
    }

}
