package com.easyadmin.service;

import com.easyadmin.consts.Consts;
import com.easyadmin.data.RequestScope;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.QueryBuilder;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.util.JSON;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Sorts.ascending;
import static com.mongodb.client.model.Sorts.descending;

/**
 * Created by gongxinyi on 2017-08-11.
 */
@Slf4j
@Component
public class DataQueryService {

    /**
     * get data json
     *
     * @param entity
     * @param allRequestParams
     * @return
     */
    public List<Map<String, Object>> list(String entity, Map<String, Object> allRequestParams) {
        MongoCollection collection = DbUtil.getCollection(entity);
        List<Map<String, Object>> dataList = new LinkedList<>();
        DBObject query = getQuery(allRequestParams);
        Map<String, Object> collect = allRequestParams.entrySet()
                .stream()
                .filter(map -> map.getKey().startsWith("_"))
                .collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue()));
        RequestScope requestScope = new ObjectMapper().convertValue(collect, RequestScope.class);
        FindIterable<Document> findIterable = collection.find((Bson) query).sort(sort(requestScope)).skip(requestScope.get_start()).limit(requestScope.getLimit());

        MongoCursor<Document> mongoCursor = findIterable.iterator();
        while (mongoCursor.hasNext()) {
            Document doc = mongoCursor.next();
            dataList.add(doc);
        }
        return dataList;
    }

    /**
     * records num
     *
     * @param entity
     * @param allRequestParams
     * @return
     */
    public long count(String entity, Map<String, Object> allRequestParams) {
        MongoCollection collection = DbUtil.getCollection(entity);
        DBObject query = getQuery(allRequestParams);
        return collection.count((Bson) query);
    }

    public Map<String, Object> findOne(String entity, String id) {
        MongoCollection collection = DbUtil.getCollection(entity);
        BasicDBObject query = new BasicDBObject("id", id);
        FindIterable<Document> findIterable = collection.find(query);
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        return mongoCursor.next();
    }

    /**
     * wrap query filter
     *
     * @param allRequestParams
     * @return
     */
    private DBObject getQuery(Map<String, Object> allRequestParams) {
        // text search
        Object search = allRequestParams.get(Consts.Q);
        QueryBuilder query = new QueryBuilder();
        if (!StringUtils.isEmpty(search)) {
            query.text(search.toString());
        }

        // common filter
        allRequestParams.entrySet()
                .stream()
//                .filter(map ->
//                        (!map.getKey().equals(Consts.Q) && !map.getKey().startsWith("_"))
//                )
                .map(k -> (
                        query.and(QueryBuilder.start().put(k.getKey()).is(k.getValue()).get())
                ));
        // logic del flag
        query.and(QueryBuilder.start(Consts.DEL_FLAG).notEquals(true).get());
        return query.get();
    }

    /**
     * sort
     *
     * @param requestScope
     * @return
     */
    private Bson sort(RequestScope requestScope) {
        return "DESC".equalsIgnoreCase(requestScope.get_order()) ? descending(requestScope.get_sort()) : ascending(requestScope.get_sort());
    }
}
