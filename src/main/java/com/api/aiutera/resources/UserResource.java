package com.api.aiutera.resources;

import com.api.aiutera.bean.MongoDocument;
import com.api.aiutera.dao.impl.MongoDataSource;
import com.google.inject.Inject;

import javax.ws.rs.*;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

/**
 * Created by Bala on 8/25/16.
 *
 * Class for handling Users related actions such as adding,
 * modifying, deleting and retrieving the user information
 * from & to the Data source
 *
 */
@Path("/user")
public class UserResource {

    private MongoDataSource mds;

    @Inject
    private UserResource(final MongoDataSource mds) {
        this.mds = mds;
    }

    @POST
    @Path("/register")
    @Consumes("application/x-www-form-urlencoded")
    public Response registerUsers(@QueryParam("v") String version, MultivaluedMap<String, String> form) {
        // Creating the Mongo Document
        MongoDocument document = new MongoDocument();

        // Setting mongodb details
        document.setDbname("aiutera");
        document.setCollection("users");

        // setting the document to be loaded
        document.setDocument(form.get("payload").get(0));

        // loading this data to mongodb
        boolean flag = mds.create(document, form.get("user_name").get(0));

        String result = "Users payload : " + form.get("payload").get(0)+ " - Status: " + flag;
        return Response.status(200).entity(result).build();
    }
}