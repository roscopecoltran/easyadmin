package com.easyadmin.service;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Indexes;

/**
 *
 */
public class DbUtil {
    private static MongoClient mongoClient;

    public static MongoCollection getCollection(String host, int port, String db, String entity) {
        MongoClientOptions.Builder options_builder = new MongoClientOptions.Builder();
        options_builder.maxConnectionIdleTime(30000);
        MongoClientOptions options = options_builder.build();
        mongoClient = new MongoClient(host+":"+port,options);
        MongoDatabase database = mongoClient.getDatabase(db);
        return database.getCollection(entity);
    }

    public static MongoCollection getCollection(String entity) {
        return getCollection("localhost", 27017, "mydb", entity);
    }

    public static void close() {
        if (mongoClient != null) {
            mongoClient.close();
            mongoClient = null;
        }
    }

    public static void createTextIndex(String entity, String field) {
        getCollection(entity).createIndex(Indexes.text(field));
        DbUtil.close();
    }

    public static void main(String[] args) {
        createTextIndex("users","name");
        createTextIndex("posts","RichText");
    }
}
