import React from 'react';
import {jsonServerRestClient, Admin, Resource} from 'admin-on-rest';
import {addUploadCapabilities} from './addUploadFeature';
import {CRUDList, CRUDCreate, CRUDEdit, CRUDShow, CRUDDelete} from './crud';
import schemas from './schemas';

const renderResources = (member, index) => (
    <Resource key={index} name={member.name} options={member} list={CRUDList} create={CRUDCreate} edit={CRUDEdit}
              show={CRUDShow} remove={CRUDDelete}/>
);
const restClient = jsonServerRestClient('http://localhost:8080/api');
const uploadCapableClient = addUploadCapabilities(restClient);
const App = () => (
    <Admin restClient={restClient}>
        {schemas.map(renderResources)}
        <Resource name=""/>
    </Admin>
);

export default App;