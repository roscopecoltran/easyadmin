import React from 'react'
import {
    BrowserRouter as Router,
    Route,
    Link
} from 'react-router-dom';
import MaterialUiForm from './MaterialUiForm';

import submit from './submit';
import FieldList from './FieldList';


const routes = [
    {
        path: '/',
        exact: true,
        main: () => ( <MaterialUiForm onSubmit={submit}/>)
    },
    {
        path: '/field',
        main: () => (<FieldList/>)
    }
]

const SidebarExample = () => (
    <Router>
        <div style={{display: 'flex'}}>
            <div style={{
                padding: '10px',
                width: '10%',
                background: '#f0f0f0'
            }}>
                <ul style={{listStyleType: 'none', padding: 15}}>
                    <li><Link to="/">Form</Link></li>
                    <li><Link to="/field">FieldList</Link></li>
                </ul>

                {routes.map((route, index) => (
                    <Route
                        key={index}
                        path={route.path}
                        exact={route.exact}
                    />
                ))}
            </div>

            <div style={{flex: 1, padding: '15px'}}>
                {routes.map((route, index) => (
                    // Render more <Route>s with the same paths as
                    // above, but different components this time.
                    <Route
                        key={index}
                        path={route.path}
                        exact={route.exact}
                        component={route.main}
                    />
                ))}
            </div>
        </div>
    </Router>
)

export default SidebarExample