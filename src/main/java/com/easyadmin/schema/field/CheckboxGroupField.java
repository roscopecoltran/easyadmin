package com.easyadmin.schema.field;

import com.easyadmin.schema.enums.Component;
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
public class CheckboxGroupField extends Field {
    private ChoiceItem[] choices;

    @Builder(toBuilder = true)
    public CheckboxGroupField(String name, String label, Object defaultValue, Boolean required, ChoiceItem[] choices) {
        super(name, Component.CheckboxGroup, label, defaultValue, required);
        this.choices = choices;
    }
}
