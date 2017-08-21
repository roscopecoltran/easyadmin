package com.easyadmin.service;

import com.mongodb.client.model.Indexes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Created by gongxinyi on 2017-08-11.
 */
@Slf4j
@Component
public class SchemaMutationService {
    /**
     * create full text search index
     *
     * @param entity
     * @param field
     */
    public void createTextIndex(String entity, String field) {
        DbUtil.getCollection(entity).createIndex(Indexes.text(field));
    }
}
