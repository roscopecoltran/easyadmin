import React from "react";
import {
    AutocompleteInput,
    BooleanField,
    BooleanInput,
    CheckboxGroupInput,
    ChipField,
    Create,
    Datagrid,
    DateField,
    DateInput,
    Delete,
    DeleteButton,
    Edit,
    EditButton,
    email,
    EmailField,
    FileField,
    FileInput,
    Filter,
    ImageField,
    ImageInput,
    List,
    ListButton,
    LongTextInput,
    maxLength,
    maxValue,
    minLength,
    minValue,
    NullableBooleanInput,
    number,
    NumberField,
    NumberInput,
    RadioButtonGroupInput,
    ReferenceArrayField,
    ReferenceArrayInput,
    ReferenceField,
    ReferenceInput,
    required,
    RichTextField,
    SelectArrayInput,
    SelectField,
    SelectInput,
    Show,
    ShowButton,
    SimpleForm,
    SimpleShowLayout,
    SingleFieldList,
    TextField,
    TextInput,
    UrlField
} from "admin-on-rest";
import RichTextInput from "aor-rich-text-input";
import {CardActions} from "material-ui/Card";
import FlatButton from "material-ui/FlatButton";
import NavigationRefresh from "material-ui/svg-icons/navigation/refresh";
import SelectArrayField from "./SelectArrayField";
import DateTimeInput from "./DateTimeInput";
const cardActionStyle = {
    zIndex: 2,
    display: 'inline-block',
    float: 'right',
};
const filterComponent = ['File', 'Image'];

/**
 * filter
 * contains all the domain and the full text search input
 * @param props
 * @constructor
 */
const CRUDFilter = (props) => (
    <Filter {...props}>
        {/*全文搜索先注释掉，对于有全文索引的字段，才显示Search框 [TODO]*/}
        {/*<TextInput label="Search" source="q" alwaysOn/>*/}
        {props.options.fields.filter(field => !filterComponent.includes(field.component) && field.showInFilter).map(renderFilter)}
    </Filter>
);

const renderFilter = (field) => (
    field.component === 'Boolean' ? renderBooleanInput(field) :
        field.component === 'NullableBoolean' ? renderNullableBooleanInput(field) :
            field.component === 'Autocomplete' ? renderAutoCompleteInput(field) :
                field.component === 'CheckboxGroup' ? renderCheckboxGroupInput(field) :
                    field.component === 'Date' ? renderDateTimeFilter(field) :
                        field.component === 'LongText' ? renderLongTextInput(field) :
                            field.component === 'Number' ? renderNumberFilter(field) :
                                field.component === 'RadioButtonGroup' ? renderRadioButtonGroupInput(field) :
                                    field.component === 'Reference' ? renderReferenceInput(field) :
                                        field.component === 'ReferenceArray' ? renderReferenceArrayInput(field) :
                                            field.component === 'RichText' ? renderRichTextInput(field) :
                                                field.component === 'Select' ? renderSelectInput(field) :
                                                    field.component === 'SelectArray' ? renderSelectArrayInput(field) :
                                                        renderTextInput(field)
);

const renderDateTimeFilter = (field) => (
    [<DateTimeInput key={field.id + '_gte'} label={field.label + " 开始"} source={field.name + '_gte'}
                    options={{locale: 'zh-hans'}}/>,
        <DateTimeInput key={field.id + '_lte'} label={field.label + " 截止"} source={field.name + '_lte'}
                       options={{locale: 'zh-hans'}}/>]
)


const renderNumberFilter = (field) => (
    [<NumberInput key={field.id + '_gte'} source={field.name + '_gte'} label={field.label + ' 大于等于'}
                  defaultValue={field.defaultValue}
                  validate={[number]}/>,
        <NumberInput key={field.id + '_lte'} source={field.name + '_lte'} label={field.label + ' 小于等于'}
                     defaultValue={field.defaultValue}
                     validate={[number]}/>]
)

/**
 * render a record's enabled actions
 *
 * ex: c contains create action
 *     r contains show action
 *     u contains edit action
 *     d contains delete action
 * @param props
 * @returns {Array}
 */
const renderRecordAction = (props) => {
    const actions = [];
    // actions.push(<ShowButton key="1"/>);
    // if (props.hasDelete) {
    //     actions.push(<DeleteButton key="2"/>);
    // }
    if (props.hasEdit) {
        actions.push(<EditButton key="3"/>);
    }
    return actions;
}

/**
 * crud list contains all the fields
 * @param props
 * @constructor
 */
export const CRUDList = (props) => (
    <List {...props} filters={<CRUDFilter {...props}/>} title={props.options.label}>
        <Datagrid>
            {renderRecordAction(props)}
            <TextField source="id" sortable={false}/>
            {props.options.fields.filter(field => field.showInList && "id" !== field.name).map(renderField)}
        </Datagrid>
    </List>
);

/**
 * create page with simple form layout
 *
 * contains validators
 * @param props
 * @constructor
 */
export const CRUDCreate = (props) => (
    <Create {...props} title={props.options.label}>
        <SimpleForm redirect={props.options.redirect}>
            {props.options.fields.filter(field => field.showInCreate).map(renderInput)}
        </SimpleForm>
    </Create>
);

/**
 * edit action
 *
 * render the delete action from schema define
 *
 * TODO render the custom actions
 * @param basePath
 * @param data
 * @param refresh
 * @param options
 * @returns {XML}
 * @constructor
 */
const EditActions = ({basePath, data, refresh, options}) => {
    return <CardActions style={cardActionStyle}>
        <ShowButton basePath={basePath} record={data}/>
        <ListButton basePath={basePath}/>
        {options.hasDelete ? <DeleteButton basePath={basePath} record={data}/> : null}
        <FlatButton primary label="Refresh" onClick={refresh} icon={<NavigationRefresh/>}/>
        {/* Add your custom actions */}
        {/*<FlatButton primary label="Custom Action" onClick={customAction} />*/}
    </CardActions>
};

/**
 * edit page with the record value
 * @param props
 * @constructor
 */
export const CRUDEdit = (props) => (
    <Edit actions={<EditActions options={props}/>} {...props} title={props.options.label}>
        <SimpleForm redirect={props.options.redirect}>
            {props.options.fields.filter(field => field.showInEdit).map(renderInput)}
        </SimpleForm>
    </Edit>
);

/**
 * show page
 * @param props
 * @constructor
 */
export const CRUDShow = (props) => (
    <Show {...props} title={props.options.label}>
        <SimpleShowLayout>
            {props.options.fields.filter(field => field.showInShow).map(renderField)}
        </SimpleShowLayout>
    </Show>
);

/**
 * delete page content
 * @param record
 * @param translate
 * @constructor
 */
const CRUDDeleteTitle = ({record, translate}) => <span>
    {'Delete'}&nbsp;
    {record && `${record.id}`}
</span>;

/**
 * delete page
 * @param props
 * @constructor
 */
export const CRUDDelete = (props) => (
    <Delete {...props} title={<CRUDDeleteTitle/>}/>
);

/**
 * render the list and show page of all kinds of fields
 *
 * ex: boolean select text number ...
 * @param field
 * @param index
 */
const renderField = (field) => (
    field.component === 'Boolean' ? renderBooleanField(field) :
        field.component === 'NullableBoolean' ? renderBooleanField(field) :
            field.component === 'Autocomplete' ? renderSelectField(field) :
                field.component === 'CheckboxGroup' ? renderSelectArrayField(field) :
                    field.component === 'Date' ? renderDateField(field) :
                        field.component === 'File' ? renderFileField(field) :
                            field.component === 'LongText' ? renderTextField(field) :
                                field.component === 'Number' ? renderNumberField(field) :
                                    field.component === 'RadioButtonGroup' ? renderSelectField(field) :
                                        field.component === 'Reference' ? renderReferenceField(field) :
                                            field.component === 'ReferenceArray' ? renderReferenceArrayField(field) :
                                                field.component === 'RichText' ? renderRichTextField(field) :
                                                    field.component === 'Select' ? renderSelectField(field) :
                                                        field.component === 'SelectArray' ? renderSelectArrayField(field) :
                                                            field.component === 'Image' ? renderImageField(field) :
                                                                renderTextField(field)

);

const renderBooleanField = (field) => (
    <BooleanField key={field.id} elStyle={{margin: 0}} label={field.label} source={field.name}/>
)
const renderReferenceField = (field) => (
    <ReferenceField key={field.id} label={field.label} source={field.name} reference={field.reference}>
        <TextField source={field.referenceOptionText}/>
    </ReferenceField>
)

const renderSelectArrayField = (field) => (
    <SelectArrayField key={field.id} label={field.label} source={field.name} choices={field.choices} optionText='name'
                      optionValue='id'/>
)
const renderReferenceArrayField = (field) => (
    <ReferenceArrayField key={field.id} label={field.label} reference={field.reference} source={field.name}>
        <SingleFieldList>
            <ChipField source={field.referenceOptionText}/>
        </SingleFieldList>
    </ReferenceArrayField>
)

const renderSelectField = (field) => (
    <SelectField key={field.id} source={field.name} label={field.label} choices={field.choices}/>
)

const renderTextField = (field) => (
    field.type === 'email' ? <EmailField key={field.id} label={field.label} source={field.name}/> :
        field.type === 'url' ? <UrlField key={field.id} label={field.label} source={field.name}/> :
            <TextField key={field.id} label={field.label} source={field.name}/>
)

const renderRichTextField = (field) => (
    <RichTextField key={field.id} label={field.label} source={field.name} stripTags/>
)

const renderImageField = (field) => (
    <ImageField key={field.id} label={field.label} source={field.name} title="title"/>
)

const renderDateField = (field) => (
    <DateField key={field.id} label={field.label} source={field.name} showTime={true}  options={{locale: 'zh-hans'}}/>
)

const renderFileField = (field) => (
    <FileField key={field.id} source="url" title="title"/>
)

const renderNumberField = (field) => (
    <NumberField key={field.id} label={field.label} source={field.name}/>
)

/**
 * render new and edit page
 * @param field
 * @param index
 */
const renderInput = (field) => (
    field.isAutoIncremented ? null :
        field.component === 'Boolean' ? renderBooleanInput(field) :
            field.component === 'NullableBoolean' ? renderNullableBooleanInput(field) :
                field.component === 'Autocomplete' ? renderAutoCompleteInput(field) :
                    field.component === 'CheckboxGroup' ? renderCheckboxGroupInput(field) :
                        field.component === 'Date' ? renderDateTimeInput(field) :
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


const renderDateTimeInput = (field) => (
    <DateTimeInput key={field.id} label={field.label} source={field.name} validate={generateValidators(field)} options={{locale: 'zh-hans'}}/>
);

const renderAutoCompleteInput = (field) => (
    <AutocompleteInput key={field.id} label={field.label} source={field.name} choices={field.choices}
                       defaultValue={field.defaultValue}
                       validate={generateValidators(field)}/>
);

const renderBooleanInput = (field) => (
    <BooleanInput key={field.id} label={field.label} source={field.name} defaultValue={field.defaultValue}/>
)

const renderNullableBooleanInput = (field) => (
    <NullableBooleanInput key={field.id} label={field.label} source={field.name} defaultValue={field.defaultValue}
                          validate={generateValidators(field)}/>
)

const renderCheckboxGroupInput = (field) => (
    <CheckboxGroupInput key={field.id} label={field.label} source={field.name} choices={field.choices}
                        defaultValue={field.defaultValue}/>
)

const renderFileInput = (field) => (
    <FileInput key={field.id} source={field.name} label={field.label} accept={'application/' + field.type}>
        <FileField source="src" title="title"/>
    </FileInput>
)

const renderLongTextInput = (field) => (
    <LongTextInput key={field.id} source={field.name} label={field.label} defaultValue={field.defaultValue}
                   validate={generateValidators(field)}/>
)

const renderNumberInput = (field) => (
    <NumberInput key={field.id} source={field.name} label={field.label} defaultValue={field.defaultValue}
                 validate={generateValidators(field)}/>
)

const renderRadioButtonGroupInput = (field) => (
    <RadioButtonGroupInput key={field.id} label={field.label} source={field.name} choices={field.choices}
                           defaultValue={field.defaultValue}
                           validate={generateValidators(field)}/>
)

const renderTextInput = (field) => (
    <TextInput key={field.id} label={field.label} source={field.name} type={field.type}
               defaultValue={field.defaultValue}
               validate={generateValidators(field)}/>
)

const renderReferenceInput = (field) => (
    <ReferenceInput key={field.id} label={field.label} source={field.name} reference={field.reference}
                    defaultValue={field.defaultValue} validate={generateValidators(field)} allowEmpty>
        <SelectInput optionText={field.referenceOptionText}/>
    </ReferenceInput>
)

const renderReferenceArrayInput = (field) => (
    <ReferenceArrayInput key={field.id} label={field.label} source={field.name} reference={field.reference}
                         defaultValue={field.defaultValue}
                         validate={generateValidators(field)} allowEmpty>
        <SelectArrayInput optionText={field.referenceOptionText}/>
    </ReferenceArrayInput>
)
const renderRichTextInput = (field) => (
    <RichTextInput key={field.id} label={field.label} source={field.name} defaultValue={field.defaultValue}
                   validate={generateValidators(field)}/>
)
const renderSelectInput = (field) => (
    <SelectInput key={field.id} label={field.label} source={field.name} choices={field.choices}
                 defaultValue={field.defaultValue}
                 validate={generateValidators(field)}/>
)

const renderSelectArrayInput = (field) => (
    <SelectArrayInput key={field.id} label={field.label} source={field.name} choices={field.choices}
                      defaultValue={field.defaultValue} validate={generateValidators(field)}/>
)

const renderImageInput = (field) => (
    <ImageInput key={field.id} source={field.name} label={field.label} accept="image/*"
                validate={generateValidators(field)}>
        <ImageField source="src" title="title"/>
    </ImageInput>
)

/**
 * generate validators from domain schema define
 *
 * validate for email number  required
 *
 * TODO custom validators
 * @param field
 * @returns {Array}
 */
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