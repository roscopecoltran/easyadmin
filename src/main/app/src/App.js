import React from 'react';
import {jsonServerRestClient, Admin, Resource} from 'admin-on-rest';
import {CRUDList, CRUDCreate, CRUDEdit, CRUDShow, CRUDDelete} from './crud';
import schemas from './schemas';
import zhcnMsg from 'aor-language-chinese';

const messages = {
    'zh-cn': zhcnMsg,
};
const renderResources = (member, index) => (
    <Resource key={index} label={member.label} name={member.name} options={member} list={CRUDList} create={CRUDCreate} edit={CRUDEdit}
              show={CRUDShow} remove={CRUDDelete}/>
);
const restClient = jsonServerRestClient('http://192.168.1.10:8080/api');
const App = () => (
    <Admin restClient={restClient} locale='zh-cn' messages={messages}>
        {schemas.map(renderResources)}
        <Resource name=""/>
    </Admin>
);

export default App;