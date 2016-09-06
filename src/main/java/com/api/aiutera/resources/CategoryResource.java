package com.api.aiutera.resources;

import com.api.aiutera.bean.MongoDocument;
import com.api.aiutera.bean.Status;
import com.api.aiutera.dao.impl.MongoDataSource;
import com.api.aiutera.dao.provider.MongoProvider;
import com.api.aiutera.utils.CommonFunctions;
import com.google.inject.Inject;
import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import org.bson.Document;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jettison.json.JSONObject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
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

    /**
     * Method to return all the category from the data source
     * @return
     */
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@QueryParam("v") String version) throws Exception {

        // Getting the Staring time
        long currTime = System.nanoTime();

        // Setting the response object
        JSONObject response = new JSONObject();
        ObjectMapper mapper = new ObjectMapper();
        //mapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);

        // Object for holding the status
        Status status = new Status();

        String data = "";

        // Creating the Mongo Document
        MongoDocument document = new MongoDocument();

        // Setting mongodb details
        document.setDbname("aiutera");
        document.setCollection("category");

        // Since category will have only records
        // getting the category from collation
        try {
            FindIterable<Document> records = mds.search(document);
            for (Document doc : records) {
                // removing the _id tag
                doc.remove("_id");
                // converting the object to json
                data = doc.toJson();
            }
            response.put("data", new JSONObject(data));
        } catch (MongoException me) {
            status.setStatus("failed");
            status.setCode(me.getCode() + "");
            status.setMessage(me.getMessage());
        }

        status.setTotalTime(CommonFunctions.getTimeDiff(currTime));
        response.put("status", new JSONObject(mapper.writeValueAsString(status)));

        return Response.status(200).entity(response.toString()).build();
    }

    public static void main(String[] args) throws Exception {
        MongoDataSource md = new MongoDataSource(new MongoProvider());

        CategoryResource cr = new CategoryResource(md);
        cr.get("1.0");
    }
}
