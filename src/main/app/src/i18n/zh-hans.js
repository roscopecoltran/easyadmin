export default {
    easyadmin: {
        settings: '设置',
    },
    resources: {
        _users: {
            name: '用户',
            fields: {
                username: '用户名',
                password: '密码',
                roles: '角色',
                enabled: '是否有效'
            }
        },
        _roles: {
            name: '角色',
            fields: {
                name: '角色名'
            },
            createAuth: '新建授权',
            authlist: '权限列表'
        },
        _entitys: {
            name: '对象',
            fields: {
                label: '标签',
                showInMenu: '是否显示在菜单栏',
            }
        },
        _fields: {
            name: '字段',
            fields: {
                label: '标签',
                component: '组件类型',
                required: '是否必填',
                eid: '对象',
                minValue: '最小值',
                maxValue: '最大值',
                defaultValue: '默认值',
                inputType: '文本类型',
                maxLength: '最大长度',
                reference: '引用对象',
                referenceOptionText: '引用对象显示字段',
                showInList:'列表',
                showInFilter:'筛选',
                showInCreate:'新增',
                showInEdit:'编辑',
                showInShow:'详细页',
            }
        },
        _permission: {
            name: '授权',
            fields: {
                roleId: '角色',
                eid: '对象',
                c: '创建',
                r: '读取',
                u: '编辑',
                d: '删除',
            }
        },
        _datasource: {
            name:'数据源'
        },
        apply:{
            name:'账号',
            fields:{
                username:'用户名（例如：手机、邮箱）',
                password:'密码',
                mobile:'手机',
                email:'邮箱'
            }
        }
    }
};