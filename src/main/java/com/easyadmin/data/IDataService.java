package com.easyadmin.data;

import java.util.List;
import java.util.Map;

/**
 * dataservice for /api/*, include c,r,u,d
 * <p>
 * <p>
 *
 * @author gongxinyi
 * @date 2017-08-31
 */
public interface IDataService {
    /**
     * query for an entity's data
     *
     * @param entity       entity name, e1、e2、e3...
     * @param filterParams all the filter params
     * @param requestScope page and sort
     * @return
     */
    List<Map<String, Object>> list(String entity, Map<String, Object> filterParams, RequestScope requestScope);

    /**
     * @param entity
     * @param filterParams
     * @return
     */
    long count(String entity, Map<String, Object> filterParams);

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
     * delete
     *
     * @param entity
     * @param id
     * @return
     */
    String delete(String entity, String id);
}
