import React from "react";
import {
    BooleanField,
    Create,
    Datagrid,
    Edit,
    EditButton,
    List,
    ReferenceField,
    ReferenceManyField,
    SimpleForm,
    TextField,
    TextInput
} from "admin-on-rest";
import HardwareSecurity from "material-ui/svg-icons/hardware/security";
import LinkButton from "./LinkButton";
export const RoleList = (props) => (
    <List {...props} pagination={null} perPage={9999}>
        <Datagrid>
            <TextField source="name"/>
            <EditButton/>
        </Datagrid>
    </List>
);

export const RoleCreate = (props) => (
    <Create {...props}>
        <SimpleForm>
            <TextInput source="name"/>
        </SimpleForm>
    </Create>
);
export const RoleEdit = (props) => (
    <Edit {...props}>
        <SimpleForm>
            <TextInput source="name"/>
            <LinkButton
                label={'新增授权'}
                icon={<HardwareSecurity />}
                style={{overflow: 'inherit'}}
            />
            <ReferenceManyField label={'resources._roles.authlist'} reference="_permission" target="roleId">
                <Datagrid>
                    <ReferenceField label={'resources._entitys.name'} source="eid" reference="_entitys">
                        <TextField source="label"/>
                    </ReferenceField>
                    <BooleanField source="c"/>
                    <BooleanField source="r"/>
                    <BooleanField source="u"/>
                    <BooleanField source="d"/>
                    <EditButton/>
                </Datagrid>
            </ReferenceManyField>
        </SimpleForm>
    </Edit>
);