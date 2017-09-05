package com.easyadmin.service;

import com.easyadmin.schema.domain.Entity;
import com.easyadmin.schema.domain.Field;
import com.easyadmin.schema.enums.CRUDPermission;
import com.easyadmin.schema.enums.Redirect;
import com.easyadmin.security.security.AuthorityName;
import com.easyadmin.security.security.Permission;
import com.mongodb.util.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by gongxinyi on 2017-08-10.
 */
@Slf4j
@org.springframework.stereotype.Component
public class SchemaServiceImpl implements SchemaService {

    public List<Entity> findEntitys() {
        List<Entity> entities = DbUtil.getDataStore().find(Entity.class).asList();

        List<Field> fields = DbUtil.getDataStore().find(Field.class).asList();
        fields.forEach(field -> {
            entities.forEach(entity -> {
                if (field.getEid().equals(entity.getId())) {
                    if (CollectionUtils.isEmpty(entity.getFields()))
                        entity.setFields(new ArrayList<>());
                    field.setShowInList(true);
                    entity.getFields().add(field);
                }
            });
        });

        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        Collection<GrantedAuthority> grantedAuthoritys = authentication.getAuthorities();
        List<String> roles = grantedAuthoritys.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        if (roles.contains(AuthorityName.ROLE_ADMIN.toString())) {
            entities.forEach(entity -> {
                entity.setCrud(Arrays.asList(CRUDPermission.values()));
                entity.setRedirect(Redirect.list);
            });
            return entities;
        }
        List<Permission> permissions = DbUtil.getDataStore().createQuery(Permission.class).field("roleId").in(roles).asList();

        List<Entity> entityList = entities.stream()
                .filter(entity -> permissions.stream().anyMatch(t -> t.getEid().equals(entity.getId()) && t.containsPermission()))
                .map(entity -> {
                    List<CRUDPermission> crudPermissions = new ArrayList<>();
                    permissions.stream()
                            .filter(permission -> permission.getEid().equals(entity.getId()))
                            .forEach(permission -> crudPermissions.addAll(permission.wrapCrudPermissions()));

                    entity.setRedirect(Redirect.list);
                    entity.setCrud(crudPermissions);
                    return entity;
                })
                .collect(Collectors.toList());
        return entityList;
    }

    public Entity findOne(String eid) {
        return DbUtil.getDataStore().get(Entity.class, eid);
    }

    public List<Field> findFields(String eid) {
        return DbUtil.getDataStore().find(Field.class).field("eid").equal(eid).asList();
    }

    public Field findOneField(String fid) {
        return DbUtil.getDataStore().get(Field.class, fid);
    }
}
