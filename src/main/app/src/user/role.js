import React from "react";
import {
    Create,
    Datagrid,
    DisabledInput,
    BooleanField,
    Edit,
    EditButton,
    List,
    SimpleForm,
    TextField,
    TextInput,
    SelectArrayInput,
    ReferenceManyField,
    ReferenceField
} from "admin-on-rest";
import HardwareSecurity from 'material-ui/svg-icons/hardware/security';
import LinkButton  from './LinkButton';
export const RoleList = (props) => (
    <List {...props} pagination={null} perPage={9999} title='角色'>
        <Datagrid>
            <TextField source="name" label="角色名"/>
            <EditButton/>
        </Datagrid>
    </List>
);

export const RoleCreate = (props) => (
    <Create {...props} title='新建角色'>
        <SimpleForm>
            <TextInput source="name" label="角色名"/>
        </SimpleForm>
    </Create>
);

export const RoleEdit = (props) => (
    <Edit {...props} title='编辑角色'>
        <SimpleForm>
            <TextInput source="name" label="角色名"/>
            <LinkButton
                label="新建授权"
                icon={<HardwareSecurity />}
                style={{overflow: 'inherit'}}
            />
            <ReferenceManyField label="权限列表" reference="_permission" target="roleId">
                <Datagrid>
                    <ReferenceField label="对象" source="eid" reference="_entitys">
                        <TextField source="label"/>
                    </ReferenceField>
                    <BooleanField source="c" label="创建"/>
                    <BooleanField source="r" label="读取"/>
                    <BooleanField source="u" label="编辑"/>
                    <BooleanField source="d" label="删除"/>
                    <EditButton/>
                </Datagrid>
            </ReferenceManyField>
        </SimpleForm>
    </Edit>
);