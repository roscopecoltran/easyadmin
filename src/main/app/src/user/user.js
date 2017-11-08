import React from "react";
import {
    BooleanField,
    BooleanInput,
    ChipField,
    Create,
    Datagrid,
    Edit,
    EditButton,
    List,
    ReferenceArrayField,
    ReferenceArrayInput,
    SelectArrayInput,
    SimpleForm,
    SingleFieldList,
    TextField,
    TextInput
} from "admin-on-rest";

export const UserList = (props) => (
    <List {...props} pagination={null} perPage={9999}>
        <Datagrid>
            <TextField source="username"/>
            <BooleanField source="enabled"/>
            <ReferenceArrayField reference="_roles" source="roles">
                <SingleFieldList>
                    <ChipField source="name"/>
                </SingleFieldList>
            </ReferenceArrayField>
            <EditButton/>
        </Datagrid>
    </List>
);


export const UserCreate = (props) => (
    <Create {...props}>
        <SimpleForm>
            <TextInput source="username"/>
            <TextInput type="password" source="password"/>
            <ReferenceArrayInput source="roles" reference="_roles" allowEmpty>
                <SelectArrayInput optionText="name"/>
            </ReferenceArrayInput>
            <BooleanInput source="enabled" defaultValue={true}/>
        </SimpleForm>
    </Create>
);

export const UserEdit = (props) => (
    <Edit {...props} >
        <SimpleForm>
            <TextField source="username"/>
            <ReferenceArrayInput source="roles" reference="_roles">
                <SelectArrayInput optionText="name"/>
            </ReferenceArrayInput>
            <BooleanInput source="enabled"/>
        </SimpleForm>
    </Edit>
);