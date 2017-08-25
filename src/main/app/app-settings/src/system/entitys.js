import React from "react";
import {
    Create,
    Datagrid,
    DisabledInput,
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
            <TextField source="id"/>
            <TextField source="label"/>
            <EditButton />
        </Datagrid>
    </List>
);

export const EntityCreate = (props) => (
    <Create {...props}>
        <SimpleForm>
                <TextInput source="name" label="唯一KEY"/>
                <TextInput source="label" label="标签"/>
        </SimpleForm>
    </Create>
);

export const EntityEdit = (props) => (
    <Edit {...props}>
        <SimpleForm>
                <DisabledInput source="name" label="唯一KEY"/>
                <TextInput source="label" label="标签"/>
                <AddFieldButton/>
                <ReferenceManyField  label="Fields" reference="fields" target="entity" >
                    <Datagrid>
                        <TextField source="name" />
                        <TextField source="label" />
                        <TextField source="component" />
                        <EditButton />
                    </Datagrid>
                </ReferenceManyField>
        </SimpleForm>
    </Edit>
);

