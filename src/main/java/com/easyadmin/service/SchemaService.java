package com.easyadmin.service;

import com.easyadmin.schema.domain.Entity;
import com.easyadmin.schema.domain.Field;

import java.util.List;

/**
 * schema service
 * <p>
 * include getEntitys，findOne entity by eid，find all the Fields by eid
 * Created by gongxinyi on 2017-08-31.
 */
public interface SchemaService {
    /**
     * find all the entitys
     *
     * @return
     */
    List<Entity> findEntitys();

    /**
     * find one entity by pk
     *
     * @param eid
     * @return
     */
    Entity findOne(String eid);

    /**
     * find all the fields of an entity
     *
     * @param eid
     * @return
     */
    List<Field> findFields(String eid);

    /**
     * find one field by field id
     *
     * @param fieldId
     * @return
     */
    Field findOneField(String fieldId);
}
