import React from 'react';
import {jsonServerRestClient, Admin, Resource} from 'admin-on-rest';

import {CRUDList, CRUDCreate, CRUDEdit, CRUDShow,CRUDDelete} from './crud';
import schemas from './schemas';

const renderResources = (member, index) => (
    <Resource key={index} name={member.name} options={member} list={CRUDList} create={CRUDCreate} edit={CRUDEdit}
              show={CRUDShow} remove={CRUDDelete}/>
);
const App = () => (
    <Admin restClient={jsonServerRestClient('http://localhost:8080')}>
        {schemas.map(renderResources)}
        <Resource name=""/>
    </Admin>
);

export default App;