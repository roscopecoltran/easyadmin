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
import SyncButton from "./SyncButton";
export const DataSourceList = (props) => (
    <List {...props} pagination={null} perPage={9999}>
        <Datagrid>
            <TextField source="id" sortable={false}/>
            <TextField source="jdbcUrl"/>
            <TextField source="username"/>
            <TextField source="password" type="password"/>
            <SelectField source="type" label="数据库类型" choices={[
                {id: 'mysql', name: 'MySql'}
            ]}/>
            <SyncButton style={{padding: 0}}/>
            <EditButton/>
        </Datagrid>
    </List>
);

export const DataSourceCreate = (props) => (
    <Create {...props}>
        <SimpleForm>
            <SelectInput source="type" label="数据库类型" choices={[
                {id: 'mysql', name: 'MySql'}
            ]} validate={required}/>
            <TextInput source="jdbcUrl" validate={required}/>
            <TextInput source="username" validate={required}/>
            <TextInput type="password" source="password" validate={required}/>
        </SimpleForm>
    </Create>
);

export const DataSourceEdit = (props) => (
    <Edit {...props} >
        <SimpleForm>
            <SelectInput source="type" label="数据库类型" choices={[
                {id: 'mysql', name: 'MySql'}
            ]} validate={required}/>
            <TextInput source="jdbcUrl" validate={required}/>
            <TextInput source="username" validate={required}/>
            <TextInput type="password" source="password" validate={required}/>
        </SimpleForm>
    </Edit>
);