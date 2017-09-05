package com.easyadmin.service;

import java.util.List;
import java.util.Map;

/**
 * dataservice for /api/*, include c,r,u,d
 * <p>
 * <p>
 * Created by gongxinyi on 2017-08-31.
 */
public interface DataService {
    /**
     * query for an entity's data
     *
     * @param entity           entity name, e1、e2、e3...
     * @param allRequestParams all the query params, include filter、page、sort
     * @return
     */
    List<Map<String, Object>> list(String entity, Map<String, Object> allRequestParams);

    /**
     * @param entity
     * @param allRequestParams
     * @return
     */
    long count(String entity, Map<String, Object> allRequestParams);

    /**
     * find by pk
     *
     * @param entity
     * @param id
     * @return
     */
    Map<String, Object> findOne(String entity, String id);

    /**
     * save an object to entity
     *
     * @param entity
     * @param data   all the form data
     * @return
     */
    Map<String, Object> save(String entity, Map<String, Object> data);

    /**
     * update a record
     *
     * @param entity
     * @param id
     * @param data   all the form data
     * @return
     */
    Map<String, Object> update(String entity, String id, Map<String, Object> data);

    /**
     * delete logic
     *
     * @param entity
     * @param id
     * @return
     */
    Map<String, Object> deleteLogic(String entity, String id);
}
