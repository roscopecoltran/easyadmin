import React from 'react';
import ReactDOM from 'react-dom';
import { Provider } from 'react-redux';
import getMuiTheme from 'material-ui/styles/getMuiTheme';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import store from './store';
import MaterialUiForm from './MaterialUiForm';

import submit from './submit';
import SidebarExample from './SidebarExample';
const rootEl = document.getElementById('root');

// ReactDOM.render(
//   <Provider store={store}>
//     <MuiThemeProvider muiTheme={getMuiTheme()}>
//       <div style={{ padding: 15 }}>
//         <h2>EasyForm</h2>
//         <MaterialUiForm onSubmit={submit} />
//       </div>
//     </MuiThemeProvider>
//   </Provider>,
//   rootEl,
// );
ReactDOM.render(
  <Provider store={store}>
    <MuiThemeProvider muiTheme={getMuiTheme()}>
      <div style={{ padding: 15 }}>
        <h2>EasyForm</h2>
          <SidebarExample/>
      </div>
    </MuiThemeProvider>
  </Provider>,
    rootEl,
);