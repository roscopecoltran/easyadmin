import React, {Component} from "react";
import PropTypes from "prop-types";
import {connect} from "react-redux";
import IconButton from "material-ui/IconButton";
import NotificationSync from "material-ui/svg-icons/notification/sync";
import ThumbUp from 'material-ui/svg-icons/action/thumb-up';
import {showNotification as showNotificationAction} from "admin-on-rest";
import {push as pushAction} from "react-router-redux";
import {url} from "../constants";
import {httpClient} from "./../authClient";
class SyncButton extends Component {
    handleSync = () => {
        const { record, showNotification} = this.props;
        const updatedRecord = {...record, is_approved: true};
        httpClient(url + `/schemas/sync/${record.id}`, {method: 'PUT', body: updatedRecord})
            .then(() => {
                showNotification('Sync Completed!');
                window.location.replace('/');
            })
            .catch((e) => {
                console.error(e);
                showNotification('Error: comment not approved', 'warning')
            });
    }

    handleSetCurrentDs = () => {
        const { record, showNotification} = this.props;
        const updatedRecord = {...record, is_approved: true};
        httpClient(url + `/schemas/resetCurrentDs/${record.id}`, {method: 'PUT', body: updatedRecord})
            .then(() => {
                showNotification('Reset Completed!');
                window.location.replace('/');
            })
            .catch((e) => {
                console.error(e);
                window.location.replace('/');
            });
    }

    render() {
        const { record} = this.props;
        return (
            <span>
                <IconButton onClick={this.handleSync} disabled={!record.current || record.type==='mongo'}><NotificationSync
                    color="#00bcd4"/></IconButton>
                <IconButton onClick={this.handleSetCurrentDs} disabled={record.current}><ThumbUp
                    color="#00bcd4"/></IconButton>
            </span>
        );
    }
}

SyncButton.propTypes = {
    push: PropTypes.func,
    record: PropTypes.object,
    showNotification: PropTypes.func,
};

export default connect(null, {
    showNotification: showNotificationAction,
    push: pushAction,
})(SyncButton);