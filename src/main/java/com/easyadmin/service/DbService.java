package com.easyadmin.service;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import org.mongodb.morphia.Datastore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
public class DbService {
    @Autowired
    MongoProperties mongoProperties;
    @Autowired
    MorphiaFactory morphiaFactory;
    @Autowired
    MongoClient mongo;

    public Datastore getDataStore() {
        return morphiaFactory.get();
    }

    public MongoCollection getCollection(String entity) {
        return mongo.getDatabase(mongoProperties.getDatabase()).getCollection(entity);
    }
}
