package com.easyadmin.service;

import com.easyadmin.cloud.Tenant;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
@Component
public class DbService {
    @Autowired
    MorphiaFactory morphiaFactory;

    /**
     * all the tenant cache the mongoClient
     */
    static Map<String, MongoClient> tenantMongoMap = new HashMap<>();

    public Datastore getSysDataStore() {
        return morphiaFactory.get();
    }

    public Datastore getDataStore() {
        String connectionStr = Tenant.get().getConnectionStr();
        initMongoClient(connectionStr);
        return new Morphia().createDatastore(tenantMongoMap.get(connectionStr), Tenant.get().getDbName());
    }

    public MongoCollection getCollection(String entity) {
        String connectionStr = Tenant.get().getConnectionStr();
        initMongoClient(connectionStr);
        return tenantMongoMap.get(connectionStr).getDatabase(Tenant.get().getDbName()).getCollection(entity);
    }

    private void initMongoClient(String connectionStr) {
        if (!tenantMongoMap.containsKey(connectionStr)) {
            MongoClientURI uri = new MongoClientURI(connectionStr);
            MongoClient client = new MongoClient(uri);
            tenantMongoMap.put(connectionStr, client);
        }
    }
}
