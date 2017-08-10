import React from 'react';
import ReactDOM from 'react-dom';
import { Provider } from 'react-redux';
import { Values } from 'redux-form-website-template';
import getMuiTheme from 'material-ui/styles/getMuiTheme';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import store from './store';
import showResults from './showResults';
import MaterialUiForm from './MaterialUiForm';

const rootEl = document.getElementById('root');

ReactDOM.render(
  <Provider store={store}>
    <MuiThemeProvider muiTheme={getMuiTheme()}>
      <div style={{ padding: 15 }}>
        <h2>Material UI Example</h2>
        <MaterialUiForm onSubmit={showResults} />
        {/*<Values form="MaterialUiForm" />*/}
      </div>
    </MuiThemeProvider>
  </Provider>,
  rootEl,
);
