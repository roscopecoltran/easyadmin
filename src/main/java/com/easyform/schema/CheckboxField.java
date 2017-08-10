package com.easyform.schema;

import com.easyform.schema.enums.Component;
import lombok.Builder;
import lombok.Data;

/**
 * Created by gongxinyi on 2017-08-10.
 */
public class CheckboxField extends Field {
    private String label;

    @Builder(toBuilder = true)
    public CheckboxField(String name, Component component, String label) {
        super(name, component);
        this.label = label;
    }
}
