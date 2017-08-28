const api_url = function () {
    let api_url = "http://" + window.location.host;
    if (process.env.NODE_ENV == 'development') {
        api_url = "http://localhost:8080"
    }
    return api_url;
}

export const url = api_url();