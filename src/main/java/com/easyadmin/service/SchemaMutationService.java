package com.easyadmin.service;

import com.easyadmin.schema.Entity;
import com.easyadmin.schema.field.Field;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.model.Indexes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by gongxinyi on 2017-08-11.
 */
@Slf4j
@Component
public class SchemaMutationService {
    @Autowired
    DataMutationService dataMutationService;
    /**
     * create full text search index
     *
     * @param entity
     * @param field
     */
    public void createTextIndex(String entity, String field) {
        DbUtil.getCollection(entity).createIndex(Indexes.text(field));
    }

    public void saveEntity(Entity entity){
        dataMutationService.save("entitys",new ObjectMapper().convertValue(entity,Map.class));
    }

    public void updateEntity(Entity entity){
        dataMutationService.update("entitys",entity.getId(),new ObjectMapper().convertValue(entity,Map.class));
    }

    public void addField(Field field){
        dataMutationService.save("fields",new ObjectMapper().convertValue(field,Map.class));
    }
}
