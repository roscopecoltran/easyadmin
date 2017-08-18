package com.easyadmin.schema.field;

import com.easyadmin.schema.enums.Component;
import com.easyadmin.schema.enums.InputType;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Created by gongxinyi on 2017-08-10.
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class TextField extends Field {
    private InputType type;
    private Integer maxLength;

    @Builder
    private TextField(String name, String label, Object defaultValue, Boolean required, InputType type, Integer maxLength) {
        super(name, Component.Text, label, defaultValue, required);
        this.type = type;
        this.maxLength = maxLength;
    }
}
