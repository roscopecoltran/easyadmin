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
    BooleanInput,
    CheckboxGroupInput,
    FileInput,
    FileField,
    ReferenceInput,
    SelectInput,
    ReferenceArrayInput,
    SelectArrayInput,
    NumberInput,
    RadioButtonGroupInput,
    ImageInput,
    ImageField,
    NullableBooleanInput,
    Show,
    SimpleShowLayout
} from 'admin-on-rest';
import RichTextInput from 'aor-rich-text-input';
import {required, minLength, maxLength, minValue, maxValue, number, regex, email, choices} from 'admin-on-rest';
import {CardActions} from 'material-ui/Card';
import FlatButton from 'material-ui/FlatButton';
import NavigationRefresh from 'material-ui/svg-icons/navigation/refresh';
import {ListButton, ShowButton, DeleteButton,Delete} from 'admin-on-rest';
const cardActionStyle = {
    zIndex: 2,
    display: 'inline-block',
    float: 'right',
};
export const CRUDList = (props) => (
    <List {...props}>
        <Datagrid>
            <TextField source="id" sortable={false}/>
            {props.options.fields.filter(field => field.showInList).map(renderField)}
            <ShowButton/>
            <DeleteButton/>
            <EditButton />
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

const EditActions = ({basePath, data, refresh}) => (
    <CardActions style={cardActionStyle}>
        <ShowButton basePath={basePath} record={data}/>
        <ListButton basePath={basePath}/>
        <DeleteButton basePath={basePath} record={data}/>
        <FlatButton primary label="Refresh" onClick={refresh} icon={<NavigationRefresh />}/>
        {/* Add your custom actions */}
        {/*<FlatButton primary label="Custom Action" onClick={customAction} />*/}
    </CardActions>
);

export const CRUDEdit = (props) => (
    <Edit actions={<EditActions />}{...props}>
        <SimpleForm>
            {props.options.fields.map(renderInput)}
        </SimpleForm>
    </Edit>
);

export const CRUDShow = (props) => (
    <Show {...props}>
        <SimpleShowLayout>
            {props.options.fields.map(renderField)}
        </SimpleShowLayout>
    </Show>
);

const CRUDDeleteTitle = (({ record, translate }) => <span>
    {'Delete'}&nbsp;
    {record && `${record.id} ${record.title}`}
</span>);

export const CRUDDelete = (props) => <Delete {...props} title={<CRUDDeleteTitle />} />;

const renderField = (field, index) => (
    <TextField key={index} source={field.name}/>
);

const renderInput = (field, index) => (
    field.component === 'Boolean' ? renderBooleanInput(field) :
        field.component === 'NullableBoolean' ? renderNullableBooleanInput(field) :
            field.component === 'Autocomplete' ? renderAutoCompleteInput(field) :
                field.component === 'CheckboxGroup' ? renderCheckboxGroupInput(field) :
                    field.component === 'Date' ? renderDateInput(field) :
                        field.component === 'File' ? renderFileInput(field) :
                            field.component === 'LongText' ? renderLongTextInput(field) :
                                field.component === 'Number' ? renderNumberInput(field) :
                                    field.component === 'RadioButtonGroup' ? renderRadioButtonGroupInput(field) :
                                        field.component === 'Reference' ? renderReferenceInput(field) :
                                            field.component === 'ReferenceArray' ? renderReferenceArrayInput(field) :
                                                field.component === 'RichText' ? renderRichTextInput(field) :
                                                    field.component === 'Select' ? renderSelectInput(field) :
                                                        field.component === 'SelectArray' ? renderSelectArrayInput(field) :
                                                            field.component === 'Image' ? renderImageInput(field) :
                                                                renderTextInput(field)
);

const renderAutoCompleteInput = (field, validators) => (
    <AutocompleteInput source={field.name} choices={field.choices} defaultValue={field.defaultValue}
                       validate={generateValidators(field)}/>
);

const renderBooleanInput = (field, validators) => (
    <BooleanInput label={field.label} source={field.name} defaultValue={field.defaultValue}/>
)

const renderNullableBooleanInput = (field, validators) => (
    <NullableBooleanInput label={field.label} source={field.name} defaultValue={field.defaultValue}
                          validate={generateValidators(field)}/>
)

const renderCheckboxGroupInput = (field, validators) => (
    <CheckboxGroupInput label={field.label} source={field.name} choices={field.choices}
                        defaultValue={field.defaultValue}/>
)

const renderFileInput = (field, validators) => (
    <FileInput source={field.name} label={field.label} accept={'application/' + field.type}>
        <FileField source="src" title="title"/>
    </FileInput>
)

const renderDateInput = (field, validators) => (
    <DateInput source={field.name} label={field.label} defaultValue={field.defaultValue}
               validate={generateValidators(field)}/>
)

const renderLongTextInput = (field, validators) => (
    <LongTextInput source={field.name} label={field.label} defaultValue={field.defaultValue}
                   validate={generateValidators(field)}/>
)

const renderNumberInput = (field, validators) => (
    <NumberInput source={field.name} label={field.label} defaultValue={field.defaultValue}
                 validate={generateValidators(field)}/>
)

const renderRadioButtonGroupInput = (field, validators) => (
    <RadioButtonGroupInput source={field.name} choices={field.choices} defaultValue={field.defaultValue}
                           validate={generateValidators(field)}/>
)

const renderTextInput = (field) => (
    <TextInput label={field.label} source={field.name} type={field.type} defaultValue={field.defaultValue}
               validate={generateValidators(field)}/>
)

const renderReferenceInput = (field, validators) => (
    <ReferenceInput label={field.label} source={field.name} reference={field.reference}
                    defaultValue={field.defaultValue} validate={generateValidators(field)} allowEmpty>
        <SelectInput source={field.referenceOptionText}/>
    </ReferenceInput>
)

const renderReferenceArrayInput = (field, validators) => (
    <ReferenceArrayInput source={field.name} reference={field.reference} defaultValue={field.defaultValue}
                         validate={generateValidators(field)} allowEmpty>
        <SelectArrayInput source={field.referenceOptionText}/>
    </ReferenceArrayInput>
)
const renderRichTextInput = (field, validators) => (
    <RichTextInput source={field.name} defaultValue={field.defaultValue} validate={generateValidators(field)}/>
)
const renderSelectInput = (field, validators) => (
    <SelectInput source={field.name} choices={field.choices} defaultValue={field.defaultValue}
                 validate={generateValidators(field)}/>
)

const renderSelectArrayInput = (field, validators) => (
    <SelectArrayInput label={field.label} source={field.name} choices={field.choices}
                      defaultValue={field.defaultValue} validate={generateValidators(field)}/>
)

const renderImageInput = (field, validators) => (
    <ImageInput source={field.name} label={field.label} accept="image/*" validate={generateValidators(field)}>
        <ImageField source="src" title="title"/>
    </ImageInput>
)

const generateValidators = (field) => {
    const validators = [];
    if (field.required) validators.push(required);
    if (field.type === 'email' && field.component === 'Text') validators.push(email);
    if (field.component === 'Number') {
        validators.push(number);
        if (field.minValue) {
            validators.push(minValue(field.minValue));
        }
        if (field.maxValue) {
            validators.push(maxValue(field.maxValue));
        }
    }
    if (field.minLength) validators.push(minLength(field.minLength));
    if (field.maxLength) validators.push(maxLength(field.maxLength));
    return validators;
}