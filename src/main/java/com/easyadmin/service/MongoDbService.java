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
public class MongoDbService {

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
        Tenant tenant = Tenant.get();
        initMongoClient(tenant);
        return new Morphia().createDatastore(tenantMongoMap.get(tenant.getId()), Tenant.get().getDbName());
    }

    public MongoCollection getCollection(String entity) {
        Tenant tenant = Tenant.get();
        initMongoClient(tenant);
        return tenantMongoMap.get(tenant.getId()).getDatabase(Tenant.get().getDbName()).getCollection(entity);
    }

    private void initMongoClient(Tenant tenant) {
        if (!tenantMongoMap.containsKey(tenant.getId())) {
            MongoClientURI uri = new MongoClientURI(tenant.getConnectionStr());
            MongoClient client = new MongoClient(uri);
            tenantMongoMap.put(tenant.getId(), client);
        }
    }
}
