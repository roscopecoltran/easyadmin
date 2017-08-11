package com.easyform.schema;

import com.easyform.schema.enums.Component;
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
    private String floatingLabelText;
    private Object defaultValue;

    @Builder(toBuilder = true)
    private TextField(String name, String floatingLabelText, Object defaultValue) {
        super(name, Component.TextField);
        this.floatingLabelText = floatingLabelText;
        this.defaultValue = defaultValue;
    }
}
