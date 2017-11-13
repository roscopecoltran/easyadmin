package com.easyadmin.service;

import com.easyadmin.cloud.DataSource;
import com.easyadmin.cloud.Tenant;
import com.easyadmin.data.DataEsServiceImpl;
import com.easyadmin.data.DataMongoDbServiceImpl;
import com.easyadmin.data.DataRdbServiceImpl;
import com.easyadmin.data.IDataService;
import com.easyadmin.schema.EsSchemaSyncServiceImpl;
import com.easyadmin.schema.ISchemaSyncService;
import com.easyadmin.schema.RdbSchemaSyncServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author gongxinyi
 * @date 2017-11-10
 */
@Component
public class ServiceProxy {
    @Autowired
    ApplicationContext applicationContext;

    public IDataService getDataService() {
        IDataService dataService;
        DataSource dataSource = Tenant.get().getCurrentDataSource();
        switch (dataSource.getType()) {
            case mysql:
                dataService = applicationContext.getBean(DataRdbServiceImpl.class);
                break;
            case es:
                dataService = applicationContext.getBean(DataEsServiceImpl.class);
                break;
            default:
                dataService = applicationContext.getBean(DataMongoDbServiceImpl.class);
                break;
        }
        return dataService;
    }

    public ISchemaSyncService getSchemaSyncService() {
        ISchemaSyncService schemaSyncService;
        DataSource dataSource = Tenant.get().getCurrentDataSource();
        switch (dataSource.getType()) {
            case mysql:
                schemaSyncService = applicationContext.getBean(RdbSchemaSyncServiceImpl.class);
                break;
            case es:
                schemaSyncService = applicationContext.getBean(EsSchemaSyncServiceImpl.class);
                break;
            default:
                schemaSyncService = applicationContext.getBean(RdbSchemaSyncServiceImpl.class);
                break;
        }
        return schemaSyncService;
    }
}
