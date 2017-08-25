import React from "react";
import {Admin, jsonServerRestClient, Resource} from "admin-on-rest";
import zhcnMsg from "aor-language-chinese";
import {EntityCreate, EntityEdit, EntityList} from "./system/entitys";
import {FieldCreate, FieldEdit, FieldList} from "./system/fields";
import Menu from "./Menu";
import Dashboard from "./Dashboard";
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
    <Admin dashboard={Dashboard} menu={Menu} restClient={schemaRestClient} locale='zh-cn' messages={messages}>
        <Resource name="entitys" list={EntityList} create={EntityCreate} edit={EntityEdit}/>
        <Resource name="fields" list={FieldList} create={FieldCreate} edit={FieldEdit} />
    </Admin>
);

export default App;
