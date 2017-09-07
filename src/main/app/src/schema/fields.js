import React from "react";
import {
    BooleanField,
    BooleanInput,
    Create,
    Datagrid,
    DateInput,
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
            <SelectField source="component" label="字段类型" choices={arr}/>
            <TextField source="label" label="字段标签"/>
            <BooleanField source="required" label="是否必填"/>
            <EditButton/>
        </Datagrid>
    </List>
);

const arrayField = ['Autocomplete', 'CheckboxGroup', 'RadioButtonGroup', 'SelectArray', 'Select'];
const checkNumber = (value) => value === 'Number';
const checkText = (value) => value === 'Text';
const checkDate = (value) => value === 'Date';
const checkReference = (value) => ['Reference', 'ReferenceArray'].includes(value);
const checkArray = (value) => arrayField.includes(value);
const checkReferenceEntity = (value) => {
    return value != null;
}
export const FieldCreate = (props) => {
    const record = props.location.data;

    return <Create {...props} actions={null} title="新增字段">
        <SimpleForm redirect={'/_entitys' + (record ? '/' + record.eid : '')}>

            <ReferenceInput label="对象" source="eid" reference="_entitys" allowEmpty
                            defaultValue={record ? record.eid : ''} validate={required}>
                <SelectInput optionText="label"/>
            </ReferenceInput>
            <TextInput source="label" label="标签" validate={[required]}/>
            <SelectInput source="component" label="组件类型" choices={arr}
                         defaultValue={record ? record.component : 'Text'}/>
            <BooleanInput label="是否必填" source="required"/>
            <DependentInput dependsOn="component" resolve={checkNumber}>
                <NumberInput source="minValue" label="最小值"/>
                <NumberInput source="maxValue" label="最大值"/>
                <NumberInput source="defaultValue" label="默认值"/>
            </DependentInput>
            <DependentInput dependsOn="component" resolve={checkText}>
                <SelectInput source="inputType" validate={required} label="文本类型" choices={[
                    {id: 'text', name: '文本'},
                    {id: 'email', name: '邮箱'},
                    {id: 'password', name: '密码'},
                    {id: 'url', name: '网址'},
                ]}/>
                <NumberInput source="maxLength" label="最大长度"/>
                <TextInput source="defaultValue" label="默认值"/>
            </DependentInput>
            <DependentInput dependsOn="component" resolve={checkReference}>
                <ReferenceInput label="引用对象" source="reference" reference="_entitys" allowEmpty validate={required}>
                    <SelectInput optionText="label"/>
                </ReferenceInput>
            </DependentInput>

            <DependentInput dependsOn="reference" resolve={checkReferenceEntity}>
                <ReferenceDependentInput label="引用对象字段" source="referenceOptionText" allowEmpty validate={required}>
                </ReferenceDependentInput>
            </DependentInput>


            <DependentInput dependsOn="component" resolve={checkArray}>
                <EmbeddedManyInput source="choices" validate={required} elStyle={{display: 'inline-block'}}>
                    <TextInput source="id"/>
                    <TextInput source="name"/>
                </EmbeddedManyInput>
            </DependentInput>

        </SimpleForm>
    </Create>
};

export const FieldEdit = (props) => {
    const record = props.location.data;
    return <Edit  {...props} actions={null} title="编辑字段">
        <SimpleForm redirect={'/_entitys' + (record ? record.entity : '')}>
            <ReferenceField label="引用对象" source="eid" reference="_entitys" allowEmpty
                            defaultValue={record ? record.entity : ''} validate={required}>
                <TextField source="label"/>
            </ReferenceField>
            <TextInput source="label" label="标签" validate={[required]}/>
            <SelectInput source="component" label="组件类型" choices={arr}/>
            <BooleanInput label="是否必填" source="required"/>
            <DependentInput dependsOn="component" resolve={checkNumber}>
                <NumberInput source="minValue" label="最小值"/>
                <NumberInput source="maxValue" label="最大值"/>
                <NumberInput source="defaultValue" label="默认值"/>
            </DependentInput>
            <DependentInput dependsOn="component" resolve={checkText}>
                <SelectInput source="inputType" validate={required} label="文本类型" choices={[
                    {id: 'text', name: '文本'},
                    {id: 'email', name: '邮箱'},
                    {id: 'password', name: '密码'},
                    {id: 'url', name: '网址'},
                ]}/>
                <NumberInput source="maxLength" label="最大长度"/>
                <TextInput source="defaultValue" label="默认值"/>
            </DependentInput>
            <DependentInput dependsOn="component" resolve={checkReference}>
                <ReferenceInput label="引用对象" source="reference" reference="_entitys" allowEmpty validate={required}>
                    <SelectInput optionText="label"/>
                </ReferenceInput>
            </DependentInput>
            <DependentInput dependsOn="reference" resolve={checkReferenceEntity}>
                <ReferenceDependentInput label="引用对象字段" source="referenceOptionText" reference="_fields" allowEmpty
                                         validate={required}/>
            </DependentInput>

            <DependentInput dependsOn="component" resolve={checkArray}>
                <EmbeddedManyInput source="choices" validate={required} elStyle={{display: 'inline-block'}}>
                    <TextInput source="id"/>
                    <TextInput source="name"/>
                </EmbeddedManyInput>
            </DependentInput>

        </SimpleForm>
    </Edit>
};

