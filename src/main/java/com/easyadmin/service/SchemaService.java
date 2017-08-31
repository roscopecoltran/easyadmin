package com.easyadmin.service;

import com.easyadmin.schema.domain.Entity;
import com.easyadmin.schema.domain.Field;

import java.util.List;

/**
 * schema service
 *
 * include getEntitys，findOne entity by eid，find all the Fields by eid
 * Created by gongxinyi on 2017-08-31.
 */
public interface SchemaService {
    List<Entity> findEntitys();

    Entity findOne(String eid);

    List<Field> findFields(String eid);

    Field findOneField(String fieldId);
}
