import React from "react";
import {
    Create,
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
            <EditButton/>
        </Datagrid>
    </List>
);

export const EntityCreate = (props) => (
    <Create {...props}>
        <SimpleForm>
            <TextInput source="label"/>
        </SimpleForm>
    </Create>
);

export const EntityEdit = (props) => (
    <Edit {...props}>
        <SimpleForm>
            <TextInput source="label"/>
            <AddFieldButton/>
            <ReferenceManyField label={'resources._fields.name'} reference="_fields" target="eid">
                <Datagrid>
                    <TextField source="label"/>
                    <TextField source="component"/>
                    <EditButton/>
                </Datagrid>
            </ReferenceManyField>
        </SimpleForm>
    </Edit>
);

