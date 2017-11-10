package com.easyadmin.data;

import com.easyadmin.cloud.DataSource;
import com.easyadmin.cloud.Tenant;
import com.easyadmin.service.SysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author gongxinyi
 * @date 2017-11-10
 */
@Component
public class DataServiceProxy {
    @Autowired
    ApplicationContext applicationContext;
    @Autowired
    SysService sysService;

    public IDataService getDataService() {
        IDataService dataService;
        DataSource dataSource = sysService.getCurrentDataSource();
        switch (dataSource.getType()) {
            case mysql:
                dataService = applicationContext.getBean(DataRdbServiceImpl.class);
                break;
            default:
                dataService = applicationContext.getBean(DataMongoDbServiceImpl.class);
                break;
        }
        return dataService;
    }
}
