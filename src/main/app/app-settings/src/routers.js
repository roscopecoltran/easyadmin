import React from "react";
import {Route} from "react-router-dom";
import {EntityList} from "./system/entitys";

export default [
    <Route exact path="/entitys" component={EntityList} />,
];