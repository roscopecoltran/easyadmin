import React from "react";
import {Create, email, minLength, required, SelectArrayInput, SimpleForm, TextInput} from "admin-on-rest";
const location = {pathname: '/'};
export const ApplyCreate = (props) => (
    <Create resource="apply" location={location} actions={null}>
        <SimpleForm redirect='/login' submitOnEnter={true}>
            <TextInput source="username" validate={[required, minLength(6)]}/>
            <TextInput source="password" validate={[required]} type="password"/>
            <SelectArrayInput label="选择套件(正在开发中)" source="kits" choices={[
                {id: 'crm', name: 'CRM系统'},
                {id: 'hr', name: 'HR系统'},
                {id: 'oa', name: 'OA系统'},
            ]}/>
            <TextInput source="mobile"/>
            <TextInput source="email" validate={[email]}/>
        </SimpleForm>
    </Create>
);