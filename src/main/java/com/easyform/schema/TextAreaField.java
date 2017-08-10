package com.easyform.schema;

import com.easyform.schema.enums.Component;
import lombok.Builder;
import lombok.Data;

/**
 * Created by gongxinyi on 2017-08-10.
 */
public class TextAreaField extends Field{
    private String hintText;
    private String floatingLabelText;
    private Integer rows;

    @Builder(toBuilder = true)
    private TextAreaField(String name, Component component, String hintText, String floatingLabelText, Integer rows) {
        super(name, component);
        this.hintText = hintText;
        this.floatingLabelText = floatingLabelText;
        this.rows = rows;
    }
}
