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
public class SelectArrayField extends Field {

    private ChoiceItem[] choices;

    @Builder
    private SelectArrayField(String name, String label, Object defaultValue, Boolean required, ChoiceItem[] choices) {
        super(name, Component.SelectArray, label, defaultValue, required);
        this.choices = choices;
    }
}
