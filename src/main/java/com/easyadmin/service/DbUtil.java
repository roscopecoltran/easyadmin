package com.easyadmin.service;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Indexes;
/**
 *
 */
public class DbUtil {
    private static MongoClient mongoClient;

    public static MongoCollection getCollection(String url,String dbName, String entity) {
        if (mongoClient == null) {
            MongoClientURI uri = new MongoClientURI(url);
            mongoClient = new MongoClient(uri);
        }
        MongoDatabase database = mongoClient.getDatabase(dbName);
        return database.getCollection(entity);
    }

    public static MongoCollection getCollection(String entity) {
        return getCollection("mongodb://admin:admin@ds161443.mlab.com:61443/easyadmin", "easyadmin", entity);
//        return getCollection("mongodb://localhost:27017", "easyadmin", entity);
    }

    public static void createTextIndex(String entity, String field) {
        getCollection(entity).createIndex(Indexes.text(field));
    }

    public static void main(String[] args) {
        createTextIndex("users", "name");
        createTextIndex("posts", "RichText");
    }
}
