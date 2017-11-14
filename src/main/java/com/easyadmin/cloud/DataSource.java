package com.easyadmin.cloud;

import com.easyadmin.schema.enums.DbTypeEnum;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
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
    private DbTypeEnum type;
    private String dbName;
    private boolean isCurrent;

    /**
     * for es
     */
    private String clusterName;
    private String indexName;

    public String getMySqlDbName() {
        return StringUtils.isNotEmpty(jdbcUrl) && DbTypeEnum.mysql.equals(type) ? jdbcUrl.substring(jdbcUrl.lastIndexOf("/") + 1) : null;
    }
}
