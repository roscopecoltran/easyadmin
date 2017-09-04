package com.easyadmin.schema.domain;

import lombok.Data;
import org.mongodb.morphia.annotations.PrePersist;

import java.util.Date;

/**
 * Created by gongxinyi on 2017-09-04.
 */
@Data
public class BaseEntity {
    private Date creationDate;
    private Date lastChange;

    @PrePersist
    public void prePersist() {
        creationDate = (creationDate == null) ? new Date() : creationDate;
        lastChange = (lastChange == null) ? creationDate : new Date();
    }
}
