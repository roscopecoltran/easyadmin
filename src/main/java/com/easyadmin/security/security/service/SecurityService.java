package com.easyadmin.security.security.service;

import com.easyadmin.schema.enums.CRUDPermission;

/**
 * current login user has the permission of an entity
 * Created by gongxinyi on 2017-08-30.
 */
public interface SecurityService {
    Boolean hasProtectedAccess(String entity, CRUDPermission permission);
}
