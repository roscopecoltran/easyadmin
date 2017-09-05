package com.easyadmin.service;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;
import java.net.UnknownHostException;

/**
 * Created by gongxinyi on 2017-09-05.
 */
@Configuration
@EnableConfigurationProperties(MongoProperties.class)
public class MongoDBConfiguration {
    @Autowired
    private MongoProperties properties;

    private Mongo mongo;

    @PreDestroy
    public void close() {
        if (this.mongo != null) {
            this.mongo.close();
        }
    }

    @Bean
    public Mongo mongo() throws UnknownHostException {
        this.mongo = new MongoClient(properties.getUri());
        return this.mongo;
    }
}
