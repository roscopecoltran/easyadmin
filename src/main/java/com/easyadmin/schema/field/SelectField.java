package com.easyadmin.schema.field;

import com.easyadmin.schema.enums.Component;
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
public class SelectField extends Field {

    private ChoiceItem[] choices;

    @Builder
    private SelectField(String name, String label, Object defaultValue, Boolean required, ChoiceItem[] choices) {
        super(name, Component.Select, label, defaultValue, required);
        this.choices = choices;
    }
}
