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
    SimpleShowLayout,
    Show
} from 'admin-on-rest';
import LinkToRelatedFields from './LinkToRelatedFields';
export const EntityList = (props) => (
    <List {...props} pagination={null} perPage={9999}>
        <Datagrid>
            <TextField source="id"/>
            <TextField source="name"/>
            <TextField source="label"/>
            <EditButton />
            <LinkToRelatedFields/>
        </Datagrid>
    </List>
);

export const EntityCreate = (props) => (
    <Create {...props}>
        <SimpleForm redirect="list">
            <TextInput source="name" label="唯一KEY"/>
            <TextInput source="label" label="标签"/>
        </SimpleForm>
    </Create>
);

export const EntityEdit = (props) => (
    <Edit  {...props}>
        <SimpleForm redirect="list">
            <DisabledInput source="name" label="唯一KEY"/>
            <TextInput source="label" label="标签"/>
        </SimpleForm>
    </Edit>
);

export const EntityShow = (props) => (
    <Show {...props}>
        <SimpleShowLayout>
            <TextField source="name" label="唯一KEY"/>
            <TextField source="label" label="标签"/>
        </SimpleShowLayout>
    </Show>
);