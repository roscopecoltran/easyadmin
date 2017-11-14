import React from "react";
import {
    Create,
    Datagrid,
    Edit,
    EditButton,
    List,
    required,
    SelectField,
    SelectInput,
    SimpleForm,
    TextField,
    TextInput
} from "admin-on-rest";
import {DependentInput} from "aor-dependent-input";
import SyncButton from "./SyncButton";
export const DataSourceList = (props) => (
    <List {...props} pagination={null} perPage={9999}>
        <Datagrid>
            <TextField source="id" sortable={false}/>
            <TextField source="jdbcUrl"/>
            <SelectField source="type" label="数据库类型" choices={dataType}/>
            <SyncButton style={{padding: 0}}/>
            <EditButton/>
        </Datagrid>
    </List>
);

export const DataSourceCreate = (props) => (
    <Create {...props} redirect="list">
        <SimpleForm>
            <SelectInput source="type" label="数据库类型" choices={dataType} validate={required}/>
            <DependentInput dependsOn="type" resolve={checkRdb}>
                <TextInput source="jdbcUrl" validate={required}/>
                <TextInput source="username" validate={required}/>
                <TextInput type="password" source="password" validate={required}/>
            </DependentInput>
            <DependentInput dependsOn="type" resolve={checkEs}>
                <TextInput source="jdbcUrl" validate={required}/>
                <TextInput source="clusterName" validate={required}/>
                <TextInput source="indexName" validate={required}/>
            </DependentInput>
        </SimpleForm>
    </Create>
);

export const DataSourceEdit = (props) => (
    <Edit {...props} redirect="list">
        <SimpleForm>
            <SelectInput source="type" label="数据库类型" choices={dataType} validate={required}/>
            <DependentInput dependsOn="type" resolve={checkRdb}>
                <TextInput source="jdbcUrl" validate={required}/>
                <TextInput source="username" validate={required}/>
                <TextInput type="password" source="password" validate={required}/>
            </DependentInput>
            <DependentInput dependsOn="type" resolve={checkEs}>
                <TextInput source="jdbcUrl" validate={required}/>
                <TextInput source="clusterName" validate={required}/>
                <TextInput source="indexName" validate={required}/>
            </DependentInput>
        </SimpleForm>
    </Edit>
);

const checkRdb = (value) => value !== 'es';

const checkEs = (value) => value === 'es';

const dataType = [{id: 'mysql', name: 'MySql'},
    {id: 'mongo', name: 'Mongo'}, {id: 'es', name: 'elasticsearch'}];