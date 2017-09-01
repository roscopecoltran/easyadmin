package com.easyadmin.service;

import com.easyadmin.consts.Constants;
import com.easyadmin.schema.domain.Entity;
import com.easyadmin.schema.domain.Field;
import com.easyadmin.schema.enums.CRUDPermission;
import com.easyadmin.schema.enums.Redirect;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import org.bson.Document;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gongxinyi on 2017-08-10.
 */
@org.springframework.stereotype.Component
public class SchemaServiceImpl implements SchemaService {
    final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public List<Entity> findEntitys() {
        List<Entity> entities = new ArrayList<>();
        Block<Document> wrapEntitysBlock = doc -> entities.add(doc2Entity(doc));
        DbUtil.getCollection(Constants.SYS_COL_Entity).find().forEach(wrapEntitysBlock);

        Map<String, List<Field>> entity2FieldsMap = new HashMap();
        entities.forEach(entity ->
                entity2FieldsMap.put(entity.getId(), new ArrayList<>())
        );
        Block<Document> wrapFieldsBlock = doc -> {
            String entity = doc.getString(Constants.ENTITY_ID);
            if (!StringUtils.isEmpty(entity))
                entity2FieldsMap.get(doc.getString(Constants.ENTITY_ID)).add(doc2Field(doc));
        };

        DbUtil.getCollection(Constants.SYS_COL_Field).find().forEach(wrapFieldsBlock);

        entities.forEach(entity -> {
            entity.setCrud(CRUDPermission.values());
            entity.setRedirect(Redirect.list);
            entity.setFields(entity2FieldsMap.get(entity.getId()));
        });


        return entities;
    }

    public Entity findOne(String eid) {
        List<Entity> entities = new ArrayList<>();
        Block<Document> wrapBlock = doc -> entities.add(doc2Entity(doc));
        DbUtil.getCollection(Constants.SYS_COL_Entity).find(new BasicDBObject(Constants._id, eid)).forEach(wrapBlock);
        return entities.get(0);
    }

    private Field doc2Field(Document doc) {
        doc.put(Constants.id, doc.get(Constants._id));
        Field field = mapper.convertValue(doc, Field.class);
        field.setShowInList(true);
        if (field.isReference() && StringUtils.isEmpty(field.getReferenceOptionText()))
            field.setReferenceOptionText("id");
        return field;
    }

    private Entity doc2Entity(Document doc) {
        return new Entity(doc.getString(Constants._id), doc.getString("label"));
    }

    public List<Field> findFields(String eid) {
        List<Field> fields = new ArrayList<>();
        Block<Document> wrapBlock = doc -> {
            doc.put(Constants.id, doc.get(Constants._id));
            fields.add(mapper.convertValue(doc, Field.class));
        };
        DbUtil.getCollection(Constants.SYS_COL_Field).find(new BasicDBObject(Constants.ENTITY_ID, eid)).forEach(wrapBlock);
        return fields;
    }

    public Field findOneField(String fieldId) {
        List<Field> fields = new ArrayList<>();
        Block<Document> wrapBlock = doc -> {
            doc.put(Constants.id, doc.get(Constants._id));
            fields.add(mapper.convertValue(doc, Field.class));
        };
        DbUtil.getCollection(Constants.SYS_COL_Field).find(new BasicDBObject(Constants._id, fieldId)).forEach(wrapBlock);
        return fields.get(0);
    }
}