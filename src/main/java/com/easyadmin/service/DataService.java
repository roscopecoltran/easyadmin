package com.easyadmin.service;

import java.util.List;
import java.util.Map;

/**
 * Created by gongxinyi on 2017-08-31.
 */
public interface DataService {
    List<Map<String, Object>> list(String entity, Map<String, Object> allRequestParams);

    long count(String entity, Map<String, Object> allRequestParams);

    Map<String, Object> findOne(String entity, String id);

    Map<String, Object> save(String entity, Map<String, Object> data);

    Map<String, Object> update(String entity, String id, Map<String, Object> data);

    Map<String, Object> deleteLogic(String entity, String id);
}
