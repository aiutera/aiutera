package com.api.aiutera.dao.impl;

import com.api.aiutera.bean.MongoDocument;
import com.api.aiutera.constants.Aiutera;
import com.api.aiutera.dao.DataSource;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;
import org.bson.Document;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.util.Iterator;
import java.util.regex.Pattern;

/**
 * Created by Bala on 8/25/16.
 * <p>
 * Class to deal with all mongodb related operations
 */
public class MongoDataSource implements DataSource {

    private Provider<MongoClient> mongoProvider;

    @Inject
    public MongoDataSource(final Provider<MongoClient> mp) {
        this.mongoProvider = mp;
    }

    /**
     * @param document
     * @param id
     * @return
     */
    public void create(MongoDocument document, String id) throws MongoException{

        // getting the mongodb connection
        //MongoDatabase db = mongoProvider.get().getDatabase(document.getDbname());

        MongoClient mc = new MongoClient(new ServerAddress(Aiutera.MONGODB_SERVER), new MongoClientOptions.Builder().build());
        System.out.println("Connection getting created.");

        // getting the collection name
        //MongoCollection<Document> coll = db.getCollection(document.getCollection());
        MongoDatabase db = mc.getDatabase(document.getDbname());

        // getting the collection name
        MongoCollection<Document> coll = db.getCollection(document.getCollection());

        // converting the json into DBObject
        DBObject dbObject = (DBObject) JSON.parse(document.getDocument());

        // adding the user id as document id
        dbObject.put("_id", id);
        coll.insertOne(new Document(dbObject.toMap()));
    }

    /**
     * @param document
     * @return
     */
    public void create(MongoDocument document) throws MongoException {

        // getting the mongodb connection
        //MongoDatabase db = mongoProvider.get().getDatabase(document.getDbname());

        MongoClient mc = new MongoClient(new ServerAddress(Aiutera.MONGODB_SERVER), new MongoClientOptions.Builder().build());
        System.out.println("Connection getting created.");

        // getting the mongodb connection
        //MongoDatabase db = mongoProvider.get().getDatabase(document.getDbname());
        MongoDatabase db = mc.getDatabase(document.getDbname());

        // getting the collection name
        MongoCollection<Document> coll = db.getCollection(document.getCollection());

        // converting the json into DBObject
        DBObject dbObject = (DBObject) JSON.parse(document.getDocument());

        coll.insertOne(new Document(dbObject.toMap()));
    }

    /**
     * Method for updating a specific document in mongodb
     *
     * @param document
     * @param id
     */
    public void update(MongoDocument document, String id) {

        // getting the mongodb connection
        //MongoDatabase db = mongoProvider.get().getDatabase(document.getDbname());

        MongoClient mc = new MongoClient(new ServerAddress(Aiutera.MONGODB_SERVER), new MongoClientOptions.Builder().build());

        // getting the collection name
        //MongoCollection<Document> coll = db.getCollection(document.getCollection());
        MongoDatabase db = mc.getDatabase(document.getDbname());

        // getting the collection name
        MongoCollection<Document> coll = db.getCollection(document.getCollection());

        // converting the json into DBObject
        DBObject dbObject = (DBObject) JSON.parse(document.getDocument());

        coll.updateOne(new Document("_id",  id), new Document("$set", new Document(dbObject.toMap())));
    }

    public Object read(String id) {
        return null;
    }

    public boolean delete(String id) {
        return false;
    }

    /**
     * Need to change the return type to generics
     *
     * @param document
     * @return
     * @throws Exception
     */
    public FindIterable<Document> search(MongoDocument document) throws MongoException, JSONException {

        MongoClient mc = new MongoClient(new ServerAddress(Aiutera.MONGODB_SERVER), new MongoClientOptions.Builder().build());
        System.out.println("Connection getting created.");


        Document d = null;

        // getting the mongodb connection
        //MongoDatabase db = mongoProvider.get().getDatabase(document.getDbname());
        MongoDatabase db = mc.getDatabase(document.getDbname());

        // getting the collection name
        MongoCollection<Document> coll = db.getCollection(document.getCollection());

        // checking for document
        if(!document.getDocument().equalsIgnoreCase("")) {
            d = new Document();
            JSONObject json = new JSONObject(document.getDocument());
            Iterator<String> keys = json.keys();
            while(keys.hasNext()) {
                String key = keys.next();
                d.append(key, Pattern.compile(".*" +json.get(key)+ ".*" , Pattern.CASE_INSENSITIVE));
            }
        }

        // getting all the document from the collection based on the where condition
        if(null == d) {
            return coll.find();
        } else {
            return coll.find(d);
        }
    }
}
