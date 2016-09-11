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
    void create(MongoDocument document, String id);

    /**
     * Method: create
     * @return
     */
    // TODO: Object should be generic
    void create(MongoDocument document);

    /**
     *
     * @return
     */
    void update(MongoDocument document, String id);

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
