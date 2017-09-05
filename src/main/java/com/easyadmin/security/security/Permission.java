package com.easyadmin.security.security;

import lombok.Data;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 * Created by gongxinyi on 2017-09-04.
 */
@Entity(value = "_permission", noClassnameStored = true)
@Data
public class Permission {
    @Id
    private String id;
    private String eid;
    private String roleId;
    private Boolean c;
    private Boolean r;
    private Boolean u;
    private Boolean d;
}
