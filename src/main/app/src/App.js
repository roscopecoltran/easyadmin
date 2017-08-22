import React from 'react';
import {jsonServerRestClient, Admin, Resource, MenuItemLink} from 'admin-on-rest';
import {CRUDList, CRUDCreate, CRUDEdit, CRUDShow, CRUDDelete} from './crud';
import schemas from './schemas';
import zhcnMsg from 'aor-language-chinese';
import Dashboard from './Dashboard';
// import addUploadFeature from './addUploadFeature';
import {EntityList, EntityCreate, EntityEdit, EntityShow} from './system/entitys';
import {FieldList, FieldCreate, FieldEdit, FieldShow} from './system/fields';
import customRoutes from './routers';
import Menu from './Menu';

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

const schemaRestClient = jsonServerRestClient(url + "/schema");
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
    <Admin dashboard={Dashboard}  menu={Menu} restClient={dataRestClient} locale='zh-cn' messages={messages}>
        {schemas.map(renderResources)}
        <Resource name="entitys" list={EntityList} create={EntityCreate} edit={EntityEdit} show={EntityShow}/>
        <Resource name="fields" list={FieldList} create={FieldCreate} edit={FieldEdit} />
    </Admin>
);

export default App;