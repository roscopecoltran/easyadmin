package com.easyadmin.schema.field;

import com.easyadmin.schema.enums.Component;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Created by gongmark on 17/8/16.
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class NumberField extends Field {
    private String minValue;
    private String maxValue;

    @Builder
    public NumberField(String name, String label, Object defaultValue, Boolean required, String minValue, String maxValue) {
        super(name, Component.Number, label, defaultValue, required);
        this.minValue = minValue;
        this.maxValue = maxValue;
    }
}
