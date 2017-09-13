package com.easyadmin.security.security.service.impl;

import com.easyadmin.schema.domain.Entity;
import com.easyadmin.schema.enums.CRUDPermission;
import com.easyadmin.security.security.service.SecurityService;
import com.easyadmin.service.SchemaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by gongxinyi on 2017-08-30.
 */
@Service
@Slf4j
public class SecurityServiceImpl implements SecurityService {

    @Autowired
    SchemaService schemaService;

    @Override
    public Boolean hasProtectedAccess(String entity, CRUDPermission permission) {
        List<Entity> entityList = schemaService.findEntitys();
        Optional<Entity> result = entityList.stream()
                .filter(entityPermission -> entityPermission.getId().equals(entity) && entityPermission.getCrud().contains(permission))
                .findAny();
        return result.isPresent();
    }

}
