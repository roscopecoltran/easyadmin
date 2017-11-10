package com.easyadmin.service;

import com.easyadmin.cloud.DataSource;
import com.easyadmin.cloud.Tenant;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 *
 */
@Component
public class SysService {

    @Autowired
    MorphiaFactory morphiaFactory;

    /**
     * all the tenant cache the mongoClient
     */
    @Autowired
    Map<String, MongoClient> uriMongoMap;

    public Datastore getSysDataStore() {
        return morphiaFactory.get();
    }

    public Datastore getTenantDataStore() {
        Tenant tenant = Tenant.get();
        initMongoClient(tenant.getConnectionStr());
        return new Morphia().createDatastore(uriMongoMap.get(tenant.getConnectionStr()), tenant.getDbName());
    }

    public MongoCollection getTenantCollection(String entity) {
        Tenant tenant = Tenant.get();
        initMongoClient(tenant.getConnectionStr());
        return uriMongoMap.get(tenant.getConnectionStr()).getDatabase(tenant.getDbName()).getCollection(entity);
    }

    protected void initMongoClient(String uri) {
        if (!uriMongoMap.containsKey(uri)) {
            MongoClientURI mongoClientURI = new MongoClientURI(uri);
            MongoClient client = new MongoClient(mongoClientURI);
            uriMongoMap.put(uri, client);
        }
    }

    public DataSource getCurrentDataSource() {
        List<DataSource> dataSourceList = getTenantDataStore().createQuery(DataSource.class).asList();
        Optional<DataSource> optional = dataSourceList.stream().filter(dataSource -> dataSource.isCurrent()).findFirst();
        if (optional.isPresent()) {
            return optional.get();
        }
        return dataSourceList.get(0);
    }
}
