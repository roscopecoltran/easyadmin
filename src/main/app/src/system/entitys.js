import React from 'react';
import { List, Datagrid, TextField,Create,SimpleForm,TextInput,EditButton,DisabledInput,SimpleShowLayout,Show } from 'admin-on-rest';

export const EntityList = (props) => (
    <List {...props} pagination={null} perPage={9999}>
        <Datagrid>
            <TextField source="id" />
            <TextField source="name" />
            <TextField source="label" />
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
    <Create {...props}>
        <SimpleForm>
            <DisabledInput source="name" label="唯一KEY"/>
            <TextInput source="label" label="标签"/>
        </SimpleForm>
    </Create>
);

export const EntityShow = (props) => (
    <Show {...props}>
        <SimpleShowLayout>
            <TextField source="name" label="唯一KEY"/>
            <TextField source="label" label="标签"/>
        </SimpleShowLayout>
    </Show>
);