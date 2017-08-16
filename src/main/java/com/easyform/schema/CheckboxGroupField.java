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
public class CheckboxGroupField extends Field{
    private ChoiceItem[] choices;
    @Builder(toBuilder = true)
    private CheckboxGroupField(String name, ChoiceItem[] choices) {
        super(name, Component.Autocomplete);
        this.choices=choices;
    }
}
