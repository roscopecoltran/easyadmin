package com.easyadmin.service;

import com.easyadmin.consts.Consts;
import com.easyadmin.data.RequestScope;
import com.easyadmin.schema.domain.Field;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.QueryBuilder;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Sorts.ascending;
import static com.mongodb.client.model.Sorts.descending;

/**
 * Created by gongxinyi on 2017-08-11.
 */
@Slf4j
@Component
public class DataQueryService {

    @Autowired
    SchemaQueryService schemaQueryService;

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
        DBObject query = getQuery(entity, allRequestParams);
        Map<String, Object> collect = allRequestParams.entrySet()
                .stream()
                .filter(map -> map.getKey().startsWith("_"))
                .collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue()));
        RequestScope requestScope = new ObjectMapper().convertValue(collect, RequestScope.class);
        FindIterable<Document> findIterable = collection.find((Bson) query).sort(sort(requestScope)).skip(requestScope.get_start()).limit(requestScope.getLimit());

        MongoCursor<Document> mongoCursor = findIterable.iterator();
        try {
            while (mongoCursor.hasNext()) {
                Document doc = mongoCursor.next();
                doc.put(Consts.id, doc.get(Consts._id));
                dataList.add(doc);
            }
        } finally {
            mongoCursor.close();
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
        DBObject query = getQuery(entity, allRequestParams);
        long count = collection.count((Bson) query);
        return count;
    }

    public Map<String, Object> findOne(String entity, String id) {
        Map<String, Object> data = null;
        MongoCollection collection = DbUtil.getCollection(entity);
        BasicDBObject query = new BasicDBObject(Consts._id, id);
        FindIterable<Document> findIterable = collection.find(query);
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        try {
            if (mongoCursor.hasNext())
                data = mongoCursor.next();
        } finally {
            mongoCursor.close();
        }
        data.put(Consts._id, data.get(Consts._id));
        data.remove(Consts._id);
        return data;
    }

    /**
     * wrap query filter
     *
     * @param allRequestParams
     * @return
     */
    private DBObject getQuery(String entity, Map<String, Object> allRequestParams) {
        // text search
        Object search = allRequestParams.get(Consts.Q);
        QueryBuilder query = new QueryBuilder();
        if (!StringUtils.isEmpty(search)) {
            query.text(search.toString());
        }

        // common filter
        allRequestParams.entrySet()
                .stream()
                .filter(map ->
                        (!map.getKey().equals(Consts.Q) && !map.getKey().startsWith("_"))
                )
                .forEach(objectEntry -> query.and(buildQuery(objectEntry, schemaQueryService.findFields(entity)).get()));
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

    private QueryBuilder buildQuery(Map.Entry<String, Object> entry, List<Field> fields) {
        log.info("entry:{}", entry);
        Map<String, Field> result =
                fields.stream().collect(Collectors.toMap(Field::getId,
                        Function.identity()));
        Field field = result.get(entry.getKey());
        QueryBuilder qb = QueryBuilder.start().put(entry.getKey());
        switch (field.getComponent()) {
            case Boolean:
                qb.is(Boolean.valueOf(entry.getValue().toString()));
                break;
            case NullableBoolean:
                if ("null".equalsIgnoreCase(entry.getValue().toString())) {
                    qb.is(null).get();
                } else {
                    qb.is(Boolean.valueOf(entry.getValue().toString()));
                }
                break;
            case CheckboxGroup:
                qb.is(entry.getValue().toString().split(","));
                break;
            case Number:
                qb.is(Integer.parseInt(entry.getValue().toString()));
                break;
            case ReferenceArray:
                qb.is(entry.getValue().toString().split(","));
                break;
            case SelectArray:
                qb.is(entry.getValue().toString().split(","));
                break;
            default:
                qb.is(entry.getValue().toString());
                break;

        }

        return qb;
    }
}
