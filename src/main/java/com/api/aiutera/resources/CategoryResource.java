package com.api.aiutera.resources;

import com.api.aiutera.bean.MongoDocument;
import com.api.aiutera.dao.impl.MongoDataSource;
import com.google.inject.Inject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * Created by Bala on 9/2/16.
 */
@Path("/category")
public class CategoryResource {

    private MongoDataSource mds;

    @Inject
    private CategoryResource(final MongoDataSource mds) {
        this.mds = mds;
    }

    @GET
    public Response get() {

        List<>

        // Creating the Mongo Document
        MongoDocument document = new MongoDocument();

        // Setting mongodb details
        document.setDbname("aiutera");
        document.setCollection("category");

        // getting the category from collation
         = mds.create(document, form.get("user_name").get(0));



        return Response.status(200).entity(result).build();
    }
}
