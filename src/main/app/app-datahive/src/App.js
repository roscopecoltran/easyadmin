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
var url = "http://" + window.location.hostname;
if (process.env.NODE_ENV === 'development') {
    url = url + ":8080";
}

/**
 * rest client
 */
const dataRestClient = jsonServerRestClient(url + '/api');

/**
 * add file support
 */
// const uploadCapableClient = addUploadFeature(restClient);

const renderResources = (member, index) => {
    const c = member.crud.includes('c');
    const u = member.crud.includes('u');
    const d = member.crud.includes('d');
    return <Resource key={index} label={member.label} name={member.name} options={member} list={CRUDList}
                     create={c ? CRUDCreate : null}
                     edit={u ? CRUDEdit : null}
                     show={CRUDShow} remove={d ? CRUDDelete : null}/>;
};

const App = () => (
    <Admin dashboard={Dashboard}  restClient={dataRestClient} locale='zh-cn' messages={messages}>
        {schemas.map(renderResources)}
    </Admin>
);

export default App;