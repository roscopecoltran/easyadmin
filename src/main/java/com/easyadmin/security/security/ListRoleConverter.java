package com.easyadmin.security.security;

import com.fasterxml.jackson.databind.util.StdConverter;

import java.util.List;
import java.util.stream.Collectors;

public class ListRoleConverter extends StdConverter<List<Role>, List<String>> {
    @Override
    public List<String> convert(List<Role> roles) {
        return roles.stream().map(Role::getId).collect(Collectors.toList());
    }
}
