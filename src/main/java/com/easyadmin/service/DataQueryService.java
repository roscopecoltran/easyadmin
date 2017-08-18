package com.easyadmin.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Created by gongxinyi on 2017-08-11.
 */
@Slf4j
@RestController
public class DataQueryService {
    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "/api/{table}", method = RequestMethod.GET)
    @SneakyThrows(JsonProcessingException.class)
    public ResponseEntity<List<Map<String, Object>>> dataQuery(@PathVariable("table") String table,@RequestParam final Map<String, Object> allRequestParams) {
        log.info("params:{}", new ObjectMapper().writeValueAsString(allRequestParams));
        List data = list(table);
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("X-Total-Count", data.size() + "")
                .header("Access-Control-Expose-Headers", "X-Total-Count")
                .body(data);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "/api/{table}/{id}", method = RequestMethod.GET)
    @SneakyThrows(JsonProcessingException.class)
    public ResponseEntity<Map<String, Object>> findOne(@PathVariable("table") String table,@PathVariable("id") String id,@RequestParam final Map<String, Object> allRequestParams) {
        log.info("params:{}", new ObjectMapper().writeValueAsString(allRequestParams));
        Map<String, Object> object = findOne(table,id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(object);
    }

    private List<Map<String, Object>> list(String table) {
        MongoCollection collection = getConnection(table);
        List<Map<String, Object>> dataList = new LinkedList<>();
        FindIterable<Document> findIterable = collection.find();
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        while (mongoCursor.hasNext()) {
            Document doc = mongoCursor.next();
            dataList.add(doc);
        }
        return dataList;
    }

    private Map<String, Object> findOne(String table,String id) {
        MongoCollection collection = getConnection(table);
        BasicDBObject query = new BasicDBObject("id", id);
        collection.find(query);
        FindIterable<Document> findIterable = collection.find();
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        return mongoCursor.next();
    }

    public static MongoCollection getConnection(String table) {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        MongoDatabase database = mongoClient.getDatabase("mydb");
        return database.getCollection(table);
    }

}
