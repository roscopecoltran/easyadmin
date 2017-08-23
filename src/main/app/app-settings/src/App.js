import React from 'react';
import {jsonServerRestClient, Admin, Resource} from 'admin-on-rest';
import zhcnMsg from 'aor-language-chinese';
import {EntityList,EntityCreate,EntityEdit} from './system/entitys';
import {FieldList,FieldCreate,FieldEdit} from './system/fields';
import Menu from './Menu';
import routers from './routers';
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
const schemaRestClient = jsonServerRestClient(url + '/schemas');

const App = () => (
    <Admin menu={Menu} restClient={schemaRestClient} locale='zh-cn' messages={messages}>
        <Resource name="entitys" list={EntityList} create={EntityCreate} edit={EntityEdit}/>
        <Resource name="fields" list={FieldList} create={FieldCreate} edit={FieldEdit} />
    </Admin>
);

export default App;
