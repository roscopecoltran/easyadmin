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
public class AutoCompleteField extends Field {
    private ChoiceItem[] choices;

    @Builder(toBuilder = true)
    public AutoCompleteField(String name, String label, Object defaultValue, Boolean required, ChoiceItem[] choices) {
        super(name, Component.Autocomplete, label, defaultValue, required);
        this.choices = choices;
    }
}
