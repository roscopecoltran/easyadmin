import React from 'react';
import {
    List,
    Datagrid,
    TextField,
    Create,
    Edit,
    SimpleForm,
    AutocompleteInput,
    DisabledInput,
    TextInput,
    DateInput,
    LongTextInput,
    ReferenceManyField,
    DateField,
    EditButton,
    RichTextInput,
    BooleanInput,
    CheckboxGroupInput,
    FileInput,
    FileField
} from 'admin-on-rest';

export const CRUDList = (props) => (
    <List {...props}>
        <Datagrid>
            {props.options.fields.map(renderField)}
        </Datagrid>
    </List>
);

export const CRUDCreate = (props) => (
    <Create {...props}>
        <SimpleForm>
            {props.options.fields.map(renderInput)}
        </SimpleForm>
    </Create>
);

const renderField = (field, index) => (
    <TextField key={index} source={field.name}/>
);

const renderInput = (field, index) => (
    field.component === 'Boolean' ? renderBooleanInput(field) :
        field.component === 'Autocomplete' ? renderAutoCompleteInput(field) :
            field.component === 'CheckboxGroup' ? renderCheckboxGroupInput(field) :
                field.component === 'Date' ? renderDateInput(field) :
                    field.component === 'File' ? renderFileInput(field) :
                renderTextInput(field)
);

const renderAutoCompleteInput = (field) => (
    <AutocompleteInput source={field.name} choices={field.choices}/>
);

const renderBooleanInput = (field) => (
    <BooleanInput label={field.label} source={field.name}/>
)
const renderCheckboxGroupInput = (field) => (
    <CheckboxGroupInput source={field.name} choices={field.choices}/>
)

const renderFileInput = (field) => (
    <FileInput source={field.name} label={field.label} accept="*">
        <FileField source="src" title="title" />
    </FileInput>
)

const renderDateInput = (field) => (
    <DateInput source={field.name} label={field.label}/>
)

const renderLongTextInput = (field) => (
    <LongTextInput source={field.name} label={field.label}/>
)

const renderNumberInput = (field) => (
        <NumberInput source={field.name} label={field.label}/>
)

const RadioButtonGroupInput = (field) => (
        <NumberInput source={field.name} choices={field.choices}/>
)

const renderTextInput = (field) => (
    <BooleanInput label={field.label} source={field.name}/>
)

