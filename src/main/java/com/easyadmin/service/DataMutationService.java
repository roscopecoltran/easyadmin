package com.easyadmin.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by gongxinyi on 2017-08-11.
 */
@Slf4j
@RestController
public class DataMutationService {
    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "/api/{table}", method = RequestMethod.POST)
    @SneakyThrows(JsonProcessingException.class)
    public ResponseEntity<String> dataMutation(@PathVariable("table") String table, @RequestBody final Map<String, Object> allRequestParams) {
        log.info("params:{}", new ObjectMapper().writeValueAsString(allRequestParams));

        save(table, allRequestParams);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    private String save(String table, Map<String, Object> data) {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        MongoDatabase database = mongoClient.getDatabase("mydb");
        MongoCollection collection = database.getCollection(table);
        String id = SequenceUtil.getNextSequence(table + "_id", database).toString();
        data.put("id", id);
        Document document = new Document(data);
        document.put("_id",id);
        collection.insertOne(document);
        return id;
    }
}
