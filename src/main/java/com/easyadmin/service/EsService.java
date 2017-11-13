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
        String nodes = dataSource.getNodes();
        if (!esClientMap.containsKey(nodes)) {
            esClientMap.put(nodes, dataSource2TransportClient(dataSource));
        }
        return esClientMap.get(nodes);
    }

    private TransportClient dataSource2TransportClient(DataSource dataSource) {
        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY);
        if (StringUtils.isBlank(dataSource.getNodes())) {
            throw new IllegalArgumentException("elasticsearch nodes can not be empty");
        }
        String[] nodeAddress = dataSource.getNodes().split(",");
        for (String node : nodeAddress) {
            String[] nodeStr = node.split(":");
            client.addTransportAddress(new InetSocketTransportAddress(InetAddresses.forString(nodeStr[0]), Integer.parseInt(nodeStr[1])));
        }
        return client;
    }


}
