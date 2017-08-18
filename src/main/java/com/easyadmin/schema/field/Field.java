package com.easyadmin.schema.field;

import com.easyadmin.schema.enums.Component;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

/**
 * parent class for html input component
 *
 * has some commmon props , ex:label,defaultValue,required
 *
 * Component contains Text,Boolean and ...
 *
 * the props of fields in list page will define in inherit class
 *
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
