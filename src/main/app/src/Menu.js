import React from "react";
import {DashboardMenuItem, translate} from "admin-on-rest";
import SettingsIcon from "material-ui/svg-icons/action/settings";
import MenuItem from "material-ui/MenuItem";
import {connect} from "react-redux";
import {Link} from "react-router-dom";
import compose from "recompose/compose";
import DashboardIcon from "material-ui/svg-icons/action/dashboard";
import {authClient} from "./authClient";
import {WithPermission} from "aor-permissions";
const Menu = ({resources, onMenuTap, translate, logout}) => (
    <div>
        <WithPermission authClient={authClient} value={['ROLE_USER', 'ROLE_ADMIN']}>
            <DashboardMenuItem onTouchTap={onMenuTap}/>
            {resources.filter(resource => !resource.name.startsWith('_') && resource.name !== 'apply' && resource.showInMenu).map((resource) => (
                <MenuItem
                    key={resource.name}
                    containerElement={<Link to={'/' + resource.name}/>}
                    primaryText={resource.label}
                    leftIcon={<DashboardIcon/>}
                    onTouchTap={onMenuTap}
                />
            ))}
        </WithPermission>
        <WithPermission authClient={authClient} value={['ROLE_ADMIN']}>
            <MenuItem
                containerElement={<Link to="/_datasource"/>}
                primaryText={translate('resources._datasource.name')}
                leftIcon={<SettingsIcon/>}
                onTouchTap={onMenuTap}
            />
            <MenuItem
                containerElement={<Link to="/_entitys"/>}
                primaryText={translate('easyadmin.settings')}
                leftIcon={<SettingsIcon/>}
                onTouchTap={onMenuTap}
            />
            <MenuItem
                containerElement={<Link to="/_users"/>}
                primaryText={translate('resources._users.name')}
                leftIcon={<SettingsIcon/>}
                onTouchTap={onMenuTap}
            />
            <MenuItem
                containerElement={<Link to="/_roles"/>}
                primaryText={translate('resources._roles.name')}
                leftIcon={<SettingsIcon/>}
                onTouchTap={onMenuTap}
            />
        </WithPermission>
        <WithPermission authClient={authClient} value={['ROLE_USER', 'ROLE_ADMIN']}>
            {logout}
        </WithPermission>
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