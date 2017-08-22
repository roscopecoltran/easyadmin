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
    SimpleShowLayout,
    Show,
    SelectInput,
    BooleanField,
    BooleanInput,
    SelectField,
    ReferenceInput
} from 'admin-on-rest';
import ComponentType from './ComponentType';

const arr = [];
for (var [key, value] of Object.entries(ComponentType)) {
    arr.push({id: key, name: value});
}
export const FieldList = (props) => (
    // filter={{ entity: true }}
    <List {...props} pagination={null} perPage={9999} >
        <Datagrid>
            <SelectField source="component" label="字段类型" choices={arr}/>
            <TextField source="name" label="字段KEY（唯一）"/>
            <TextField source="label" label="字段标签"/>
            <BooleanField source="required" label="是否必填"/>
            <EditButton />
        </Datagrid>
    </List>
);

export const FieldCreate = (props) => (
    <Create {...props}>
        <SimpleForm redirect="list">
            <ReferenceInput label="实体" source="entity" reference="entitys" allowEmpty>
                <SelectInput optionText="label" />
            </ReferenceInput>
            <SelectInput source="component" label="字段类型" choices={arr}/>
            <TextInput source="name" label="唯一KEY"/>
            <TextInput source="label" label="标签"/>
            <BooleanInput label="是否必填" source="required"/>
        </SimpleForm>
    </Create>
);

export const FieldEdit = (props) => (
    <Edit  {...props}>
        <SimpleForm redirect="list">
            <DisabledInput source="component" label="字段类型" choices={arr}/>
            <DisabledInput source="name" label="唯一KEY"/>
            <TextInput source="label" label="标签"/>
            <BooleanInput label="是否必填" source="required"/>
        </SimpleForm>
    </Edit>
);

