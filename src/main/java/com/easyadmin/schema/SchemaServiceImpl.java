package com.easyadmin.schema;

import com.easyadmin.cloud.DataSource;
import com.easyadmin.cloud.Tenant;
import com.easyadmin.schema.domain.Entity;
import com.easyadmin.schema.domain.Field;
import com.easyadmin.schema.enums.CRUDPermission;
import com.easyadmin.schema.enums.Redirect;
import com.easyadmin.security.security.AuthorityName;
import com.easyadmin.security.security.Permission;
import com.easyadmin.service.SysService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author gongxinyi
 * @date 2017-08-10
 */
@Slf4j
@org.springframework.stereotype.Component
public class SchemaServiceImpl implements SchemaService {
    @Autowired
    SysService sysService;

    @Override
    public List<Entity> findEntities() {
        DataSource dataSource = Tenant.get().getCurrentDataSource();
        List<Entity> entities = sysService.getTenantDataStore().createQuery(Entity.class).field("dataSourceId").equal(dataSource.getId()).asList();

        List<Field> fields = sysService.getTenantDataStore().createQuery(Field.class).field("dataSourceId").equal(dataSource.getId()).asList();

        fields.forEach(field -> {
            entities.forEach(entity -> {
                if (field.getEid().equals(entity.getId())) {
                    if (CollectionUtils.isEmpty(entity.getFields()))
                        entity.setFields(new ArrayList<>());
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
        List<Permission> permissions = sysService.getTenantDataStore().createQuery(Permission.class).field("roleId").in(roles).asList();

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

    @Override
    public Entity findOne(String eid) {
        return findEntities().stream().filter(entity -> entity.getId().equals(eid)).findFirst().get();
    }

    @Override
    public List<Field> findFields(String eid) {
        return findOne(eid).getFields();
    }

    @Override
    public Field findOneField(String fid) {
        return sysService.getTenantDataStore().get(Field.class, fid);
    }
}
