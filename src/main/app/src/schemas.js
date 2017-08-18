export default [{
    label: '文章',
    name: 'posts',
    fields: [{
        'component': 'Boolean',
        'label': 'Boolean',
        'name': 'Boolean',
        'defaultValue': true,
        'showInList':true
    }, {
        'component': 'NullableBoolean',
        'label': 'NullableBoolean',
        'name': 'NullableBoolean',
        'defaultValue': true,
        'showInList':true
    }, {
        'component': 'Date',
        'label': 'Date',
        'name': 'Date',
        'defaultValue': '2017-08-17',
        'showInList':true
    }, {
        'component': 'File',
        'label': '文件',
        'name': 'File',
        'type': '*',
        'showInList':true
    }, {
        'component': 'Image',
        'label': 'Image',
        'name': 'Image',
        'showInList':true
    }, {
        'component': 'CheckboxGroup', 'label': 'CheckboxGroup', 'name': 'CheckboxGroup', 'choices': [
            {id: 'programming', name: 'Programming'},
            {id: 'lifestyle', name: 'Lifestyle'},
            {id: 'photography', name: 'Photography'},
        ], 'defaultValue': ['programming']
    }, {
        'component': 'Autocomplete', 'name': 'Autocomplete', 'choices': [
            {id: 'programming', name: 'Programming'},
            {id: 'lifestyle', name: 'Lifestyle'},
            {id: 'photography', name: 'Photography'},
        ], 'defaultValue': 'programming',
        'showInList':true
    }, {
        'component': 'LongText',
        'label': 'LongText',
        'name': 'LongText',
        'defaultValue': '1'
    }, {
        'component': 'Number',
        'label': 'Number',
        'name': 'Number',
        'defaultValue': 1
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
        'defaultValue': 'programming'
    }, {
        'component': 'Reference',
        'label': 'Reference',
        'name': 'Reference',
        'reference': 'users',
        'referenceOptionText': 'name',
        'defaultValue': 1
    }, {
        'component': 'ReferenceArray',
        'label': 'ReferenceArray',
        'name': 'ReferenceArray',
        'reference': 'users',
        'referenceOptionText': 'name',
        'defaultValue': [1, 2]
    }, {
        'component': 'RichText',
        'label': 'RichText',
        'name': 'RichText',
        'defaultValue': 'RichText'
    }, {
        'component': 'Select', 'name': 'Select', 'choices': [
            {id: 'programming', name: 'Programming'},
            {id: 'lifestyle', name: 'Lifestyle'},
            {id: 'photography', name: 'Photography'},
        ], 'defaultValue': 'lifestyle'
    }, {
        'component': 'SelectArray', 'name': 'SelectArray', 'choices': [
            {id: 'programming', name: 'Programming'},
            {id: 'lifestyle', name: 'Lifestyle'},
            {id: 'photography', name: 'Photography'},
        ], 'defaultValue': ['lifestyle']
    }]
}, {
    label: '用户',
    name: 'users',
    fields:[{
            'component': 'Text',
            'label': 'Text',
            'name': 'name',
            'type': 'email',
            'required': true,
            'maxLength': 20,
            'defaultValue': 'data@cloud.com',
        'showInList':true
    }]
}];