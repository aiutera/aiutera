package com.api.aiutera.dao.provider;

import com.google.inject.Provider;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;

/**
 * Created by Bala on 8/25/16.
 */
public class MongoProvider implements Provider<MongoClient> {

    private MongoClient mc;

    public MongoClient get() {
        if (mc == null) {
            //mc = new MongoClient(new ServerAddress("172.31.1.96"), 27017);
            mc = new MongoClient(new ServerAddress("52.43.79.96"), new MongoClientOptions.Builder().build());
            System.out.println("Connection getting created.");
        }
        return mc;
    }
}
