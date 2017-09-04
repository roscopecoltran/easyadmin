package com.easyadmin.security.security;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Reference;

import java.util.List;

@Entity(value = "_role", noClassnameStored = true)
@Data
@NoArgsConstructor
public class Role {
    @Id
    private String id;
    private String name;
    @Reference
    private List<User> users;

    public Role(String id) {
        this.id = id;
    }
}