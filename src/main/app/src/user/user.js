import React from "react";
import {
    BooleanField,
    BooleanInput,
    ChipField,
    Create,
    Datagrid,
    DisabledInput,
    Edit,
    EditButton,
    List,
    ReferenceArrayField,
    ReferenceArrayInput,
    ReferenceManyField,
    SelectArrayInput,
    SimpleForm,
    SingleFieldList,
    TextField,
    TextInput
} from "admin-on-rest";

export const UserList = (props) => (
    <List {...props} pagination={null} perPage={9999} title='用户'>
        <Datagrid>
            <TextField source="username" label="用户名"/>
            <BooleanField source="enabled" label="是否有效"/>
            <ReferenceArrayField label="角色" reference="_roles" source="roles">
                <SingleFieldList>
                    <ChipField source="name"/>
                </SingleFieldList>
            </ReferenceArrayField>
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
            <BooleanInput source="enabled" label="是否有效"/>
        </SimpleForm>
    </Edit>
);