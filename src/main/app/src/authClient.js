// in authClient.js
import {AUTH_LOGIN, AUTH_LOGOUT, AUTH_CHECK, AUTH_ERROR} from 'admin-on-rest';
import {AUTH_GET_PERMISSIONS} from 'aor-permissions';
import jwtDecode from 'jwt-decode';
import {url} from './constants';
export default (type, params) => {
    if (type === AUTH_LOGIN) {
        const {username, password} = params;
        const request = new Request(url + "/auth", {
            method: 'POST',
            body: JSON.stringify({username, password}),
            headers: new Headers({'Content-Type': 'application/json'}),
        })
        return fetch(request)
            .then(response => {
                if (response.status < 200 || response.status >= 300) {
                    throw new Error(response.statusText);
                }
                return response.json();
            })
            .then(({token, permissions}) => {
                const decoded = jwtDecode(token);
                localStorage.setItem('token', token);
                localStorage.setItem('permissions', decoded.permissions);
            });
    }
    if (type === AUTH_LOGOUT) {
        localStorage.removeItem('token');
        return Promise.resolve();
    }
    if (type === AUTH_ERROR) {
        const { status } = params;
        if (status === 401 || status === 403) {
            localStorage.removeItem('token');
            return Promise.reject();
        }
        return Promise.resolve();
    }
    if (type === AUTH_CHECK) {
        return localStorage.getItem('token') ? Promise.resolve() : Promise.reject();
    }

    if (type === AUTH_GET_PERMISSIONS) {
        return Promise.resolve(localStorage.getItem('permissions'));
    }

    return Promise.reject('Unkown method');
};