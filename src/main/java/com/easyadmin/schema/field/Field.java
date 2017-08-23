package com.easyadmin.schema.field;

import com.easyadmin.schema.enums.Component;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

/**
 * parent class for html input component
 * <p>
 * has some commmon props , ex:label,defaultValue,required
 * <p>
 * Component contains Text,Boolean and ...
 * <p>
 * the props of fields in list page will define in inherit class
 * <p>
 * Created by gongxinyi on 2017-08-10.
 */
@Data
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Field implements Serializable {
    protected String id;
    protected String name;
    protected Component component;
    protected String label;
    protected Object defaultValue;
    protected Boolean required;

    public Field(String name, Component component, String label, Object defaultValue, Boolean required) {
        this.name = name;
        this.component = component;
        this.label = label;
        this.defaultValue = defaultValue;
        this.required = required;
    }
}
