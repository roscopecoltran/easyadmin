package com.easyadmin.service;

import com.easyadmin.consts.Consts;
import com.mongodb.BasicDBObject;
import com.mongodb.QueryBuilder;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by gongxinyi on 2017-08-11.
 */
@Slf4j
@Component
public class DataQueryService {

    public List<Map<String, Object>> list(String entity, Map<String, Object> allRequestParams) {
        MongoCollection collection = DbUtil.getCollection(entity);
        List<Map<String, Object>> dataList = new LinkedList<>();
        // text search
        Object search = allRequestParams.get("q");
        QueryBuilder query = new QueryBuilder();
        if (!StringUtils.isEmpty(search)) {
            query.text(search.toString());
        }
        // logic del flag
        query.and(QueryBuilder.start(Consts.DEL_FLAG).notEquals(true).get());
        FindIterable<Document> findIterable = collection.find((Bson) query.get());
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
