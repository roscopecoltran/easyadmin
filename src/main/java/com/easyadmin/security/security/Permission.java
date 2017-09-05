package com.easyadmin.security.security;

import com.easyadmin.schema.enums.CRUDPermission;
import lombok.Data;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gongxinyi on 2017-09-04.
 */
@Entity(value = "_permission", noClassnameStored = true)
@Data
public class Permission {
    @Id
    private String id;
    private String eid;
    private String roleId;
    private boolean c;
    private boolean r;
    private boolean u;
    private boolean d;

    public List<CRUDPermission> wrapCrudPermissions() {
        List<CRUDPermission> permissions = new ArrayList<>();
        if (c) permissions.add(CRUDPermission.c);
        if (r) permissions.add(CRUDPermission.r);
        if (u) permissions.add(CRUDPermission.u);
        if (d) permissions.add(CRUDPermission.d);
        return permissions;
    }

    public boolean containsPermission() {
        return c || r || u || d;
    }
}
