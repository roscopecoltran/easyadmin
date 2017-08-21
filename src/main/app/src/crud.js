import React from 'react';
import {
    List,
    Datagrid,
    Create,
    Edit,
    SimpleForm,
    AutocompleteInput,
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
    SingleFieldList,
    ChipField,
    ReferenceArrayField,
    RichTextField,
    SelectField,
} from 'admin-on-rest';
import RichTextInput from 'aor-rich-text-input';
import {required, minLength, maxLength, minValue, maxValue, number, email} from 'admin-on-rest';
import {CardActions} from 'material-ui/Card';
import FlatButton from 'material-ui/FlatButton';
import NavigationRefresh from 'material-ui/svg-icons/navigation/refresh';
import {ListButton, ShowButton, DeleteButton, Delete,Filter} from 'admin-on-rest';
import schemas from './schemas';
import SelectArrayField from './SelectArrayField';
const cardActionStyle = {
    zIndex: 2,
    display: 'inline-block',
    float: 'right',
};
const filterComponent=['File','Image'];
const CRUDFilter = (props) => (
    <Filter {...props}>
        <TextInput label="Search" source="q" alwaysOn/>
        {schemas.find(resource => (resource.name=== props.resource)).fields.filter(field=> !filterComponent.includes(field.component)).map(renderInput)}
    </Filter>
);

export const CRUDList = (props) => (
    <List {...props} filters={<CRUDFilter/>} title={props.options.label}>
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
    <Create {...props} title={props.options.label}>
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
    <Edit actions={<EditActions/>}{...props} title={props.options.label}>
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
        field.component === 'Boolean' ? renderBooleanField(field,index) :
            field.component === 'NullableBoolean' ? renderBooleanField(field,index) :
                field.component === 'Autocomplete' ? renderSelectField(field,index) :
                    field.component === 'CheckboxGroup' ? renderSelectArrayField(field,index) :
                        field.component === 'Date' ? renderDateField(field,index) :
                            field.component === 'File' ? renderFileField(field,index) :
                                field.component === 'LongText' ? renderTextField(field,index) :
                                    field.component === 'Number' ? renderNumberField(field,index) :
                                        field.component === 'RadioButtonGroup' ? renderSelectField(field,index) :
                                            field.component === 'Reference' ? renderReferenceField(field,index) :
                                                field.component === 'ReferenceArray' ? renderReferenceArrayField(field,index) :
                                                    field.component === 'RichText' ? renderRichTextField(field,index) :
                                                        field.component === 'Select' ? renderSelectField(field,index) :
                                                            field.component === 'SelectArray' ? renderSelectArrayField(field,index) :
                                                                field.component === 'Image' ? renderImageField(field,index) :
                                                                    renderTextField(field,index)

);

const renderBooleanField = (field,index) => (
    <BooleanField key={index}label={field.label} source={field.name}/>
)
const renderReferenceField = (field,index) => (
    <ReferenceField key={index} label={field.label} source={field.name} reference={field.reference}>
        <TextField source={field.referenceOptionText}/>
    </ReferenceField>
)

const renderSelectArrayField = (field,index) =>(
    <SelectArrayField  key={index}label={field.label} source={field.name} choices={field.choices} optionText='name' optionValue='id'/>
)
const renderReferenceArrayField = (field,index) => (
    <ReferenceArrayField  key={index}label={field.label} reference={field.reference} source={field.name}>
        <SingleFieldList>
            <ChipField source={field.referenceOptionText}/>
        </SingleFieldList>
    </ReferenceArrayField>
)

const renderSelectField = (field,index) => (
    <SelectField  key={index}source={field.name} choices={field.choices}/>
)

const renderTextField = (field,index) => (
    field.type === 'email' ? <EmailField key={index} source={field.name}/> :
        field.type === 'url' ? <UrlField key={index} source={field.name}/> :
            <TextField key={index} source={field.name}/>
)
const renderRichTextField = (field,index) => (
    <RichTextField  key={index} label={field.label} source={field.name} stripTags/>
)
const renderImageField = (field,index) => (
    <ImageField  key={index} source={field.name} title="title"/>
)

const renderDateField = (field,index) => (
    <DateField  key={index} source={field.name}/>
)

const renderFileField = (field,index) => (
    <FileField  key={index} source="url" title="title" />
)

const renderNumberField = (field,index) => (
    <NumberField  key={index} source={field.name}/>
)


const renderInput = (field, index) => (
    field.component === 'Boolean' ? renderBooleanInput(field,index) :
        field.component === 'NullableBoolean' ? renderNullableBooleanInput(field,index) :
            field.component === 'Autocomplete' ? renderAutoCompleteInput(field,index) :
                field.component === 'CheckboxGroup' ? renderCheckboxGroupInput(field,index) :
                    field.component === 'Date' ? renderDateInput(field,index) :
                        field.component === 'File' ? renderFileInput(field,index) :
                            field.component === 'LongText' ? renderLongTextInput(field,index) :
                                field.component === 'Number' ? renderNumberInput(field,index) :
                                    field.component === 'RadioButtonGroup' ? renderRadioButtonGroupInput(field,index) :
                                        field.component === 'Reference' ? renderReferenceInput(field,index) :
                                            field.component === 'ReferenceArray' ? renderReferenceArrayInput(field,index) :
                                                field.component === 'RichText' ? renderRichTextInput(field,index) :
                                                    field.component === 'Select' ? renderSelectInput(field,index) :
                                                        field.component === 'SelectArray' ? renderSelectArrayInput(field,index) :
                                                            field.component === 'Image' ? renderImageInput(field,index) :
                                                                renderTextInput(field,index)
);

const renderAutoCompleteInput = (field,index) => (
    <AutocompleteInput key={index} source={field.name} choices={field.choices} defaultValue={field.defaultValue}
                       validate={generateValidators(field)}/>
);

const renderBooleanInput = (field,index) => (
    <BooleanInput key={index} label={field.label} source={field.name} defaultValue={field.defaultValue}/>
)

const renderNullableBooleanInput = (field, index) => (
    <NullableBooleanInput key={index} label={field.label} source={field.name} defaultValue={field.defaultValue}
                          validate={generateValidators(field)}/>
)

const renderCheckboxGroupInput = (field, index) => (
    <CheckboxGroupInput key={index} label={field.label} source={field.name} choices={field.choices}
                        defaultValue={field.defaultValue}/>
)

const renderFileInput = (field, index) => (
    <FileInput key={index} source={field.name} label={field.label} accept={'application/' + field.type}>
        <FileField source="src" title="title"/>
    </FileInput>
)

const renderDateInput = (field, index) => (
    <DateInput key={index} source={field.name} label={field.label} defaultValue={field.defaultValue}
               validate={generateValidators(field)}/>
)

const renderLongTextInput = (field, index) => (
    <LongTextInput key={index} source={field.name} label={field.label} defaultValue={field.defaultValue}
                   validate={generateValidators(field)}/>
)

const renderNumberInput = (field, index) => (
    <NumberInput  key={index} source={field.name} label={field.label} defaultValue={field.defaultValue}
                 validate={generateValidators(field)}/>
)

const renderRadioButtonGroupInput = (field, index) => (
    <RadioButtonGroupInput key={index} source={field.name} choices={field.choices} defaultValue={field.defaultValue}
                           validate={generateValidators(field)}/>
)

const renderTextInput = (field,index) => (
    <TextInput key={index} label={field.label} source={field.name} type={field.type} defaultValue={field.defaultValue}
               validate={generateValidators(field)}/>
)

const renderReferenceInput = (field, index) => (
    <ReferenceInput key={index} label={field.label} source={field.name} reference={field.reference}
                    defaultValue={field.defaultValue} validate={generateValidators(field)} allowEmpty>
        <SelectInput source={field.referenceOptionText}/>
    </ReferenceInput>
)

const renderReferenceArrayInput = (field, index) => (
    <ReferenceArrayInput key={index} source={field.name} reference={field.reference} defaultValue={field.defaultValue}
                         validate={generateValidators(field)} allowEmpty>
        <SelectArrayInput source={field.referenceOptionText}/>
    </ReferenceArrayInput>
)
const renderRichTextInput = (field, index) => (
    <RichTextInput  key={index} label={field.label} source={field.name} defaultValue={field.defaultValue} validate={generateValidators(field)}/>
)
const renderSelectInput = (field, index) => (
    <SelectInput  key={index} source={field.name} choices={field.choices} defaultValue={field.defaultValue}
                 validate={generateValidators(field)}/>
)

const renderSelectArrayInput = (field, index) => (
    <SelectArrayInput key={index} label={field.label} source={field.name} choices={field.choices}
                      defaultValue={field.defaultValue} validate={generateValidators(field)}/>
)

const renderImageInput = (field, index) => (
    <ImageInput key={index} source={field.name} label={field.label} accept="image/*" validate={generateValidators(field)}>
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