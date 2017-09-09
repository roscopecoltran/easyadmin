package com.easyadmin.cloud;

import lombok.Data;
import org.mongodb.morphia.annotations.Entity;

/**
 * Created by gongxinyi on 2017-09-09.
 */
@Data
@Entity(value = "apply",noClassnameStored = true)
public class Apply {
    private String userName;
    private String mobile;
    private String email;
}
