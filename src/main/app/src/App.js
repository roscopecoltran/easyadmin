import React from 'react';
import {jsonServerRestClient, Admin, Resource} from 'admin-on-rest';

import {CRUDList, CRUDCreate} from './crud';

const resources = [{
    label: '文章',
    name: 'posts',
    fields: [{
        'component': 'Boolean',
        'label': 'Allow comments?',
        'name': 'commentable'
    },{
        'component': 'Date',
        'label': '发布时间',
        'name': 'published_at'
    },{
        'component': 'File',
        'label': '文件',
        'name': 'files'
    }, {
        'component': 'CheckboxGroup', 'name': 'category', 'choices': [
            {id: 'programming', name: 'Programming'},
            {id: 'lifestyle', name: 'Lifestyle'},
            {id: 'photography', name: 'Photography'},
        ]
    }, {
        'component': 'Autocomplete', 'name': 'title', 'choices': [
            {id: 'programming', name: 'Programming'},
            {id: 'lifestyle', name: 'Lifestyle'},
            {id: 'photography', name: 'Photography'},
        ]
    }]
}];
const renderResources = (member, index) => (
    <Resource key={index} name={member.name} options={member} list={CRUDList} create={CRUDCreate}/>
);
const App = () => (
    <Admin restClient={jsonServerRestClient('http://jsonplaceholder.typicode.com')}>
        {resources.map(renderResources)}
    </Admin>
);

export default App;