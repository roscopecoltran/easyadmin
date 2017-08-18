package com.easyadmin.service;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 *
 */
public class DbUtil {

    public static MongoCollection getCollection(String host, int port, String db, String entity) {
        MongoClient mongoClient = new MongoClient(host, port);
        MongoDatabase database = mongoClient.getDatabase(db);
        return database.getCollection(entity);
    }

    public static MongoCollection getCollection(String entity) {
        return getCollection("localhost", 27017, "mydb", entity);
    }
}
