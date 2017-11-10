import React from "react";
import {
    BooleanInput,
    Create,
    Edit,
    ReferenceField,
    ReferenceInput,
    required,
    SelectInput,
    SimpleForm,
    TextField
} from "admin-on-rest";

export const PermissionCreate = (props) => {
    const record = props.location.data;

    return <Create {...props} actions={null}>
        <SimpleForm redirect={'/_roles' + (record ? '/' + record.roleId : '')}>

            <ReferenceInput source="roleId" reference="_roles" allowEmpty
                            defaultValue={record ? record.roleId : ''} validate={required}>
                <SelectInput optionText="name"/>
            </ReferenceInput>
            <ReferenceInput source="eid" reference="_entitys" allowEmpty
                            defaultValue={record ? record.eid : ''} validate={required}>
                <SelectInput optionText="label"/>
            </ReferenceInput>
            <BooleanInput source="c"/>
            <BooleanInput source="r"/>
            <BooleanInput source="u"/>
            <BooleanInput source="d"/>
        </SimpleForm>
    </Create>
};

export const PermissionEdit = (props) => {
    const record = props.location.data;

    return <Edit {...props} actions={null}>
        <SimpleForm redirect={'/_roles' + (record ? '/' + record.roleId : '')}>

            <ReferenceField source="roleId" reference="_roles" allowEmpty
                            defaultValue={record ? record.roleId : ''} validate={required}>
                <TextField source="name"/>
            </ReferenceField>
            <ReferenceField source="eid" reference="_entitys" allowEmpty>
                <TextField source="label"/>
            </ReferenceField>

            <BooleanInput source="c"/>
            <BooleanInput source="r"/>
            <BooleanInput source="u"/>
            <BooleanInput source="d"/>

        </SimpleForm>
    </Edit>
};