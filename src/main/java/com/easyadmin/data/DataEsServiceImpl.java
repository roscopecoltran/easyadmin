package com.easyadmin.data;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by gongxinyi on 2017-11-13.
 */
@Component
public class DataEsServiceImpl implements IDataService {
    @Override
    public List<Map<String, Object>> list(String entity, Map<String, Object> allRequestParams) {
        return null;
    }

    @Override
    public long count(String entity, Map<String, Object> allRequestParams) {
        return 0;
    }

    @Override
    public Map<String, Object> findOne(String entity, String id) {
        return null;
    }

    @Override
    public Map<String, Object> save(String entity, Map<String, Object> data) {
        return null;
    }

    @Override
    public Map<String, Object> update(String entity, String id, Map<String, Object> data) {
        return null;
    }

    @Override
    public String delete(String entity, String id) {
        return null;
    }
}
