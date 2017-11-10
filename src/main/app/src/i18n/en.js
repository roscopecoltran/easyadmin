export default {
    easyadmin: {
        settings: 'Settings',
    },
    resources: {
        _users: {
            name: 'User',
            fields: {
                username: 'username',
                password: 'password',
                roles: 'roles',
                enabled: 'enabled'
            }
        },
        _roles: {
            name: 'Role',
            fields: {
                name: 'rolename'
            },
            createAuth: 'createAuth',
            authlist: 'authlist'
        },
        _entitys: {
            name: 'Entity',
            fields: {
                label: 'label',
                showInMenu: 'showInMenu',
            }
        },
        _fields: {
            name: 'Field',
            fields: {
                label: 'label',
                component: 'component',
                required: 'required',
                eid: 'entity',
                minValue: 'minValue',
                maxValue: 'maxValue',
                defaultValue: 'defaultValue',
                inputType: 'inputType',
                maxLength: 'maxLength',
                reference: 'reference',
                referenceOptionText: 'referenceOptionText',
                showInList:'showInList',
                showInFilter:'showInFilter',
                showInCreate:'showInCreate',
                showInEdit:'showInEdit',
                showInShow:'showInShow',
            }
        },
        _permission: {
            name: 'auth',
            fields: {
                roleId: 'role',
                eid: 'entity',
                c: 'create',
                r: 'read',
                u: 'edit',
                d: 'delete',
            }
        },
        _datasource: {
            name: 'DataSource'
        },
        apply: {
            name: 'apply for test',
            fields: {
                username: 'username',
                password: 'password',
                mobile: 'mobile',
                email: 'email'
            }
        }
    }
};