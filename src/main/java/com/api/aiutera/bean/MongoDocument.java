package com.api.aiutera.bean;

/**
 * Created by Bala on 9/2/16.
 */
public class MongoDocument {

    // Database name
    private String dbname;

    // Table Name
    private String collection;

    // Json Document
    private String document;

    /**
     *
     * @return
     */
    public String getDbname() {
        return dbname;
    }

    /**
     *
     * @param dbname
     */
    public void setDbname(String dbname) {
        this.dbname = dbname;
    }

    /**
     *
     * @return
     */
    public String getCollection() {
        return collection;
    }

    /**
     *
     * @param collection
     */
    public void setCollection(String collection) {
        this.collection = collection;
    }

    /**
     *
     * @return
     */
    public String getDocument() {
        return document;
    }

    /**
     *
     * @param document
     */
    public void setDocument(String document) {
        this.document = document;
    }

}
