package com.api.aiutera.resources;

import com.api.aiutera.bean.MongoDocument;
import com.api.aiutera.bean.Status;
import com.api.aiutera.dao.impl.MongoDataSource;
import com.api.aiutera.utils.CommonFunctions;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.inject.Inject;
import com.mongodb.MongoException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.IOException;

/**
 * Created by Bala on 9/11/16.
 */
@Path("/rating")
public class RatingResource {

    private MongoDataSource mds;

    @Inject
    private RatingResource(final MongoDataSource mds) {
        this.mds = mds;
    }

    @POST
    @Path("/")
    @ApiOperation(value = "Adviser Rating",
            response = Status.class)
    @Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiResponses({@ApiResponse(code = 400, message = "Invalid Adviser Details")})
    public Response registerUsers(@QueryParam("v") String version, MultivaluedMap<String, String> form)
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
        document.setCollection("ratings");

        JsonParser jsonParser = new JsonParser();
        JsonElement element = jsonParser.parse(form.get("payload").get(0));

        // Adding the current timestamp to the document
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
}
