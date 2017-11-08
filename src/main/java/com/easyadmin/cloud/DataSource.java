package com.easyadmin.cloud;

import lombok.Data;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 *
 * @author gongxinyi
 * @date 2017-11-08
 */
@Data
@Entity(value = "_datasource", noClassnameStored = true)
public class DataSource {
    @Id
    private String id;
    private String jdbcUrl;
    private String username;
    private String password;
    private String type;
    private boolean isCurrent;
}
