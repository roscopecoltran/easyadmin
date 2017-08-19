import React from 'react';
import {
    List,
    Datagrid,
    Create,
    Edit,
    SimpleForm,
    AutocompleteInput,
    DisabledInput,
    TextInput,
    DateInput,
    LongTextInput,
    EditButton,
    BooleanInput,
    CheckboxGroupInput,
    FileInput,
    ReferenceInput,
    SelectInput,
    ReferenceArrayInput,
    SelectArrayInput,
    NumberInput,
    RadioButtonGroupInput,
    ImageInput,
    NullableBooleanInput,
    Show,
    SimpleShowLayout,
    TextField,
    FileField,
    DateField,
    ImageField,
    BooleanField,
    EmailField,
    UrlField,
    NumberField,
    ReferenceField,
    ReferenceManyField,
    SingleFieldList,
    ChipField,
    ReferenceArrayField,
    RichTextField,
    SelectField,
    FunctionField
} from 'admin-on-rest';
import RichTextInput from 'aor-rich-text-input';
import {required, minLength, maxLength, minValue, maxValue, number, regex, email, choices} from 'admin-on-rest';
import {CardActions} from 'material-ui/Card';
import FlatButton from 'material-ui/FlatButton';
import NavigationRefresh from 'material-ui/svg-icons/navigation/refresh';
import {ListButton, ShowButton, DeleteButton, Delete,Filter} from 'admin-on-rest';
import schemas from './schemas';
const cardActionStyle = {
    zIndex: 2,
    display: 'inline-block',
    float: 'right',
};
const filterComponent=['File','Image'];
const CRUDFilter = (props) => (
    <Filter {...props}>
            {
                /*<TextInput label="Search" source="q" alwaysOn />*/

                schemas.find(resource => (resource.name=== props.resource)).fields.filter(field=> !filterComponent.includes(field.component)).map(renderInput)}
            }
    </Filter>
);

export const CRUDList = (props) => (
    <List {...props} >
        <Datagrid>
            <TextField source="id" sortable={false}/>
            {props.options.fields.filter(field => field.showInList).map(renderField)}
            <ShowButton/>
            <DeleteButton/>
            <EditButton/>
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
        <FlatButton primary label="Refresh" onClick={refresh} icon={<NavigationRefresh/>}/>
        {/* Add your custom actions */}
        {/*<FlatButton primary label="Custom Action" onClick={customAction} />*/}
    </CardActions>
);

export const CRUDEdit = (props) => (
    <Edit actions={<EditActions/>}{...props}>
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

const CRUDDeleteTitle = ({record, translate}) => <span>
    {'Delete'}&nbsp;
    {record && `${record.id}`}
</span>;

export const CRUDDelete = (props) => <Delete {...props} title={<CRUDDeleteTitle/>}/>;

const renderField = (field, index) => (
    field.component === 'Boolean' ? renderBooleanField(field) :
        field.component === 'NullableBoolean' ? renderBooleanField(field) :
            field.component === 'Autocomplete' ? renderTextField(field) :
                field.component === 'CheckboxGroup' ? renderTextField(field) :
                    field.component === 'Date' ? renderDateField(field) :
                        field.component === 'File' ? renderFileField(field) :
                            field.component === 'LongText' ? renderTextField(field) :
                                field.component === 'Number' ? renderNumberField(field) :
                                    field.component === 'RadioButtonGroup' ? renderTextField(field) :
                                        field.component === 'Reference' ? renderReferenceField(field) :
                                            field.component === 'ReferenceArray' ? renderReferenceArrayField(field) :
                                                field.component === 'RichText' ? renderRichTextField(field) :
                                                    field.component === 'Select' ? renderSelectField(field) :
                                                        field.component === 'SelectArray' ? renderTextField(field) :
                                                            field.component === 'Image' ? renderImageField(field) :
                                                                renderTextField(field)
);

const renderBooleanField = (field) => (
    <BooleanField source={field.name}/>
)
const renderReferenceField = (field) => (
    <ReferenceField label={field.label} source={field.name} reference={field.reference}>
        <TextField source={field.referenceOptionText}/>
    </ReferenceField>
)
const renderReferenceArrayField = (field) => (
    <ReferenceArrayField label={field.label} reference={field.reference} source={field.name}>
        <SingleFieldList>
            <ChipField source={field.referenceOptionText}/>
        </SingleFieldList>
    </ReferenceArrayField>
)

const renderSelectField = (field) => (
    <SelectField source={field.name} choices={field.choices}/>
)

const renderTextField = (field) => (
    field.type === 'email' ? <EmailField source={field.name}/> :
        field.type === 'url' ? <UrlField source={field.name}/> :
            <TextField source={field.name}/>
)
const renderRichTextField = (field) => (
    <RichTextField source={field.name} stripTags/>
)
const renderImageField = (field) => (
    <ImageField source={field.name} title="title"/>
)

const renderDateField = (field) => (
    <DateField source={field.name}/>
)

const renderFileField = (field) => (
    <FileField source="url" title="title" />
)

const renderNumberField = (field) => (
    <NumberField source={field.name}/>
)


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