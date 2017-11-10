package com.easyadmin.schema.domain;

import com.easyadmin.schema.enums.CRUDPermission;
import com.easyadmin.schema.enums.Redirect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.mongodb.morphia.annotations.Id;

import java.util.List;

/**
 * Created by gongxinyi on 2017-08-23.
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode()
@NoArgsConstructor
@org.mongodb.morphia.annotations.Entity(value = "_entitys", noClassnameStored = true)
public final class Entity {
    @Id
    private String id;
    private String label;
    private List<Field> fields;

    private List<CRUDPermission> crud;
    private Redirect redirect;// 新增记录后跳转到哪个页面 edit,show,list

    @JsonIgnore
    private String dataSourceId;

    private boolean showInMenu;
}
