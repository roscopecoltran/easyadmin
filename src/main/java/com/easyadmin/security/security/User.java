package com.easyadmin.security.security;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class User {
    private Long id;
    private String username;
    private String password;
    private String email;
    private Boolean enabled;
    private Date lastPasswordResetDate;
    private List<Authority> authorities;
}