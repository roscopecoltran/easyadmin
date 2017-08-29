import React, {Component} from 'react';
import {jsonServerRestClient} from 'admin-on-rest';
import zhcnMsg from 'aor-language-chinese';
import Dashboard from './Dashboard';
import AdminBuilder from './AdminBuilder';
import {url} from './constants';
import restClientRouter from 'aor-rest-client-router';
import Menu from './Menu';
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
        ['*', 'data']
    ],
    services: {
        schema: jsonServerRestClient(url + '/schemas'),
        data: jsonServerRestClient(url + '/api'),
    }
});
/**
 * add file support
 */
// const uploadCapableClient = addUploadFeature(restClient);

class App extends Component {
    state = {schemas: null};

    componentDidMount() {
        this.getSchemas();
    }

    getSchemas() {
        fetch(url + `/schemas/_entitys`)
            .then((response) => {
                return response.json()
            })
            .then((json) => {
                this.setState({
                    schemas: json
                })
            });
    }

    render() {
        if (null === this.state.schemas) return <div>Loading...</div>;

        return <AdminBuilder {...this.props} menu={Menu} schemas={this.state.schemas} dashboard={Dashboard}
                             restClient={restRouter} locale='zh-cn' messages={messages}/>
    }
}
;

export default App;