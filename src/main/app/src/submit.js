import { SubmissionError } from 'redux-form';

var url = "/data/mutation";
if (process.env.NODE_ENV === 'development') {
    url = "http://localhost:8080/" + url;
}

export default (async function submit(values) {
    window.alert(`You submitted:\n\n${JSON.stringify(values, null, 2)}`);
    var formData  = new FormData();

    for(var name in values) {
        formData.append(name, values[name]);
    }

    fetch(url, {
        method: 'POST',
        body: formData
    }).then(function (response) {
    });
});