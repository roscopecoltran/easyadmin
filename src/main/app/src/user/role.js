import React from "react";
import {
    Create,
    Datagrid,
    DisabledInput,
    BooleanField,
    Edit,
    EditButton,
    List,
    ReferenceManyField,
    SimpleForm,
    TextField,
    TextInput,
    SelectArrayInput
} from "admin-on-rest";

export const RoleList = (props) => (
    <List {...props} pagination={null} perPage={9999} title='角色'>
        <Datagrid>
            <TextField source="name" label="角色名"/>
            <EditButton/>
        </Datagrid>
    </List>
);

export const RoleCreate = (props) => (
    <Create {...props} title='新建角色'>
        <SimpleForm>
            <TextInput source="name" label="角色名"/>
        </SimpleForm>
    </Create>
);

export const RoleEdit = (props) => (
    <Create {...props} title='新建角色'>
        <SimpleForm>
            <TextField source="name" label="角色名"/>
        </SimpleForm>
    </Create>
);