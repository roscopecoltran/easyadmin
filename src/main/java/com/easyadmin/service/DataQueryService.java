package com.easyadmin.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
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
    public ResponseEntity<List<Map<String, Object>>> dataQuery(@RequestParam final Map<String, Object> allRequestParams) {
        log.info("params:{}", new ObjectMapper().writeValueAsString(allRequestParams));
        List data = list();
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("X-Total-Count", data.size() + "")
                .header("Access-Control-Expose-Headers", "X-Total-Count")
                .body(data);
    }

    private List<Map<String, Object>> list() {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        MongoDatabase database = mongoClient.getDatabase("mydb");
        MongoCollection collection = database.getCollection("test");
        List<Map<String, Object>> dataList = new LinkedList<>();
        FindIterable<Document> findIterable = collection.find();
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        while (mongoCursor.hasNext()) {
            dataList.add(mongoCursor.next());
            log.info("{}", mongoCursor.next());
        }
        return dataList;
    }
}
