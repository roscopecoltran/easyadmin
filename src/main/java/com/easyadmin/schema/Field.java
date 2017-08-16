package com.easyadmin.schema;

import com.easyadmin.schema.enums.Component;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

/**
 * Created by gongxinyi on 2017-08-10.
 */
@Data
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Field implements Serializable {
    protected String name;
    protected Component component;
}
