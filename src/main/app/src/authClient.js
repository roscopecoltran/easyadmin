// in authClient.js
import {AUTH_CHECK, AUTH_ERROR, AUTH_LOGIN, AUTH_LOGOUT, fetchUtils} from "admin-on-rest";
import {AUTH_GET_PERMISSIONS} from "aor-permissions";
import {url} from "./constants";
/**
 * add token before request
 * @param url
 * @param options
 * @returns {*}
 */
export const httpClient = (url, options = {}) => {
    if (!options.headers) {
        options.headers = new Headers({Accept: 'application/json'});
    }
    const token = localStorage.getItem('token');
    options.headers.set('Authorization', `Bearer ${token}`);
    return fetchUtils.fetchJson(url, options);
}

export const authClient = (type, params) => {
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
            .then(({token, authorityName}) => {
                localStorage.setItem('token', token);
                localStorage.setItem('permissions', authorityName);
                window.location.replace('/'); // after logged in , the admin page need to refresh to load schemas ,ugly , hope for awsome
            });
    }
    if (type === AUTH_LOGOUT) {
        localStorage.removeItem('token');
        localStorage.removeItem('permissions');
        return Promise.resolve();
    }
    if (type === AUTH_ERROR) {
        const {status} = params;
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