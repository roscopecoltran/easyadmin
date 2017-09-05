import React, {Component} from 'react';
import {jsonServerRestClient, fetchUtils, Login, Logout} from 'admin-on-rest';
import zhcnMsg from 'aor-language-chinese';
import Dashboard from './Dashboard';
import AdminBuilder from './AdminBuilder';
import {url} from './constants';
import restClientRouter from 'aor-rest-client-router';
import Menu from './Menu';
import {authClient,httpClient} from './authClient';
/**
 * i18n
 * @type {{zh-cn}}
 */
const messages = {
    'zh-cn': zhcnMsg,
};

/**
 * rest client
 */
const restRouter = restClientRouter({
    rules: [
        ['_entitys', 'schema'],
        ['_fields', 'schema'],
        ['_users', 'user'],
        ['_roles', 'role'],
        ['_permission', 'permission'],
        ['*', 'data']
    ],
    services: {
        schema: jsonServerRestClient(url + '/schemas', httpClient),
        user : jsonServerRestClient(url + '/user', httpClient),
        role : jsonServerRestClient(url + '/role', httpClient),
        permission:  jsonServerRestClient(url + '/permission', httpClient),
        data: jsonServerRestClient(url + '/api', httpClient),
    }
});
/**
 * add file support
 */
// const uploadCapableClient = addUploadFeature(restClient);
const isLogin = localStorage.getItem('token');
class App extends Component {
    state = {schemas: null};

    componentDidMount() {
        if (isLogin)
            this.getSchemas();
    }

    getSchemas() {
        httpClient(url + `/schemas/_entitys`)
            .then((response) => {
                return response.json
            })
            .then((json) => {
                this.setState({
                    schemas: json
                })
            });
    }

    render() {
        if (isLogin && null === this.state.schemas) return <div>Loading...</div>;
        return <AdminBuilder {...this.props} title="DataCloud" isLogin={isLogin} authClient={authClient} loginPage={Login}
                             logoutButton={Logout} menu={Menu}
                             schemas={this.state.schemas} dashboard={Dashboard}
                             restClient={restRouter} locale='zh-cn' messages={messages}/>
    }
}
;

export default App;