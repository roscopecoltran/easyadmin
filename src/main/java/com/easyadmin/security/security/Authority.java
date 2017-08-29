package com.easyadmin.security.security;

import lombok.Data;

import java.util.List;

@Data
public class Authority {
    private Long id;
    private AuthorityName name;
    private List<User> users;
}