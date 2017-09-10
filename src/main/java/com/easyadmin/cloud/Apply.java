package com.easyadmin.cloud;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.mongodb.morphia.annotations.Entity;

/**
 * Created by gongxinyi on 2017-09-09.
 */
@Data
@Entity(value = "apply",noClassnameStored = true)
public class Apply {
    @NotEmpty(message="姓名不能为空")
    private String username;
    @NotEmpty(message="密码不能为空")
    private String password;
    private String mobile;
    private String email;
}
