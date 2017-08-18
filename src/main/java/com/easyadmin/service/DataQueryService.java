package com.easyadmin.service;

import com.easyadmin.consts.Consts;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by gongxinyi on 2017-08-11.
 */
@Slf4j
@Component
public class DataQueryService {

    public List<Map<String, Object>> list(String entity) {
        MongoCollection collection = DbUtil.getCollection(entity);
        List<Map<String, Object>> dataList = new LinkedList<>();

        BasicDBObject query = new BasicDBObject();
        query.append(Consts.DEL_FLAG, false);

        FindIterable<Document> findIterable = collection.find(query);
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        while (mongoCursor.hasNext()) {
            Document doc = mongoCursor.next();
            dataList.add(doc);
        }
        return dataList;
    }

    public Map<String, Object> findOne(String entity, String id) {
        MongoCollection collection = DbUtil.getCollection(entity);
        BasicDBObject query = new BasicDBObject("id", id);
        collection.find(query);
        FindIterable<Document> findIterable = collection.find();
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        return mongoCursor.next();
    }


}
