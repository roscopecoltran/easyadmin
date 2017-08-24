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
    ReferenceInput,
    ReferenceField
} from 'admin-on-rest';
import ComponentType from './ComponentType';
import {CardActions} from 'material-ui/Card';
import FlatButton from 'material-ui/FlatButton';
import NavigationRefresh from 'material-ui/svg-icons/navigation/refresh';
import {ListButton, ShowButton, DeleteButton, CreateButton, FilterButton} from 'admin-on-rest';
const keys = Object.keys(ComponentType);
const arr = [];
keys.forEach(v => {
    arr.push({id: v, name: ComponentType[v]});
})
const cardActionStyle = {
    zIndex: 2,
    display: 'inline-block',
    float: 'right',
};

const CreateFieldActions = ({resource, filters, displayedFilters, filterValues, basePath, showFilter, refresh}) => (
    <CardActions style={cardActionStyle}>
        <FilterButton filterValues={filterValues} basePath={basePath}/>
        <FlatButton primary label="refresh" onClick={refresh} icon={<NavigationRefresh />}/>
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
    <Create {...props} actions={null}>
        <SimpleForm redirect={'/entitys/' + props.location.data.entity}>
            <TextInput source="entity" defaultValue={props.location.data.entity} disabled={true}/>
            <SelectInput source="component" label="字段类型" choices={arr} defaultValue={props.location.data.component}
                         disabled={true}/>
            <TextInput source="name" label="唯一KEY"/>
            <TextInput source="label" label="标签"/>
            <BooleanInput label="是否必填" source="required"/>
        </SimpleForm>
    </Create>
);

export const FieldEdit = (props) => (
    <Edit  {...props} actions={null}>
        <SimpleForm redirect={'/entitys'}>
            <DisabledInput source="entity"/>
            <SelectField source="component" label="字段类型" choices={arr} disabled={true}/>
            <DisabledInput source="name" label="唯一KEY"/>
            <TextInput source="label" label="标签"/>
            <BooleanInput label="是否必填" source="required"/>
        </SimpleForm>
    </Edit>
);

