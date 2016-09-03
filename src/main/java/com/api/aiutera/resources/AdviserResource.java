package com.api.aiutera.resources;

import com.api.aiutera.bean.MongoDocument;
import com.api.aiutera.dao.impl.MongoDataSource;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.inject.Inject;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

/**
 * Created by Bala on 8/27/16.
 *
 * Class for handling Adviser related actions such as adding,
 * modifying, deleting and retrieving the user information
 * from & to the Data source
 *
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
    public Response registerAdviser(@QueryParam("v") String version, MultivaluedMap<String, String> form) {
        // Creating the Mongo Document
        MongoDocument document = new MongoDocument();

        // Setting mongodb details
        document.setDbname("aiutera");
        document.setCollection("adviser");

        // setting the document to be loaded
        document.setDocument(form.get("payload").get(0));

        JsonParser jsonParser = new JsonParser();
        JsonElement element = jsonParser.parse(form.get("payload").get(0));

        // loading this data to mongodb
        boolean flag = mds.create(document,
                element.getAsJsonObject().get("email").getAsString());

        String result = "Adviser payload : " + form.get("payload").get(0)+ " - Status: " + flag;
        return Response.status(200).entity(result).build();
    }
}
