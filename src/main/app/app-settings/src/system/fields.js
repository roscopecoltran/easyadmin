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
    SelectInput,
    BooleanField,
    BooleanInput,
    SelectField,
    ReferenceInput
} from 'admin-on-rest';
import ComponentType from './ComponentType';
import { CardActions } from 'material-ui/Card';
import FlatButton from 'material-ui/FlatButton';
import NavigationRefresh from 'material-ui/svg-icons/navigation/refresh';
import { ListButton, ShowButton, DeleteButton,CreateButton,FilterButton } from 'admin-on-rest';
import {AddFieldButton} from './addFieldButton';
const arr = [];
for (var [key, value] of Object.entries(ComponentType)) {
    arr.push({id: key, name: value});
}

const cardActionStyle = {
    zIndex: 2,
    display: 'inline-block',
    float: 'right',
};

const CreateFieldActions = ({ resource, filters, displayedFilters, filterValues, basePath, showFilter, refresh }) => (
    <CardActions style={cardActionStyle}>
        <AddFieldButton />
        <FilterButton filterValues={filterValues} basePath={basePath} />
        <FlatButton primary label="refresh" onClick={refresh} icon={<NavigationRefresh />} />
        {/* Add your custom actions */}
    </CardActions>
);

export const FieldList = (props) => (
    // filter={{ entity: true }}
    <List {...props} pagination={null} perPage={9999} actions={<CreateFieldActions/>}>
        <Datagrid>
            <TextField source="id" sortable={false}/>
            <SelectField source="component" label="字段类型" choices={arr}/>
            <TextField source="name" label="字段KEY（唯一）"/>
            <TextField source="label" label="字段标签"/>
            <BooleanField source="required" label="是否必填"/>
            <EditButton />
        </Datagrid>
    </List>
);

export const FieldCreate = (props) => (
    <Create {...props}>
        <SimpleForm redirect="list">
            <ReferenceInput label="实体" source="entity" reference="entitys" allowEmpty>
                <SelectInput optionText="label" />
            </ReferenceInput>
            <SelectInput source="component" label="字段类型" choices={arr}/>
            <TextInput source="name" label="唯一KEY"/>
            <TextInput source="label" label="标签"/>
            <BooleanInput label="是否必填" source="required"/>
        </SimpleForm>
    </Create>
);

export const FieldEdit = (props) => (
    <Edit  {...props}>
        <SimpleForm redirect="list">
            <DisabledInput source="component" label="字段类型" choices={arr}/>
            <DisabledInput source="name" label="唯一KEY"/>
            <TextInput source="label" label="标签"/>
            <BooleanInput label="是否必填" source="required"/>
        </SimpleForm>
    </Edit>
);

