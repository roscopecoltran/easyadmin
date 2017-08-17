package com.easyadmin.schema;

import com.easyadmin.schema.enums.Component;
import lombok.*;

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
    protected String label;
    protected Object defaultValue;
    protected Boolean required;

}
