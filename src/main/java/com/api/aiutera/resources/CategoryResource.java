package com.api.aiutera.resources;

import com.api.aiutera.bean.MongoDocument;
import com.api.aiutera.dao.impl.MongoDataSource;
import com.api.aiutera.dao.provider.MongoProvider;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Inject;
import com.mongodb.client.FindIterable;
import org.bson.Document;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

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

    /**
     * Method to return all the category from the data source
     * @return
     */
    @GET
    @Path("/")
    public Response get(@QueryParam("v") String version) throws Exception {

        String data = "";

        // Creating the Mongo Document
        MongoDocument document = new MongoDocument();

        // Setting mongodb details
        document.setDbname("aiutera");
        document.setCollection("category");

        // Since category will have only records
        // getting the category from collation
        FindIterable<Document> records = mds.search(document);
        for (Document doc : records ) {
            data = doc.toJson();
            System.out.println(data);
        }

        return Response.status(200).entity(data).build();
    }

    public static void main(String[] args) throws Exception {
        MongoDataSource md = new MongoDataSource(new MongoProvider());

        CategoryResource cr = new CategoryResource(md);
        cr.get("1.0");
    }
}
