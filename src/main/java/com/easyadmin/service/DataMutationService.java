package com.easyadmin.service;

import com.easyadmin.consts.Consts;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.client.MongoCollection;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by gongxinyi on 2017-08-11.
 */
@Slf4j
@Component
public class DataMutationService {

    public Document save(String entity, Map<String, Object> data) {
        MongoCollection collection = DbUtil.getCollection(entity);
        String id = SequenceUtil.getNextSequence(entity + "_id").toString();
        data.put("id", id);
        Document document = new Document(data);
        document.put("_id", id);
        document.put(Consts.DEL_FLAG, false);
        collection.insertOne(document);
        return document;
    }

    public Document update(String entity, String id, Map<String, Object> data) {
        MongoCollection collection = DbUtil.getCollection(entity);
        Document document = new Document(data);
        BasicDBObject searchQuery = new BasicDBObject().append("id", id);
        collection.replaceOne(searchQuery, document);
        return document;
    }

    public Document deleteLogic(String entity, String id) {
        MongoCollection<Document> collection = DbUtil.getCollection(entity);
        BasicDBObject searchQuery = new BasicDBObject().append("id", id);
        Document document = collection.findOneAndUpdate(searchQuery, new BasicDBObject("$set", new BasicDBObject(Consts.DEL_FLAG, true)));
        return document;
    }
}
