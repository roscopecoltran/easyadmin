import React, {Component} from 'react';
import {Admin, Resource} from 'admin-on-rest';
import {CRUDList, CRUDCreate, CRUDEdit, CRUDShow, CRUDDelete} from './crud';
import {EntityCreate, EntityEdit, EntityList} from "./schema/entitys";
import {FieldCreate, FieldEdit, FieldList} from "./schema/fields";
import {UserList, UserCreate, UserEdit} from './user/user';
import {RoleList, RoleCreate, RoleEdit} from './user/role';
import {PermissionCreate, PermissionEdit} from './user/permission';
export default class extends Component {

    render() {
        let props = {...this.props};

        return props.isLogin ? (
            <Admin {...props}>
                {this.props.schemas.map(resource =>
                    <Resource key={resource.id} label={resource.label} name={resource.id} options={resource}
                              list={CRUDList}
                              create={resource.crud.includes('c') ? CRUDCreate : null}
                              edit={resource.crud.includes('u') ? CRUDEdit : null}
                              show={CRUDShow} remove={resource.crud.includes('c') ? CRUDDelete : null}/>
                )}
                <Resource name="_entitys" list={EntityList} create={EntityCreate} edit={EntityEdit}/>
                <Resource name="_fields" list={FieldList} create={FieldCreate} edit={FieldEdit}/>
                <Resource name="_users" list={UserList} create={UserCreate} edit={UserEdit}/>
                <Resource name="_roles" list={RoleList} create={RoleCreate} edit={RoleEdit}/>
                <Resource name="_permission" create={PermissionCreate} edit={PermissionEdit}/>
            </Admin>
        ) : <Admin {...props}></Admin>;
    }
}