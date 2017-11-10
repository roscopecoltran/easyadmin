package com.easyadmin.service;

import com.easyadmin.cloud.DataSource;
import com.easyadmin.cloud.Tenant;
import com.mongodb.client.MongoCollection;
import org.springframework.stereotype.Component;

/**
 *
 * @author gongxinyi
 * @date 2017-11-10
 */
@Component
public class MongoService extends SysService {
    public MongoCollection getCustomerCollection(String entity) {
        DataSource dataSource = Tenant.get().getCurrentDataSource();

        initMongoClient(dataSource.getJdbcUrl());
        return uriMongoMap.get(dataSource.getJdbcUrl()).getDatabase(dataSource.getDbName()).getCollection(entity);
    }
}
