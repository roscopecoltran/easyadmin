package com.easyadmin.service;

import com.easyadmin.schema.domain.Entity;
import com.easyadmin.schema.domain.Field;
import com.easyadmin.schema.enums.CRUDPermission;
import com.easyadmin.schema.enums.Redirect;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gongxinyi on 2017-08-10.
 */
@org.springframework.stereotype.Component
public class SchemaServiceImpl implements SchemaService {

    public List<Entity> findEntitys() {
        List<Entity> entities = DbUtil.getDataStore().find(Entity.class).asList();

        List<Field> fields = DbUtil.getDataStore().find(Field.class).asList();
        fields.forEach(field -> {
            entities.forEach(entity -> {
                if (field.getEid().equals(entity.getId())) {
                    if (CollectionUtils.isEmpty(entity.getFields()))
                        entity.setFields(new ArrayList<>());
                    field.setShowInList(true);
                    entity.getFields().add(field);
                }
            });
        });

        entities.forEach(entity -> {
            entity.setCrud(CRUDPermission.values());
            entity.setRedirect(Redirect.list);
        });

        return entities;
    }

    public Entity findOne(String eid) {
        return DbUtil.getDataStore().get(Entity.class, eid);
    }

    public List<Field> findFields(String eid) {
        return DbUtil.getDataStore().find(Field.class).field("eid").equal(eid).asList();
    }

    public Field findOneField(String fid) {
        return DbUtil.getDataStore().get(Field.class, fid);
    }
}
