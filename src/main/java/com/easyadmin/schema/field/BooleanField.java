package com.easyadmin.schema.field;

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
public class BooleanField extends Field {

    @Builder(toBuilder = true)
    public BooleanField(String name, String label, Object defaultValue, Boolean required) {
        super(name, Component.Boolean, label, defaultValue, required);
    }
}
