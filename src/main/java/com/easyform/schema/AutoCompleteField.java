package com.easyform.schema;

import com.easyform.schema.enums.Component;
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
    private AutoCompleteField(String name, ChoiceItem[] choices) {
        super(name, Component.Autocomplete);
        this.choices=choices;
    }
}
