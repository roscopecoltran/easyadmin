package com.easyadmin.schema.domain;

import com.easyadmin.schema.enums.CRUDPermission;
import com.easyadmin.schema.enums.Redirect;
import com.easyadmin.schema.domain.Field;
import lombok.*;

import java.util.List;

/**
 * Created by gongxinyi on 2017-08-23.
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode()
@NoArgsConstructor
public final class Entity {
    private String id;
    private String label;
    private List<Field> fields;

    private CRUDPermission[] crud;
    private Redirect redirect;// 新增记录后跳转到哪个页面 edit,show,list
    @Builder
    public Entity(String id, String label) {
        this.id = id;
        this.label = label;
    }
}
