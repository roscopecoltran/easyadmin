export default [{
    label: '文章',
    name: 'posts',
    fields: [{
        'component': 'Boolean',
        'label': 'Allow comments?',
        'name': 'commentable',
        'defaultValue': true,
    }, {
        'component': 'NullableBoolean',
        'label': 'Allow comments?',
        'name': 'NullableBoolean',
        'defaultValue': true
    }, {
        'component': 'Date',
        'label': '发布时间',
        'name': 'published_at',
        'defaultValue': '2017-08-17'
    }, {
        'component': 'File',
        'label': '文件',
        'name': 'files',
        'type': '*'
    }, {
        'component': 'Image',
        'label': 'Image',
        'name': 'Image'
    }, {
        'component': 'CheckboxGroup', 'label': 'CheckboxGroup', 'name': 'category', 'choices': [
            {id: 'programming', name: 'Programming'},
            {id: 'lifestyle', name: 'Lifestyle'},
            {id: 'photography', name: 'Photography'},
        ], 'defaultValue': ['programming']
    }, {
        'component': 'Autocomplete', 'name': 'title', 'choices': [
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
}];