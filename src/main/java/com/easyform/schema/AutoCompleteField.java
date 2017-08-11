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
    private DataSourceItem[] dataSource;
    private String floatingLabelText;

    @Builder(toBuilder = true)
    private AutoCompleteField(String name, DataSourceItem[] dataSource, String floatingLabelText) {
        super(name, Component.AutoComplete);
        this.dataSource = dataSource;
        this.floatingLabelText = floatingLabelText;
    }
}
