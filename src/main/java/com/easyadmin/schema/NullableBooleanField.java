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
public class NullableBooleanField extends Field {

    @Builder
    public NullableBooleanField(String name, String label, Object defaultValue, Boolean required) {
        super(name, Component.NullableBoolean, label, defaultValue, required);
    }
}
