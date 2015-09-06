package com.nosoftskills.predcomposer.rest;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.core.Response;
import java.io.StringReader;

/**
 * @author Ivan St. Ivanov
 */
public class RestUtilities {

    public static long getIdFromLocationHeader(Response response) {
        String location = response.getHeaderString("location");
        return Long.parseLong(location.substring(location.lastIndexOf("/") + 1));
    }

    public static JsonObject readJsonContent(Response response) {
        String competitionJson = response.readEntity(String.class);
        StringReader stringReader = new StringReader(competitionJson);
        JsonReader jsonReader = Json.createReader(stringReader);
        return jsonReader.readObject();
    }


}
