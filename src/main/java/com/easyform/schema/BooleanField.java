package com.easyform.schema;

import com.easyform.schema.enums.Component;
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
    private String label;

    @Builder(toBuilder = true)
    public BooleanField(String name, String label) {
        super(name, Component.Boolean);
        this.label = label;
    }

    protected BooleanField(String name,Component component, String label) {
        super(name, component);
        this.label = label;
    }
}
