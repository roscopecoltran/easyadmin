import React, {Component} from "react";
import PropTypes from "prop-types";
import {connect} from "react-redux";
import FlatButton from "material-ui/FlatButton";
import {showNotification as showNotificationAction} from "admin-on-rest";
import {push as pushAction} from "react-router-redux";
import {Link} from "react-router-dom";
class LinkButton extends Component {

    render() {
        const {label, icon, record} = this.props;
        return <FlatButton
            primary
            label={label}
            icon={icon}
            containerElement={<Link to={{pathname: '/_permission/create', data: {roleId: record.id}}}/>}
            style={{overflow: 'inherit'}}
        />;
    }
}

LinkButton.propTypes = {
    push: PropTypes.func,
    record: PropTypes.object,
    showNotification: PropTypes.func,
    label: PropTypes.string,
};

export default connect(null, {
    showNotification: showNotificationAction,
    push: pushAction,
})(LinkButton);