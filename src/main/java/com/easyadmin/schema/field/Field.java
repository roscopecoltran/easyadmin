package com.easyadmin.schema.field;

import com.easyadmin.schema.enums.Component;
import com.easyadmin.schema.enums.InputType;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

/**
 * parent class for html input component
 * <p>
 * has some commmon props , ex:label,defaultValue,required
 * <p>
 * Component contains Text,Boolean and ...
 * <p>
 * the props of fields in list page will define in inherit class
 * <p>
 * Created by gongxinyi on 2017-08-10.
 */
@Data
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class Field implements Serializable {
    protected String entity;
    protected String id;
    protected String name;
    protected Component component;
    protected String label;
    protected Object defaultValue;
    protected Boolean required;

    /**
     * array field Autocomplete、CheckboxGroup、RadioButtonGroup、SelectArray、Select
     */
    private ChoiceItem[] choices;

    /**
     * input type
     */
    private InputType inputType;
    private Integer maxLength;

    /**
     * reference field
     */
    private String reference;
    private String referenceOptionText;

    /**
     * number field
     */
    private String minValue;
    private String maxValue;

    private Boolean showInList;
    public Field(String name, Component component, String label, Object defaultValue, Boolean required) {
        this.name = name;
        this.component = component;
        this.label = label;
        this.defaultValue = defaultValue;
        this.required = required;
    }

    public boolean isReference(){
        return Component.Reference.equals(component) || Component.ReferenceArray.equals(component);
    }
}
