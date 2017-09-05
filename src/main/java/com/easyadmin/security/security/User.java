package com.easyadmin.security.security;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Reference;

import java.util.Date;
import java.util.List;

@Entity(value = "_user", noClassnameStored = true)
@Data
public class User {
    @Id
    private String id;
    private String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String email;
    private Boolean enabled;
    private Date lastPasswordResetDate;
    @Reference
    @JsonDeserialize(contentAs = Role.class)
    @JsonSerialize(converter = ListRoleConverter.class)
    private List<Role> roles;
}