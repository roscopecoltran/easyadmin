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
    ReferenceArrayInput,
    SelectArrayInput,
    BooleanInput
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

export const UserCreate = (props) => (
    <Create {...props} title='新建用户'>
        <SimpleForm>
            <TextInput source="username" label="用户名"/>
            <TextInput type="password" source="password" label="密码"/>
            <ReferenceArrayInput source="roles" reference="_roles" label="角色" allowEmpty>
                <SelectArrayInput optionText="name"/>
            </ReferenceArrayInput>
            <BooleanInput source="enabled" label="是否有效" defaultValue={true}/>
        </SimpleForm>
    </Create>
);

export const UserEdit = (props) => (
    <Edit {...props} title='修改用户'>
        <SimpleForm>
            <TextField source="username" label="用户名"/>
            <ReferenceArrayInput source="roles" reference="_roles" label="角色">
                <SelectArrayInput optionText="name"/>
            </ReferenceArrayInput>
        </SimpleForm>
    </Edit>
);