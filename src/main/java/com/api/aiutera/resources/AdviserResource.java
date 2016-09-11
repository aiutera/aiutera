package com.api.aiutera.resources;

import com.api.aiutera.bean.MongoDocument;
import com.api.aiutera.bean.Status;
import com.api.aiutera.dao.impl.MongoDataSource;
import com.api.aiutera.utils.CommonFunctions;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.inject.Inject;
import com.mongodb.MongoException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.IOException;

/**
 * Created by Bala on 8/27/16.
 * <p>
 * Class for handling Adviser related actions such as adding,
 * modifying, deleting and retrieving the user information
 * from & to the Data source
 */
@Path("/adviser")
public class AdviserResource {

    private MongoDataSource mds;

    @Inject
    private AdviserResource(final MongoDataSource mds) {
        this.mds = mds;
    }

    @POST
    @Path("/register")
    @Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerAdviser(@QueryParam("v") String version, MultivaluedMap<String, String> form) throws IOException, JSONException {
        // Getting the Staring time
        long currTime = System.nanoTime();

        // Setting the response object
        JSONObject response = new JSONObject();
        ObjectMapper mapper = new ObjectMapper();
        //mapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);

        // Object for holding the status
        Status status = new Status();

        // Creating the Mongo Document
        MongoDocument document = new MongoDocument();

        // Setting mongodb details
        document.setDbname("aiutera");
        document.setCollection("adviser");

        JsonParser jsonParser = new JsonParser();
        JsonElement element = jsonParser.parse(form.get("payload").get(0));

        // Adding the current timestamp to the document
        element.getAsJsonObject().addProperty("created_timestap", CommonFunctions.getCurrentDate());

        // setting the document to be loaded
        document.setDocument(element.getAsJsonObject().toString());

        // loading this data to mongodb
        try {
            mds.create(document,
                    element.getAsJsonObject().get("email_id").getAsString());
        } catch (MongoException me) {
            status.setStatus("failed");
            status.setCode(me.getCode() + "");
            status.setMessage(me.getMessage());
        }

        status.setTotalTime(CommonFunctions.getTimeDiff(currTime));
        response.put("status", new JSONObject(mapper.writeValueAsString(status)));

        return Response.status(200).entity(response.toString()).build();
    }

    @POST
    @Path("/update")
    @Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateAdviser(@QueryParam("v") String version, MultivaluedMap<String, String> form)
            throws IOException, JSONException {
        // Getting the Staring time
        long currTime = System.nanoTime();

        // Setting the response object
        JSONObject response = new JSONObject();
        ObjectMapper mapper = new ObjectMapper();

        // Object for holding the status
        Status status = new Status();

        // Creating the Mongo Document
        MongoDocument document = new MongoDocument();

        // Setting mongodb details
        document.setDbname("aiutera");
        document.setCollection("adviser");

        JsonParser jsonParser = new JsonParser();
        JsonElement element = jsonParser.parse(form.get("payload").get(0));

        // Adding the current timestamp to the document
        element.getAsJsonObject().addProperty("updated_timestamp", CommonFunctions.getCurrentDate());

        // setting the document to be loaded
        document.setDocument(element.getAsJsonObject().toString());

        // loading this data to mongodb
        try {
            mds.update(document,
                    element.getAsJsonObject().get("email_id").getAsString());
        } catch (MongoException me) {
            status.setStatus("failed");
            status.setCode(me.getCode() + "");
            status.setMessage(me.getMessage());
        }

        status.setTotalTime(CommonFunctions.getTimeDiff(currTime));
        response.put("status", new JSONObject(mapper.writeValueAsString(status)));

        return Response.status(200).entity(response.toString()).build();
    }

    @POST
    @Path("/setAvailability")
    @Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_JSON)
    public Response setAvailability(@QueryParam("v") String version, MultivaluedMap<String, String> form) throws IOException, JSONException {
        // Getting the Staring time
        long currTime = System.nanoTime();

        // Setting the response object
        JSONObject response = new JSONObject();
        ObjectMapper mapper = new ObjectMapper();
        //mapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);

        // Object for holding the status
        Status status = new Status();

        // Creating the Mongo Document
        MongoDocument document = new MongoDocument();

        // Setting mongodb details
        document.setDbname("aiutera");
        document.setCollection("adviser_status");

        // Parsing the json to add additional information
        JsonParser jsonParser = new JsonParser();
        JsonElement element = jsonParser.parse(form.get("payload").get(0));

        // adding the timestamp
        element.getAsJsonObject().addProperty("created_timestamp", CommonFunctions.getCurrentDate());

        // setting the document to be loaded
        document.setDocument(element.getAsJsonObject().toString());

        // loading this data to mongodb
        try {
            mds.create(document);
        } catch (MongoException me) {
            status.setStatus("failed");
            status.setCode(me.getCode() + "");
            status.setMessage(me.getMessage());
        }

        status.setTotalTime(CommonFunctions.getTimeDiff(currTime));
        response.put("status", new JSONObject(mapper.writeValueAsString(status)));

        return Response.status(200).entity(response.toString()).build();
    }

    @POST
    @Path("/isAvailable")
    @Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAvailability(@QueryParam("v") String version, MultivaluedMap<String, String> form) {

        return Response.status(200).entity("").build();
    }

    @POST
    @Path("/search")
    @Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAdviser(@QueryParam("v") String version, MultivaluedMap<String, String> form) {

        String json = "{\"status\":{\"status\":\"success\",\"code\":\"200\",\"message\":\"Advisers list\",\"totalTime\":12345},\"advisers\":[{\"name\":\"John Smith\",\"company_name\":\"ABC\",\"city\":\"Phoenix\",\"phone\":\"xxx-xxx-xxxx\",\"qualification\":\"degree\",\"rating\":4,\"languages\":[\"english\",\"spanish\"],\"price\":\"12.0\",\"web_page\":\"\",\"facebook\":\"\",\"twiter\":\"\",\"email_id\":\"\"},{\"name\":\"John Smith\",\"company_name\":\"ABC\",\"city\":\"Phoenix\",\"phone\":\"xxx-xxx-xxxx\",\"qualification\":\"degree\",\"rating\":4,\"languages\":[\"english\",\"spanish\"],\"price\":\"12.0\",\"web_page\":\"\",\"facebook\":\"\",\"twiter\":\"\",\"email_id\":\"\"},{\"name\":\"John Smith\",\"company_name\":\"ABC\",\"city\":\"Phoenix\",\"phone\":\"xxx-xxx-xxxx\",\"qualification\":\"degree\",\"rating\":4,\"languages\":[\"english\",\"spanish\"],\"price\":\"12.0\",\"web_page\":\"\",\"facebook\":\"\",\"twiter\":\"\",\"email_id\":\"\"}]}";
        return Response.status(200).entity(json).build();
    }
}
