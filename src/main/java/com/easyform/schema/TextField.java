package com.easyform.schema;

import com.easyform.schema.enums.Component;
import lombok.*;

/**
 * Created by gongxinyi on 2017-08-10.
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class TextField extends Field{
    private String floatingLabelText;
    @Builder(toBuilder = true)
    private TextField(String name, Component component, String floatingLabelText) {
        super(name,component);
        this.floatingLabelText=floatingLabelText;
    }
}
