import React, {Component} from 'react';
import {jsonServerRestClient, Admin, Resource} from 'admin-on-rest';
import {CRUDList, CRUDCreate, CRUDEdit, CRUDShow, CRUDDelete} from './crud';
import zhcnMsg from 'aor-language-chinese';
import Dashboard from './Dashboard';
import localSchemas from './schemas';
import AdminBuilder from './AdminBuilder';
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

class App extends Component {
    constructor(props) {
        super(props);
    }

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

        return <AdminBuilder {...this.props} schemas={this.state.schemas} dashboard={Dashboard}
                             restClient={dataRestClient} locale='zh-cn' messages={messages}/>
    }
};

export default App;