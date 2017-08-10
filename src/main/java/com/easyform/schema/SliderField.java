package com.easyform.schema;

import com.easyform.schema.enums.Component;
import lombok.Builder;
import lombok.Data;

/**
 * Created by gongxinyi on 2017-08-10.
 */
public class SliderField extends Field {
    private Integer defaultValue;
    private Integer min;
    private Integer max;
    private Integer step;

    @Builder(toBuilder = true)
    public SliderField(String name, Component component, Integer defaultValue, Integer min, Integer max, Integer step) {
        super(name, component);
        this.defaultValue = defaultValue;
        this.min = min;
        this.max = max;
        this.step = step;
    }
}
