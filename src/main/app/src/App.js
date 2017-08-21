import React from 'react';
import {jsonServerRestClient, Admin, Resource} from 'admin-on-rest';
import {CRUDList, CRUDCreate, CRUDEdit, CRUDShow, CRUDDelete} from './crud';
import schemas from './schemas';
import zhcnMsg from 'aor-language-chinese';
import Dashboard from './Dashboard';
// import addUploadFeature from './addUploadFeature';



/**
 * i18n
 * @type {{zh-cn}}
 */
const messages = {
    'zh-cn': zhcnMsg,
};

/**
 * profile for dev
 * @type {string}
 */
var url = "/api";
if (process.env.NODE_ENV === 'development') {
    url = "http://" + window.location.hostname + ":8080/api";
}

/**
 * rest client
 */
const restClient = jsonServerRestClient(url);

/**
 * add file support
 */
// const uploadCapableClient = addUploadFeature(restClient);

const renderResources = (member, index) => (
    <Resource key={index} label={member.label} name={member.name} options={member} list={CRUDList} create={CRUDCreate}
              edit={CRUDEdit}
              show={CRUDShow} remove={CRUDDelete}/>
);

const App = () => (
    <Admin dashboard={Dashboard} restClient={restClient} locale='zh-cn' messages={messages}>
        {schemas.map(renderResources)}
    </Admin>
);

export default App;