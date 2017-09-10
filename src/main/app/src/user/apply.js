import React from "react";
import {
    BooleanField,
    Create,
    Datagrid,
    DisabledInput,
    Edit,
    EditButton,
    email,
    List,
    ReferenceField,
    ReferenceManyField,
    required,
    SelectArrayInput,
    SimpleForm,
    TextField,
    TextInput,
    translate,
    password,
    minLength,
    SelectInput
} from "admin-on-rest";
const location = {pathname: '/'};
export const ApplyCreate = (props) => (
    <Create resource="apply" location={location} actions={null}>
        <SimpleForm redirect='/login' submitOnEnter={true}>
            <TextInput source="username" validate={[required,minLength(6)]}/>
            <TextInput source="password" validate={[required]} type="password"/>
            <SelectArrayInput label="选择套件(正在开发中)" source="kits" choices={[
                { id: 'crm', name: 'CRM系统' },
                { id: 'hr', name: 'HR系统' },
                { id: 'oa', name: 'OA系统' },
            ]} />
            <TextInput source="mobile" />
            <TextInput source="email" validate={[email]} />
        </SimpleForm>
    </Create>
);