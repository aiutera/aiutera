package com.api.aiutera.resources;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * Created by Bala on 8/22/16.
 *
 * Class for validating the user login
 */
@Path("/")
public class LoginResource {

    @GET
    @Path("/login")
    public Response printMessage(@QueryParam("v") String version) {

        String result = "Version No : " + version;

        return Response.status(200).entity(result).build();

    }
}
