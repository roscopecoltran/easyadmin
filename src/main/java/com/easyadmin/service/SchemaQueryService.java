package com.easyadmin.service;

import com.easyadmin.schema.enums.Component;
import com.easyadmin.schema.enums.InputType;
import com.easyadmin.schema.field.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gongxinyi on 2017-08-10.
 */
@org.springframework.stereotype.Component
public class SchemaQueryService {

    public List<Field> list() {
        List<Field> fields = new ArrayList<>();
        fields.add(BooleanField.builder()
                .name(Component.Boolean.toString())
                .label(Component.Boolean.toString())
                .defaultValue(true)
                .build());
        fields.add(NullableBooleanField.builder()
                .name(Component.NullableBoolean.toString())
                .label(Component.NullableBoolean.toString())
                .defaultValue(true)
                .build());
        fields.add(DateField.builder()
                .name(Component.Date.toString())
                .label(Component.Date.toString())
                .defaultValue("2017-08-17")
                .build());
        fields.add(FileField.builder()
                .name(Component.File.toString())
                .label(Component.File.toString())
                .fileType("*")
                .build());
        fields.add(ImageField.builder()
                .name(Component.Image.toString())
                .label(Component.Image.toString())
                .build());
        fields.add(CheckboxGroupField.builder()
                .name(Component.CheckboxGroup.toString())
                .label(Component.CheckboxGroup.toString())
                .choices(new ChoiceItem[]{
                        ChoiceItem.builder().id("programming").name("Programming").build(),
                        ChoiceItem.builder().id("lifestyle").name("Lifestyle").build(),
                        ChoiceItem.builder().id("photography").name("Photography'").build()
                })
                .defaultValue("programming")
                .build());
        fields.add(AutoCompleteField.builder()
                .name(Component.Autocomplete.toString())
                .label(Component.Autocomplete.toString())
                .choices(new ChoiceItem[]{
                        ChoiceItem.builder().id("programming").name("Programming").build(),
                        ChoiceItem.builder().id("lifestyle").name("Lifestyle").build(),
                        ChoiceItem.builder().id("photography").name("Photography'").build()
                })
                .defaultValue("programming")
                .build());

        fields.add(LongTextField.builder()
                .name(Component.LongText.toString())
                .label(Component.LongText.toString())
                .defaultValue("1")
                .build());
        fields.add(NumberField.builder()
                .name(Component.Number.toString())
                .label(Component.Number.toString())
                .defaultValue(1)
                .build());
        fields.add(RadioButtonGroupField.builder()
                .name(Component.RadioButtonGroup.toString())
                .label(Component.RadioButtonGroup.toString())
                .choices(new ChoiceItem[]{
                        ChoiceItem.builder().id("programming").name("Programming").build(),
                        ChoiceItem.builder().id("lifestyle").name("Lifestyle").build(),
                        ChoiceItem.builder().id("photography").name("Photography'").build()
                })
                .defaultValue("programming")
                .build());

        fields.add(TextField.builder()
                .name(Component.Text.toString())
                .label(Component.Text.toString())
                .type(InputType.email)
                .required(true)
                .maxLength(10)
                .defaultValue("programming")
                .build());
        fields.add(ReferenceField.builder()
                .name(Component.Reference.toString())
                .label(Component.Reference.toString())
                .reference("users")
                .referenceOptionText("name")
                .defaultValue(1)
                .build());
        fields.add(ReferenceArrayField.builder()
                .name(Component.ReferenceArray.toString())
                .label(Component.ReferenceArray.toString())
                .reference("users")
                .referenceOptionText("name")
                .defaultValue(new Integer[]{1, 2})
                .build());
        fields.add(RichTextField.builder()
                .name(Component.RichText.toString())
                .label(Component.RichText.toString())
                .defaultValue(true)
                .build());
        fields.add(SelectField.builder()
                .name(Component.Select.toString())
                .label(Component.Select.toString())
                .choices(new ChoiceItem[]{
                        ChoiceItem.builder().id("programming").name("Programming").build(),
                        ChoiceItem.builder().id("lifestyle").name("Lifestyle").build(),
                        ChoiceItem.builder().id("photography").name("Photography'").build()
                })
                .defaultValue("lifestyle")
                .build());
        fields.add(SelectArrayField.builder()
                .name(Component.SelectArray.toString())
                .label(Component.SelectArray.toString())
                .choices(new ChoiceItem[]{
                        ChoiceItem.builder().id("programming").name("Programming").build(),
                        ChoiceItem.builder().id("lifestyle").name("Lifestyle").build(),
                        ChoiceItem.builder().id("photography").name("Photography'").build()
                })
                .defaultValue("lifestyle")
                .build());
        return fields;
    }
}
