package com.easyadmin.service;


import com.easyadmin.cloud.DataSource;
import com.easyadmin.cloud.Tenant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.network.InetAddresses;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * @author gongxinyi
 * @date 2017-11-12
 */
@Slf4j
@org.springframework.stereotype.Component
public class EsService {

    @Autowired
    Map<String, TransportClient> esClientMap;

    public TransportClient getEsClient() {
        DataSource dataSource = Tenant.get().getCurrentDataSource();
        String nodes = dataSource.getJdbcUrl();
        if (!esClientMap.containsKey(nodes)) {
            esClientMap.put(nodes, dataSource2TransportClient(dataSource));
        }
        return esClientMap.get(nodes);
    }

    private TransportClient dataSource2TransportClient(DataSource dataSource) {
        Settings settings = Settings.builder()
                .put("cluster.name", dataSource.getClusterName())
                .build();
        TransportClient client = new PreBuiltTransportClient(settings);
        if (StringUtils.isBlank(dataSource.getJdbcUrl())) {
            throw new IllegalArgumentException("elasticsearch nodes can not be empty");
        }
        String[] nodeAddress = dataSource.getJdbcUrl().split(",");
        for (String node : nodeAddress) {
            String[] nodeStr = node.split(":");
            client.addTransportAddress(new InetSocketTransportAddress(InetAddresses.forString(nodeStr[0]), Integer.parseInt(nodeStr[1])));
        }
        return client;
    }


}
