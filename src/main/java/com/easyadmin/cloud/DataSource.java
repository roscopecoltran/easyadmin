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
    private String globalUrl;
    private boolean isCurrent;

    public String getMySqlDbName() {
        if (DbTypeEnum.mysql.equals(type)) {
            return StringUtils.isNotEmpty(jdbcUrl) ? jdbcUrl.substring(jdbcUrl.lastIndexOf("/") + 1) : null;
        } else if (DbTypeEnum.cds.equals(type)) {
            return StringUtils.isNotEmpty(globalUrl) ? globalUrl.substring(globalUrl.lastIndexOf("/") + 1) : null;
        } else {
            return null;
        }
    }

    public String getCurrentSchema() {
        if (DbTypeEnum.mysql.equals(type)) {
            return StringUtils.isNotEmpty(jdbcUrl) ? jdbcUrl.substring(jdbcUrl.lastIndexOf("/") + 1) : null;
        } else if (DbTypeEnum.cds.equals(type)) {
            return StringUtils.isNotEmpty(jdbcUrl) ? jdbcUrl.substring(jdbcUrl.lastIndexOf("/") + 1, jdbcUrl.indexOf("?")) : null;
        } else {
            return null;
        }
    }

}