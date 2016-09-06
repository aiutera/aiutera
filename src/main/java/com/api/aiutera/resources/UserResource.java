package com.api.aiutera.resources;

import com.api.aiutera.bean.MongoDocument;
import com.api.aiutera.bean.Status;
import com.api.aiutera.dao.impl.MongoDataSource;
import com.api.aiutera.utils.CommonFunctions;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.inject.Inject;
import com.mongodb.MongoException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
 * Created by Bala on 8/25/16.
 *
 * Class for handling Users related actions such as adding,
 * modifying, deleting and retrieving the user information
 * from & to the Data source
 *
 */
@Api(value = "/user", description = "the User api")
@Path("/user")
public class UserResource {

    private MongoDataSource mds;

    @Inject
    private UserResource(final MongoDataSource mds) {
        this.mds = mds;
    }

    @POST
    @Path("/register")
    @ApiOperation(value = "User Registration",
            response = Status.class)
    @Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiResponses({ @ApiResponse(code = 400, message = "Invalid User Details") })
    public Response registerUsers(@QueryParam("v") String version, MultivaluedMap<String, String> form)
            throws IOException, JSONException {
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
        document.setCollection("users");

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
}