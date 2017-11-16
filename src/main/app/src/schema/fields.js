import React from "react";
import {
    BooleanField,
    BooleanInput,
    Create,
    Datagrid,
    DisabledInput,
    Edit,
    EditButton,
    FilterButton,
    List,
    NumberInput,
    ReferenceField,
    ReferenceInput,
    required,
    SelectField,
    SelectInput,
    SimpleForm,
    TextField,
    TextInput
} from "admin-on-rest";
import ComponentType from "./ComponentType";
import {CardActions} from "material-ui/Card";
import FlatButton from "material-ui/FlatButton";
import NavigationRefresh from "material-ui/svg-icons/navigation/refresh";
import EmbeddedManyInput from "./EmbeddedManyInput";
import {DependentInput} from "aor-dependent-input";
import ReferenceDependentInput from "./ReferenceDependentInput";
const keys = Object.keys(ComponentType);
const arr = [];
keys.forEach(v => {
    arr.push({id: v, name: ComponentType[v]});
})
const cardActionStyle = {
    zIndex: 2,
    display: 'inline-block',
    float: 'right',
};

const CreateFieldActions = ({resource, filters, displayedFilters, filterValues, basePath, showFilter, refresh}) => (
    <CardActions style={cardActionStyle}>
        <FilterButton filterValues={filterValues} basePath={basePath}/>
        <FlatButton primary label="refresh" onClick={refresh} icon={<NavigationRefresh/>}/>
        {/* Add your custom actions */}
    </CardActions>
);

export const FieldList = (props) => (
    // filter={{ entity: true }}
    <List {...props} pagination={null} perPage={9999} actions={<CreateFieldActions/>}>
        <Datagrid>
            <SelectField source="component" choices={arr}/>
            <TextField source="label"/>
            <BooleanField source="required"/>
            <EditButton/>
        </Datagrid>
    </List>
);

const arrayField = ['Autocomplete', 'CheckboxGroup', 'RadioButtonGroup', 'SelectArray', 'Select'];
const checkNumber = (value) => value === 'Number';
const checkText = (value) => value === 'Text';
const checkReference = (value) => ['Reference', 'ReferenceArray'].includes(value);
const checkArray = (value) => arrayField.includes(value);
const checkReferenceEntity = (value) => {
    return value != null;
}

const inputType = [
    {id: 'text', name: '文本'},
    {id: 'email', name: '邮箱'},
    {id: 'password', name: '密码'},
    {id: 'url', name: '网址'},
];

export const FieldCreate = (props) => {
    const record = props.location.data;

    return <Create {...props} actions={null}>
        <SimpleForm redirect={'/_entitys' + (record ? '/' + record.eid : '')}>

            <ReferenceInput source="eid" reference="_entitys" allowEmpty
                            defaultValue={record ? record.eid : ''} validate={required}>
                <SelectInput optionText="label"/>
            </ReferenceInput>
            <TextInput source="label" validate={[required]}/>
            <SelectInput source="component" choices={arr}
                         defaultValue={record ? record.component : 'Text'}/>
            <BooleanInput source="required"/>
            <DependentInput dependsOn="component" resolve={checkNumber}>
                <NumberInput source="minValue"/>
                <NumberInput source="maxValue"/>
                <NumberInput source="defaultValue"/>
            </DependentInput>
            <DependentInput dependsOn="component" resolve={checkText}>
                <SelectInput source="inputType" validate={required} choices={inputType}/>
                <NumberInput source="maxLength"/>
                <TextInput source="defaultValue"/>
            </DependentInput>
            <DependentInput dependsOn="component" resolve={checkReference}>
                <ReferenceInput source="reference" reference="_entitys" allowEmpty validate={required}>
                    <SelectInput optionText="label" optionValue="name"/>
                </ReferenceInput>
            </DependentInput>

            <DependentInput dependsOn="reference" resolve={checkReferenceEntity}>
                <ReferenceDependentInput source="referenceOptionText" allowEmpty validate={required}>
                </ReferenceDependentInput>
            </DependentInput>


            <DependentInput dependsOn="component" resolve={checkArray}>
                <EmbeddedManyInput source="choices" validate={required} elStyle={{display: 'inline-block'}}>
                    <TextInput source="id"/>
                    <TextInput source="name"/>
                </EmbeddedManyInput>
            </DependentInput>

            <BooleanInput source="showInList" label="列表页显示"/>
            <BooleanInput source="showInFilter" label="筛选显示"/>
            <BooleanInput source="showInCreate" label="新增页显示"/>
            <BooleanInput source="showInEdit" label="编辑页显示"/>
            <BooleanInput source="showInShow" label="详细页显示"/>
        </SimpleForm>
    </Create>
};

export const FieldEdit = (props) => {
    const record = props.location.data;
    return <Edit  {...props} actions={null}>
        <SimpleForm redirect={'/_entitys' + (record ? record.entity : '')}>
            <ReferenceField source="eid" reference="_entitys" allowEmpty
                            defaultValue={record ? record.entity : ''} validate={required}>
                <TextField source="label"/>
            </ReferenceField>
            <DisabledInput source="name"/>
            <TextInput source="label" validate={[required]}/>
            <SelectInput source="component" choices={arr}/>
            <BooleanInput source="required"/>
            <DependentInput dependsOn="component" resolve={checkNumber}>
                <NumberInput source="minValue"/>
                <NumberInput source="maxValue"/>
                <NumberInput source="defaultValue"/>
            </DependentInput>
            <DependentInput dependsOn="component" resolve={checkText}>
                <SelectInput source="inputType" validate={required} choices={inputType}/>
                <NumberInput source="maxLength"/>
                <TextInput source="defaultValue"/>
            </DependentInput>
            <DependentInput dependsOn="component" resolve={checkReference}>
                <ReferenceInput source="reference" reference="_entitys" allowEmpty validate={required}>
                    <SelectInput optionText="label" optionValue="name"/>
                </ReferenceInput>
            </DependentInput>
            <DependentInput dependsOn="reference" resolve={checkReferenceEntity}>
                <ReferenceDependentInput source="referenceOptionText" reference="_fields" allowEmpty
                                         validate={required}/>
            </DependentInput>

            <DependentInput dependsOn="component" resolve={checkArray}>
                <EmbeddedManyInput source="choices" validate={required} elStyle={{display: 'inline-block'}}>
                    <TextInput source="id"/>
                    <TextInput source="name"/>
                </EmbeddedManyInput>
            </DependentInput>
            <BooleanInput source="showInList" label="列表页显示"/>
            <BooleanInput source="showInFilter" label="筛选显示"/>
            <BooleanInput source="showInCreate" label="新增页显示"/>
            <BooleanInput source="showInEdit" label="编辑页显示"/>
            <BooleanInput source="showInShow" label="详细页显示"/>
        </SimpleForm>
    </Edit>
};

