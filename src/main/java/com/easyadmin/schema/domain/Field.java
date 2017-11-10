package com.easyadmin.schema.domain;

import com.easyadmin.schema.enums.Component;
import com.easyadmin.schema.enums.InputType;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.mongodb.morphia.annotations.Id;

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
 *
 * @author gongxinyi
 * @date 2017-08-10
 */
@Data
@ToString
@EqualsAndHashCode
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@org.mongodb.morphia.annotations.Entity(value = "_fields", noClassnameStored = true)
public class Field implements Serializable {
    protected String dataSourceId;
    protected String eid;
    @Id
    protected String id;
    protected String name;
    protected Component component;
    protected String label;
    protected String defaultValue;
    protected Boolean required;

    /**
     * array domain Autocomplete、CheckboxGroup、RadioButtonGroup、SelectArray、Select
     */
    private ChoiceItem[] choices;

    /**
     * input type
     */
    private InputType inputType;
    private Integer maxLength;

    /**
     * reference domain
     */
    private String reference;
    private String referenceOptionText;

    /**
     * number domain
     */
    private String minValue;
    private String maxValue;

    /**
     * 是否是主键
     */
    private Boolean isPartOfPrimaryKey;

    /**
     * 是否自增字段
     */
    private Boolean isAutoIncremented;

    public boolean isReference() {
        return Component.Reference.equals(component) || Component.ReferenceArray.equals(component);
    }

    private Boolean showInList;
    private Boolean showInFilter;
    private Boolean showInCreate;
    private Boolean showInEdit;
    private Boolean showInShow;
}
