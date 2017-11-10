package com.easyadmin.security.security.service.impl;

import com.easyadmin.schema.domain.Entity;
import com.easyadmin.schema.enums.CRUDPermission;
import com.easyadmin.security.security.service.SecurityService;
import com.easyadmin.schema.SchemaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author gongxinyi
 * @date 2017-08-30
 */
@Service
@Slf4j
public class SecurityServiceImpl implements SecurityService {

    @Resource
    SchemaService schemaService;

    @Override
    public Boolean hasProtectedAccess(String entity, CRUDPermission permission) {
        List<Entity> entityList = schemaService.findEntities();
        Optional<Entity> result = entityList.stream()
                .filter(entityPermission -> entityPermission.getName().equals(entity) && entityPermission.getCrud().contains(permission))
                .findAny();
        return result.isPresent();
    }

}
