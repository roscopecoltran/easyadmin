package com.easyadmin.data;

import com.easyadmin.consts.Constants;
import com.easyadmin.schema.SchemaService;
import com.easyadmin.schema.domain.Field;
import com.easyadmin.schema.domain.Filter;
import com.easyadmin.service.MongoService;
import com.easyadmin.service.SequenceService;
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

import javax.annotation.Resource;
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
@Component("dataMongoDbService")
public class DataMongoDbServiceImpl implements IDataService {

    @Autowired
    MongoService mongoService;
    @Autowired
    SequenceService sequenceService;
    @Autowired
    DataServiceHelper dataServiceHelper;

    /**
     * get data json
     *
     * @param entity
     * @param filters
     * @return
     */
    @Override
    public List<Map<String, Object>> list(String entity, Map<String, Object> filters) {
        MongoCollection collection = mongoService.getCustomerCollection(entity);
        Map<String, Field> fieldMap = dataServiceHelper.getFieldIdMap(entity);
        List<Map<String, Object>> dataList = new LinkedList<>();
        DBObject query = getQuery(filters, fieldMap);
        Map<String, Object> collect = filters.entrySet()
                .stream()
                .filter(map -> map.getKey().startsWith("_"))
                .collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue()));
        RequestScope requestScope = new ObjectMapper().convertValue(collect, RequestScope.class);
        FindIterable<Document> findIterable = collection.find((Bson) query).sort(sort(requestScope)).skip(requestScope.get_start()).limit(requestScope.getLimit());

        MongoCursor<Document> mongoCursor = findIterable.iterator();
        try {
            while (mongoCursor.hasNext()) {
                Document doc = mongoCursor.next();
                doc.put(Constants.id, doc.get(Constants._id));
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
     * @param filters
     * @return
     */
    @Override
    public long count(String entity, Map<String, Object> filters) {
        MongoCollection collection = mongoService.getCustomerCollection(entity);
        Map<String, Field> fieldMap = dataServiceHelper.getFieldIdMap(entity);
        DBObject query = getQuery(filters, fieldMap);
        long count = collection.count((Bson) query);
        return count;
    }

    @Override
    public Map<String, Object> findOne(String entity, String id) {
        Map<String, Object> data = null;
        MongoCollection collection = mongoService.getCustomerCollection(entity);
        BasicDBObject query = new BasicDBObject(Constants._id, id);
        FindIterable<Document> findIterable = collection.find(query);
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        try {
            if (mongoCursor.hasNext()) {
                data = mongoCursor.next();
            }
        } finally {
            mongoCursor.close();
        }
        data.put(Constants._id, data.get(Constants._id));
        data.remove(Constants._id);
        return data;
    }

    /**
     * wrap query filter
     *
     * @param filters
     * @return
     */
    private DBObject getQuery(Map<String, Object> filters, Map<String, Field> fieldMap) {
        // text search
        Object search = filters.get(Constants.Q);
        QueryBuilder query = new QueryBuilder();
        if (!StringUtils.isEmpty(search)) {
            query.text(search.toString());
        }

        // common filter
        filters.entrySet()
                .stream()
                .filter(map ->
                        (!map.getKey().equals(Constants.Q) && !map.getKey().startsWith("_"))
                )
                .forEach(entry -> {
                    Filter filter = dataServiceHelper.getFilter(entry, fieldMap);
                    buildQuery(query, filter, fieldMap.get(filter.getKey()));
                });
        // logic del flag
        query.and(QueryBuilder.start(Constants.DEL_FLAG).notEquals(true).get());
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

    private void buildQuery(QueryBuilder query, Filter filter, Field field) {
        log.info("filter:{}", filter);
        QueryBuilder qb = QueryBuilder.start().put(filter.getKey());
        switch (field.getComponent()) {
            case Boolean:
                qb.is(Boolean.valueOf(filter.getValue().toString()));
                break;
            case NullableBoolean:
                if ("null".equalsIgnoreCase(filter.getValue().toString())) {
                    qb.is(null).get();
                } else {
                    qb.is(Boolean.valueOf(filter.getValue().toString()));
                }
                break;
            case CheckboxGroup:
                qb.is(filter.getValue().toString().split(","));
                break;
            case Number:
                qb.is(Integer.parseInt(filter.getValue().toString()));
                break;
            case ReferenceArray:
                qb.is(filter.getValue().toString().split(","));
                break;
            case SelectArray:
                qb.is(filter.getValue().toString().split(","));
                break;
            default:
                qb.is(filter.getValue().toString());
                break;

        }
        query.and(qb.get());
    }

    @Override
    public Document save(String entity, Map<String, Object> data) {
        MongoCollection collection = mongoService.getCustomerCollection(entity);
        if (!data.containsKey(Constants.id) || StringUtils.isEmpty(data.get(Constants.id))) {
            String id = sequenceService.getNextSequence(entity + Constants._id).toString();
            data.put(Constants.id, id);
        }
        data.put(Constants._id, data.get(Constants.id));
        data.remove(Constants.id);
        Document document = new Document(data);
        document.put(Constants.DEL_FLAG, false);
        collection.insertOne(document);
        return document;
    }

    @Override
    public Document update(String entity, String id, Map<String, Object> data) {
        MongoCollection collection = mongoService.getCustomerCollection(entity);
        Document document = new Document(data);
        BasicDBObject searchQuery = new BasicDBObject().append(Constants._id, id);
        collection.replaceOne(searchQuery, document);
        return document;
    }

    @Override
    public String delete(String entity, String id) {
        MongoCollection<Document> collection = mongoService.getCustomerCollection(entity);
        BasicDBObject searchQuery = new BasicDBObject().append(Constants._id, id);
        collection.findOneAndUpdate(searchQuery, new BasicDBObject("$set", new BasicDBObject(Constants.DEL_FLAG, true)));
        return id;
    }
}
