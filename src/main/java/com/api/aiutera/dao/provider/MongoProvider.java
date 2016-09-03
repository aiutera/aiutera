package com.api.aiutera.dao.provider;

import com.google.inject.Provider;
import com.mongodb.MongoClient;

/**
 * Created by Bala on 8/25/16.
 */
public class MongoProvider implements Provider<MongoClient> {

    private MongoClient mc;

    public MongoClient get() {
        if (mc == null)
            mc = new MongoClient("localhost", 27017);
        return mc;
    }
}
