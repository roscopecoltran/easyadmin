import React from 'react';
import { Route } from 'react-router-dom';
import {ApplyCreate} from './user/apply';

export default [
    <Route exact path="/apply/create" component={ApplyCreate} />,
];