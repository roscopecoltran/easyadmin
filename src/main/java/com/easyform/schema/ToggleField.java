package com.easyform.schema;

import com.easyform.schema.enums.Component;
import com.easyform.schema.enums.LabelPosition;
import lombok.Builder;
import lombok.Data;

/**
 * Created by gongxinyi on 2017-08-10.
 */
public class ToggleField extends Field {
    private String label;
    private LabelPosition labelPosition;
    @Builder(toBuilder = true)
    private ToggleField(String name, Component component, String label, LabelPosition labelPosition) {
        super(name, component);
        this.label = label;
        this.labelPosition = labelPosition;
    }
}
