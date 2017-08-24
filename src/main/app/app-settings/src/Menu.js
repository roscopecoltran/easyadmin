import React from 'react';
import {translate,DashboardMenuItem } from 'admin-on-rest';
import SettingsIcon from 'material-ui/svg-icons/action/settings';
import MenuItem from 'material-ui/MenuItem';
import {connect} from 'react-redux';
import {Link} from 'react-router-dom';
import compose from 'recompose/compose';
const Menu = ({resources, onMenuTap, logout}) => (
    <div>
        <DashboardMenuItem onTouchTap={onMenuTap} />
        <MenuItem
            containerElement={<Link to="/entitys"/>}
            primaryText='Settings'
            leftIcon={<SettingsIcon />}
            onTouchTap={onMenuTap}
        />
    </div>
);

const enhance = compose(
    connect(state => ({
        theme: state.theme,
        locale: state.locale,
    })),
    translate,
);
export default enhance(Menu);