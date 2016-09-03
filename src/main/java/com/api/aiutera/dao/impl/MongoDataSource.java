package com.api.aiutera.dao.impl;

import com.api.aiutera.bean.MongoDocument;
import com.api.aiutera.dao.DataSource;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;
import org.bson.Document;

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

    public boolean create(MongoDocument document, String id) {
        try {

            // getting the mongodb connection
            MongoDatabase db = mongoProvider.get().getDatabase(document.getDbname());

            // getting the collection name
            MongoCollection<Document> coll = db.getCollection(document.getCollection());

            // converting the json into DBObject
            DBObject dbObject = (DBObject) JSON.parse(document.getDocument());

            // adding the user id as document id
            dbObject.put("_id", id);
            coll.insertOne(new Document(dbObject.toMap()));
        } catch (MongoWriteException mwe) {
            return false;
        }
        return true;
    }

    public boolean update() {
        return false;
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
    public FindIterable<Document> search(MongoDocument document) {

        // getting the mongodb connection
        MongoDatabase db = mongoProvider.get().getDatabase(document.getDbname());

        // getting the collection name
        MongoCollection<Document> coll = db.getCollection(document.getCollection());

        // getting all the document from the collection based on the where condition
        //BasicDBObject whereQuery = new BasicDBObject();
        return coll.find();
    }
}
