package com.api.aiutera.bootstarp;

import com.api.aiutera.dao.DataSource;
import com.api.aiutera.dao.impl.MongoDataSource;
import com.api.aiutera.dao.provider.MongoProvider;
import com.api.aiutera.resources.AdviserResource;
import com.api.aiutera.resources.CategoryResource;
import com.api.aiutera.resources.UserResource;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Singleton;

/**
 * Created by Bala on 8/25/16.
 * <p>
 * Class for loading the configuration and entry point
 * of the application
 */
public class ApplicationModule implements Module {

    /**
     * Configuration method
     * @param binder
     */
    public void configure(final Binder binder) {
        // binding the User Resource
        binder.bind(UserResource.class);

        // binding the Advisor Resource
        binder.bind(AdviserResource.class);

        // binding the Category Resource
        binder.bind(CategoryResource.class);

        // binding mongodb client
        binder.bind(MongoProvider.class).in(Singleton.class);
        binder.bind(DataSource.class).to(MongoDataSource.class).in(Singleton.class);
    }
}
