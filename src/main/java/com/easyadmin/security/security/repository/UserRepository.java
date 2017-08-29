package com.easyadmin.security.security.repository;


import com.easyadmin.security.security.User;

/**
 * Created by stephan on 20.03.16.
 */
public interface UserRepository {
    User findByUsername(String username);
}
