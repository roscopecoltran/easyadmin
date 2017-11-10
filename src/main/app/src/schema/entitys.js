import React from "react";
import {
    Create,
    BooleanField,
    BooleanInput,
    Datagrid,
    Edit,
    EditButton,
    List,
    ReferenceManyField,
    SimpleForm,
    TextField,
    TextInput
} from "admin-on-rest";
import AddFieldButton from "./AddFieldButton";

export const EntityList = (props) => (
    <List {...props} pagination={null} perPage={9999}>
        <Datagrid>
            <TextField source="label"/>
            <BooleanField source="showInMenu"/>
            <EditButton/>
        </Datagrid>
    </List>
);

export const EntityCreate = (props) => (
    <Create {...props}>
        <SimpleForm>
            <TextInput source="label"/>
            <BooleanInput source="showInMenu"/>
        </SimpleForm>
    </Create>
);

export const EntityEdit = (props) => (
    <Edit {...props}>
        <SimpleForm>
            <TextInput source="label"/>
            <BooleanInput source="showInMenu"/>
            <AddFieldButton/>
            <ReferenceManyField label={'resources._fields.name'} reference="_fields" target="eid">
                <Datagrid>
                    <TextField source="label"/>
                    <TextField source="component"/>
                    <BooleanField source="showInList"/>
                    <BooleanField source="showInFilter"/>
                    <BooleanField source="showInCreate"/>
                    <BooleanField source="showInEdit"/>
                    <BooleanField source="showInShow"/>
                    <EditButton/>
                </Datagrid>
            </ReferenceManyField>
        </SimpleForm>
    </Edit>
);

