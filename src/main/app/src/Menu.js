import React from "react";
import {DashboardMenuItem, translate} from "admin-on-rest";
import SettingsIcon from "material-ui/svg-icons/action/settings";
import MenuItem from "material-ui/MenuItem";
import {connect} from "react-redux";
import {Link} from "react-router-dom";
import compose from "recompose/compose";
import DashboardIcon from 'material-ui/svg-icons/action/dashboard';
import { WithPermission } from 'aor-permissions';
import {authClient} from './authClient';
const Menu = ({resources, onMenuTap, logout}) => (
    <div>
        <DashboardMenuItem onTouchTap={onMenuTap}/>
        {resources.filter(resource => !resource.name.startsWith('_')).map((resource)=>(
            <MenuItem
                key={resource.name}
                containerElement={<Link to={'/'+resource.name}/>}
                primaryText={resource.label}
                leftIcon={<DashboardIcon/>}
                onTouchTap={onMenuTap}
            />
        ))}
        <WithPermission authClient={authClient} value="true">
            <MenuItem
                containerElement={<Link to="/_entitys"/>}
                primaryText='系统设置'
                leftIcon={<SettingsIcon/>}
                onTouchTap={onMenuTap}
            />
            <MenuItem
                containerElement={<Link to="/_users"/>}
                primaryText='用户'
                leftIcon={<SettingsIcon/>}
                onTouchTap={onMenuTap}
            />
            <MenuItem
                containerElement={<Link to="/_roles"/>}
                primaryText='角色'
                leftIcon={<SettingsIcon/>}
                onTouchTap={onMenuTap}
            />
        </WithPermission>
        {logout}
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