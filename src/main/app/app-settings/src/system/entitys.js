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
            <TextField source="label" label="名称"/>
            <EditButton />
        </Datagrid>
    </List>
);

export const EntityCreate = (props) => (
    <Create {...props}>
        <SimpleForm>
                <TextInput source="label" label="标签"/>
        </SimpleForm>
    </Create>
);

export const EntityEdit = (props) => (
    <Edit {...props}>
        <SimpleForm>
                <TextInput source="label" label="标签"/>
                <AddFieldButton/>
                <ReferenceManyField  label="字段" reference="fields" target="entity" >
                    <Datagrid>
                        <TextField source="label" label="标签"/>
                        <TextField source="component" label="类型"/>
                        <EditButton />
                    </Datagrid>
                </ReferenceManyField>
        </SimpleForm>
    </Edit>
);

