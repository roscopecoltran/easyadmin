export default [{
    label: '文章',
    name: 'posts',
    fields: [{
        'component': 'Boolean',
        'label': '是否',
        'name': 'Boolean',
        'defaultValue': true,
        'showInList': true
    }, {
        'component': 'NullableBoolean',
        'label': '是否',
        'name': 'NullableBoolean',
        'defaultValue': true,
        'showInList': true
    }, {
        'component': 'Date',
        'label': 'Date',
        'name': 'Date',
        'defaultValue': '2017-08-17',
        'showInList': true

    }// }, {
    //     'component': 'File',
    //     'label': '文件',
    //     'name': 'File',
    //     'type': '*',
    //     'showInList': true}
    // , {
    //     'component': 'Image',
    //     'label': 'Image',
    //     'name': 'Image',
    //     'showInList': true,
    // }
    ,{    'component': 'CheckboxGroup', 'label': 'CheckboxGroup', 'name': 'CheckboxGroup', 'choices': [
            {id: 'programming', name: 'Programming'},
            {id: 'lifestyle', name: 'Lifestyle'},
            {id: 'photography', name: 'Photography'},
        ], 'defaultValue': ['programming'],
        'showInList': true
    }, {
        'component': 'Autocomplete', 'name': 'Autocomplete', 'choices': [
            {id: 'programming', name: 'Programming'},
            {id: 'lifestyle', name: 'Lifestyle'},
            {id: 'photography', name: 'Photography'},
        ], 'defaultValue': 'programming',
        'showInList': true
    }, {
        'component': 'LongText',
        'label': 'LongText',
        'name': 'LongText',
        'defaultValue': '1',
        'textIndex': true
    }, {
        'component': 'Number',
        'label': 'Number',
        'name': 'Number',
        'defaultValue': 1,
    }, {
        'component': 'RadioButtonGroup',
        'label': 'RadioButtonGroup',
        'name': 'RadioButtonGroup',
        'choices': [
            {id: 'programming', name: 'Programming'},
            {id: 'lifestyle', name: 'Lifestyle'},
            {id: 'photography', name: 'Photography'},
        ], 'defaultValue': 'programming'
    }, {
        'component': 'Text',
        'label': 'Text',
        'name': 'Text',
        'type': 'email',
        'required': true,
        'maxLength': 10,
        'defaultValue': 'progr@1.co'
    }, {
        'component': 'Reference',
        'label': 'Reference',
        'name': 'Reference',
        'reference': 'users',
        'referenceOptionText': 'name',
        'defaultValue': 1,
        'showInList': false
    }, {
        'component': 'ReferenceArray',
        'label': 'ReferenceArray',
        'name': 'ReferenceArray',
        'reference': 'users',
        'referenceOptionText': 'name',
        // 'defaultValue': [1, 2],
        'showInList': true
    }, {
        'component': 'RichText',
        'label': 'RichText',
        'name': 'RichText',
        'defaultValue': 'RichText',
        'showInList': true
    }, {
        'component': 'Select', 'name': 'Select', 'choices': [
            {id: 'programming', name: 'Programming'},
            {id: 'lifestyle', name: 'Lifestyle'},
            {id: 'photography', name: 'Photography'},
        ], 'defaultValue': 'lifestyle',
        'showInList': true
    }, {
        'component': 'SelectArray', 'name': 'SelectArray', 'choices': [
            {id: 'programming', name: 'Programming'},
            {id: 'lifestyle', name: 'Lifestyle'},
            {id: 'photography', name: 'Photography'},
        ],
        'showInList': true
    }]
}, {
    label: '用户',
    name: 'users',
    fields: [{
        'component': 'Text',
        'label': 'Text',
        'name': 'name',
        'type': 'email',
        'required': true,
        'maxLength': 20,
        'defaultValue': 'data@cloud.com',
        'showInList': true
    }]
}, {
    label: '测试报告',
    name: 'bug',
    fields: [{
        'component': 'Text',
        'label': '报告标题',
        'name': 'title',
        'required': true,
        'showInList': true
    },{
        'component': 'RichText',
        'label': '报告内容',
        'name': 'name',
        'required': true,
        'showInList': true
    }]
}];