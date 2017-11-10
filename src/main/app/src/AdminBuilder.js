import React, {Component} from "react";
import {Admin, Resource} from "admin-on-rest";
import {CRUDCreate, CRUDDelete, CRUDEdit, CRUDList, CRUDShow} from "./crud/crud";
import {EntityCreate, EntityEdit, EntityList} from "./schema/entitys";
import {FieldCreate, FieldEdit, FieldList} from "./schema/fields";
import {UserCreate, UserEdit, UserList} from "./user/user";
import {RoleCreate, RoleEdit, RoleList} from "./user/role";
import {DataSourceCreate, DataSourceEdit,DataSourceList} from "./user/datasource";
import {PermissionCreate, PermissionEdit} from "./user/permission";
import {ApplyCreate} from './user/apply';
export default class extends Component {

    render() {
        let props = {...this.props};

        return props.isLogin ? (
            <Admin {...props}>
                {this.props.schemas.map(resource =>
                    <Resource key={resource.name} label={resource.label} name={resource.name} options={resource}
                              list={CRUDList}
                              showInMenu={resource.showInMenu}
                              create={resource.crud.includes('c') ? CRUDCreate : null}
                              edit={resource.crud.includes('u') ? CRUDEdit : null}
                              show={CRUDShow} remove={resource.crud.includes('c') ? CRUDDelete : null}/>
                )}
                <Resource name="_entitys" list={EntityList} create={EntityCreate} edit={EntityEdit}/>
                <Resource name="_fields" list={FieldList} create={FieldCreate} edit={FieldEdit}/>
                <Resource name="_users" list={UserList} create={UserCreate} edit={UserEdit}/>
                <Resource name="_roles" list={RoleList} create={RoleCreate} edit={RoleEdit}/>
                <Resource name="_permission" create={PermissionCreate} edit={PermissionEdit}/>
                <Resource name="_datasource" list={DataSourceList} create={DataSourceCreate} edit={DataSourceEdit}/>
                <Resource name="_apply" create={ApplyCreate}/>
            </Admin>
        ) : <Admin {...props}></Admin>;
    }
}