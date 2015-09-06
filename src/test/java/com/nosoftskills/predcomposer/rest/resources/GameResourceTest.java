package com.nosoftskills.predcomposer.rest.resources;

import com.nosoftskills.predcomposer.alternatives.UserContextAlternative;
import com.nosoftskills.predcomposer.common.TestData;
import com.nosoftskills.predcomposer.game.GamesService;
import com.nosoftskills.predcomposer.model.Game;
import com.nosoftskills.predcomposer.prediction.GameLockedException;
import com.nosoftskills.predcomposer.rest.RestConfiguration;
import com.nosoftskills.predcomposer.session.UserContext;
import com.nosoftskills.predcomposer.user.PasswordHashUtil;
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
import java.net.MalformedURLException;
import java.net.URL;

import static com.nosoftskills.predcomposer.rest.RestUtilities.getIdFromLocationHeader;
import static com.nosoftskills.predcomposer.rest.RestUtilities.readJsonContent;
import static org.junit.Assert.assertEquals;

/**
 * @author Ivan St. Ivanov
 */
@RunWith(Arquillian.class)
@RunAsClient
public class GameResourceTest {

    public static final String HOME_TEAM = "Manchester City";
    public static final String AWAY_TEAM = "Juventus";
    public static final String MATCH_RESULT = "3:1";

    @Deployment
    public static WebArchive createDeployment() {
        WebArchive webArchive = ShrinkWrap.create(WebArchive.class, "predcomposer-test.war")
                .addClass(GameResource.class)
                .addClasses(GamesService.class, GameLockedException.class)
                .addClass(RestConfiguration.class)
                .addClasses(UserContextAlternative.class, UserContext.class, TestData.class,
                        PasswordHashUtil.class)
                .addPackage(Game.class.getPackage())
                .addAsResource(new File("src/main/resources/META-INF/persistence.xml"),
                        "META-INF/persistence.xml")
                .addAsWebInfResource("test-beans.xml", "beans.xml");
        System.out.println(webArchive.toString(true));
        return webArchive;
    }

    private static Long gameId;

    @Test
    @InSequence(1)
    public void shouldCreatedGame(@ArquillianResource URL base) throws Exception {
        URL url = new URL(base, "rest/game");
        WebTarget target = ClientBuilder.newClient().target(url.toExternalForm());
        Form newGameForm = buildForm(false, false);
        Response response = target.request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(newGameForm, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
        assertEquals(201, response.getStatus());
        gameId = getIdFromLocationHeader(response);
    }

    @Test
    @InSequence(2)
    public void shouldFindCreatedGameById(@ArquillianResource URL base) throws Exception {
        Response response = requestGame(base);
        assertEquals(200, response.getStatus());
        JsonObject jsonObject = readJsonContent(response);
        assertEquals(gameId.intValue(), jsonObject.getInt("id"));
        assertEquals(HOME_TEAM, jsonObject.getString("homeTeam"));
        assertEquals(AWAY_TEAM, jsonObject.getString("awayTeam"));
    }

    @Test
    @InSequence(3)
    public void shouldUpdateCreatedGame(@ArquillianResource URL base) throws Exception {
        URL url = new URL(base, "rest/game");
        WebTarget target = ClientBuilder.newClient().target(url.toExternalForm());
        Form newGameForm = buildForm(true, true);
        Response response = target.request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(newGameForm, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
        assertEquals(201, response.getStatus());
        assertEquals(gameId.intValue(), getIdFromLocationHeader(response));
    }

    @Test
    @InSequence(4)
    public void shouldGetTheUpdatedMatch(@ArquillianResource URL base) throws Exception {
        Response response = requestGame(base);
        assertEquals(200, response.getStatus());
        JsonObject jsonObject = readJsonContent(response);
        System.out.println("jsonObject = " + jsonObject);
        assertEquals(gameId.intValue(), jsonObject.getInt("id"));
        assertEquals(MATCH_RESULT, jsonObject.getString("result"));
    }

    private Form buildForm(boolean withId, boolean withResult) {
        Form gameForm = new Form();
        if (withId) {
            gameForm.param("gameId", gameId.toString());
        }
        gameForm.param("homeTeam", HOME_TEAM);
        gameForm.param("awayTeam", AWAY_TEAM);
        if (withResult) {
            String[] goals = MATCH_RESULT.split(":");
            gameForm.param("homeTeamScore", goals[0]);
            gameForm.param("awayTeamScore", goals[1]);
        }
        gameForm.param("day", "15");
        gameForm.param("month", "9");
        gameForm.param("year", "2015");
        gameForm.param("hours", "21");
        gameForm.param("minutes", "45");
        gameForm.param("locked", "false");
        return gameForm;
    }

    private Response requestGame(URL base) throws MalformedURLException {
        URL url = new URL(base, "rest/game/" + gameId);
        WebTarget target = ClientBuilder.newClient().target(url.toExternalForm());
        return target.request(MediaType.APPLICATION_JSON_TYPE).get();
    }
}
