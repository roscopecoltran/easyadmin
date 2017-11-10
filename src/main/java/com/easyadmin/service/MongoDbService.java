package com.easyadmin.service;

import com.easyadmin.cloud.Tenant;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbColumn;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbSchema;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbSpec;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbTable;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.zaxxer.hikari.HikariDataSource;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import schemacrawler.schema.Catalog;
import schemacrawler.schema.Column;
import schemacrawler.schema.Table;
import schemacrawler.schemacrawler.RegularExpressionInclusionRule;
import schemacrawler.schemacrawler.SchemaCrawlerOptions;
import schemacrawler.utility.SchemaCrawlerUtility;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
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
