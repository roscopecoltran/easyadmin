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
    TextInput
} from "admin-on-rest";

export const UserList = (props) => (
    <List {...props} pagination={null} perPage={9999} title='用户'>
        <Datagrid>
            <TextField source="username" label="用户名"/>
            <BooleanField source="enabled" label="是否有效"/>
            <EditButton/>
        </Datagrid>
    </List>
);