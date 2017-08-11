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
public class TextAreaField extends Field {
    private String hintText;
    private String floatingLabelText;
    private Integer rows;
    private Object defaultValue;
    @Builder(toBuilder = true)
    private TextAreaField(String name, String hintText, String floatingLabelText, Integer rows, Object defaultValue) {
        super(name, Component.TextArea);
        this.hintText = hintText;
        this.floatingLabelText = floatingLabelText;
        this.rows = rows;
        this.defaultValue = defaultValue;
    }
}
