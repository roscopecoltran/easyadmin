import React from 'react';
import {
    List,
    Edit,
    Datagrid,
    TextField,
    Create,
    SimpleForm,
    TextInput,
    EditButton,
    DisabledInput,
    SelectInput,
    BooleanField,
    BooleanInput,
    SelectField,
    ReferenceInput,
    ReferenceField,
    NumberInput,
    AutocompleteInput
} from 'admin-on-rest';
import ComponentType from './ComponentType';
import {CardActions} from 'material-ui/Card';
import FlatButton from 'material-ui/FlatButton';
import NavigationRefresh from 'material-ui/svg-icons/navigation/refresh';
import {ListButton, ShowButton, DeleteButton, CreateButton, FilterButton, required} from 'admin-on-rest';
import EmbeddedManyInput from './EmbeddedManyInput';
import {DependentInput} from 'aor-dependent-input';
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
        <FlatButton primary label="refresh" onClick={refresh} icon={<NavigationRefresh />}/>
        {/* Add your custom actions */}
    </CardActions>
);

export const FieldList = (props) => (
    // filter={{ entity: true }}
    <List {...props} pagination={null} perPage={9999} actions={<CreateFieldActions/>}>
        <Datagrid>
            <TextField source="id" sortable={false}/>
            <SelectField source="component" label="字段类型" choices={arr}/>
            <TextField source="name" label="字段KEY（唯一）"/>
            <TextField source="label" label="字段标签"/>
            <BooleanField source="required" label="是否必填"/>
            <EditButton />
        </Datagrid>
    </List>
);

const arrayField = ['Autocomplete', 'CheckboxGroup', 'RadioButtonGroup', 'SelectArray', 'Select'];
const checkNumber = (value) => value === 'Number';
const checkText = (value) => value === 'Text';
const checkReference = (value) => value === 'Reference';
const checkArray = (value) => arrayField.includes(value);
const checkReferenceEntity = (value) =>  value;
export const FieldCreate = (props) => {
    const record = props.location.data;
    return <Create {...props} actions={null}>
        <SimpleForm redirect={'/entitys/' + record.entity}>
            <DisabledInput source="entity" defaultValue={record.entity} disabled={true}/>
            <DisabledInput source="component" choices={arr} defaultValue={record.component} disabled/>
            <TextInput source="name" label="唯一KEY" validate={[required]}/>
            <TextInput source="label" label="标签" validate={[required]}/>
            <BooleanInput label="是否必填" source="required"/>
            <DependentInput dependsOn="component" resolve={checkNumber}>
                <NumberInput source="minValue" label="最小值"/>
                <NumberInput source="maxValue" label="最大值"/>
            </DependentInput>
            <DependentInput dependsOn="component" resolve={checkText}>
                <SelectInput source="inputType" validate={required} label="文本类型" choices={[
                    {id: 'text', name: '文本'},
                    {id: 'email', name: '邮箱'},
                    {id: 'password', name: '密码'},
                    {id: 'url', name: '网址'},
                ]}/>
            </DependentInput>
            <DependentInput dependsOn="component" resolve={checkReference}>
                <ReferenceInput label="引用对象" source="reference" reference="entitys" allowEmpty validate={required}>
                    <SelectInput optionText="label"/>
                </ReferenceInput>
            </DependentInput>
            <DependentInput dependsOn="reference" resolve={checkReferenceEntity}>
                <ReferenceInput {...props} label="引用对象显示字段" source="referenceOptionText" reference="fields" allowEmpty
                                filter={{entity: this.props.dependsOnValue}}>
                    <SelectInput optionText="label"/>
                </ReferenceInput>
            </DependentInput>
            <DependentInput dependsOn="reference" resolve={checkArray}>
                <EmbeddedManyInput source="choices" validate={required} elStyle={{display: 'inline-block'}}>
                    <TextInput source="id"/>
                    <TextInput source="name"/>
                </EmbeddedManyInput>
            </DependentInput>
        </SimpleForm>
    </Create>
};

export const FieldEdit = (props) => (
    <Edit  {...props} actions={null}>
        <SimpleForm redirect={'/entitys'}>
            <DisabledInput source="entity"/>
            <SelectField source="component" label="字段类型" choices={arr} disabled={true}/>
            <DisabledInput source="name" label="唯一KEY"/>
            <TextInput source="label" label="标签"/>
            <BooleanInput label="是否必填" source="required"/>
            <DependentInput dependsOn="component" resolve={checkNumber}>
                <NumberInput source="minValue" label="最小值"/>
                <NumberInput source="maxValue" label="最大值"/>
            </DependentInput>
            <DependentInput dependsOn="component" resolve={checkText}>
                <SelectInput source="inputType" validate={required} label="文本类型" choices={[
                    {id: 'text', name: '文本'},
                    {id: 'email', name: '邮箱'},
                    {id: 'password', name: '密码'},
                    {id: 'url', name: '网址'},
                ]}/>
            </DependentInput>
            <DependentInput dependsOn="component" resolve={checkReference}>
                <ReferenceInput label="引用对象" source="reference" reference="entitys" allowEmpty validate={required}>
                    <SelectInput optionText="label"/>
                </ReferenceInput>
            </DependentInput>
            <DependentInput dependsOn="component" resolve={checkReferenceEntity}>
                <ReferenceInput label="引用对象显示字段" source="referenceOptionText" reference="fields" allowEmpty
                                filter={{entity: '3'}}>
                    <SelectInput optionText="label"/>
                </ReferenceInput>
            </DependentInput>
            <DependentInput dependsOn="reference" resolve={checkArray}>
                <EmbeddedManyInput source="choices" validate={required} elStyle={{display: 'inline-block'}}>
                    <TextInput source="id"/>
                    <TextInput source="name"/>
                </EmbeddedManyInput>
            </DependentInput>
        </SimpleForm>
    </Edit>
);

