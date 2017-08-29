package com.easyadmin.service;

import com.easyadmin.consts.Consts;
import com.mongodb.BasicDBObject;
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
        if (!data.containsKey(Consts.id)) {
            String id = SequenceUtil.getNextSequence(entity + Consts._id).toString();
            data.put(Consts.id, id);
        }
        data.put(Consts._id, data.get(Consts.id));
        data.remove(Consts.id);
        Document document = new Document(data);
        document.put(Consts.DEL_FLAG, false);
        collection.insertOne(document);
        return document;
    }

    public Document update(String entity, String id, Map<String, Object> data) {
        MongoCollection collection = DbUtil.getCollection(entity);
        Document document = new Document(data);
        BasicDBObject searchQuery = new BasicDBObject().append(Consts._id, id);
        collection.replaceOne(searchQuery, document);
        return document;
    }

    public Document deleteLogic(String entity, String id) {
        MongoCollection<Document> collection = DbUtil.getCollection(entity);
        BasicDBObject searchQuery = new BasicDBObject().append(Consts._id, id);
        Document document = collection.findOneAndUpdate(searchQuery, new BasicDBObject("$set", new BasicDBObject(Consts.DEL_FLAG, true)));
        return document;
    }
}
