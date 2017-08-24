import React from 'react';
import {
    List,
    Edit,
    Datagrid,
    TextField,
    Create,
    SimpleForm,
    TextInput,
    EditButton,
    DisabledInput,
    ReferenceManyField
} from 'admin-on-rest';
import { TabbedForm, FormTab ,CreateButton,DeleteButton,Button} from 'admin-on-rest';
import PopoverExampleSimple from './AddFieldButton';

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
                <PopoverExampleSimple/>
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

