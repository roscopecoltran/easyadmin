import React from "react";
import {Card, CardText} from "material-ui/Card";
import {ViewTitle} from "admin-on-rest/lib/mui";

export default () => (
    <Card>
        <ViewTitle title="Dashboard" />
        <CardText>schema settings page, these settings will affect datahive page</CardText>
    </Card>
);