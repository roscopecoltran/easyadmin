package com.easyadmin.schema;

import com.easyadmin.schema.enums.Component;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Created by gongxinyi on 2017-08-16.
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class DateField extends Field {
    @Builder(toBuilder = true)
    public DateField(String name, String label, Object defaultValue,Boolean required) {
        super(name, Component.Date, label, defaultValue,required);
    }
}
