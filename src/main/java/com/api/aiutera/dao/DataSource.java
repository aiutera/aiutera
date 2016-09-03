package com.api.aiutera.dao;

import com.api.aiutera.bean.MongoDocument;

/**
 * Created by Bala on 8/25/16.
 *
 * Interface for DataSource
 * Default curd operation support
 */
public interface DataSource {

    /**
     * Method: create
     * @return
     */
    // TODO: Object should be generic
    boolean create(MongoDocument userInfo, String id);

    /**
     *
     * @return
     */
    boolean update();

    /**
     *
     * @return
     */
    Object read(String id);

    /**
     *
     * @return
     */
    boolean delete(String id);

    /**
     *
     * @return
     */
    Object search(MongoDocument userInfo);
}
