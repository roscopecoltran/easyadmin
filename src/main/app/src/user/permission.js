import React from "react";
import {
    BooleanField,
    BooleanInput,
    Create,
    Datagrid,
    Edit,
    EditButton,
    FilterButton,
    List,
    NumberInput,
    ReferenceInput,
    required,
    SelectField,
    SelectInput,
    SimpleForm,
    TextField,
    TextInput,
    DateInput,
    ReferenceField
} from "admin-on-rest";

export const PermissionCreate = (props) => {
    const record = props.location.data;

    return <Create {...props} actions={null} title="新增授权">
        <SimpleForm redirect={'/_roles'+ (record ? '/' + record.roleId : '')}>

            <ReferenceInput label="角色" source="roleId" reference="_roles" allowEmpty
                            defaultValue={record ? record.roleId : ''} validate={required}>
                <SelectInput optionText="name"/>
            </ReferenceInput>
            <ReferenceInput label="对象" source="eid" reference="_entitys" allowEmpty
                            defaultValue={record ? record.eid : ''} validate={required}>
                <SelectInput optionText="label"/>
            </ReferenceInput>
            <BooleanInput label="创建" source="c" />
            <BooleanInput label="读取" source="r" />
            <BooleanInput label="编辑" source="u" />
            <BooleanInput label="删除" source="d" />
        </SimpleForm>
    </Create>
};

export const PermissionEdit= (props) => {
    const record = props.location.data;

    return <Edit {...props} actions={null} title="编辑授权">
        <SimpleForm redirect={'/_roles'+ (record ? '/' + record.roleId : '')}>

            <ReferenceField label="角色" source="roleId" reference="_roles" allowEmpty
                            defaultValue={record ? record.roleId : ''} validate={required}>
                <TextField source="name"/>
            </ReferenceField>
            <ReferenceField label="对象" source="eid" reference="_entitys" allowEmpty>
                <TextField source="label"/>
            </ReferenceField>

            <BooleanInput label="创建" source="c" />
            <BooleanInput label="读取" source="r" />
            <BooleanInput label="编辑" source="u" />
            <BooleanInput label="删除" source="d" />

        </SimpleForm>
    </Edit>
};