import React from 'react';
import FlatButton from 'material-ui/FlatButton';
import { Link } from 'react-router-dom';
import { translate } from 'admin-on-rest';
import { stringify } from 'query-string';


const LinkToRelatedFields = ({ record, translate }) => (
    <FlatButton
        primary
        // label={translate('resources.segments.fields.customers')}
        label="字段"
        containerElement={<Link to={{
            pathname: "/fields",
            query : { entity: record.id },
            search: stringify({ page: 1, perPage: 9999,filter: JSON.stringify({ entity: record.id }) }),
        }} />}
    />
);

export default translate(LinkToRelatedFields);