/**
 * Convert a `File` object returned by the upload input into
 * a base 64 string. That's easier to use on FakeRest, used on
 * the ng-admin example. But that's probably not the most optimized
 * way to do in a production database.
 */
const convertFileToBase64 = file => new Promise((resolve, reject) => {
    const reader = new FileReader();
    reader.readAsDataURL(file['rawFile']);
    reader.onload = () => resolve({src: reader.result, title: file.title});
    reader.onerror = reject;
});

/**
 * For posts update only, convert uploaded image in base 64 and attach it to
 * the `picture` sent property, with `src` and `title` attributes.
 */
export const addUploadCapabilities = requestHandler => (type, resource, params) => {
    if (type === 'UPDATE' || type === 'CREATE') {
        for (var key of Object.keys(params.data)) {
            if (params.data[key] instanceof Array && params.data[key][0]['rawFile'] instanceof File) {
                // only freshly dropped pictures are instance of File
                // const formerPictures = params.data.pictures.filter(p => !(p instanceof File));
                const newPictures = params.data[key];

                return Promise.all(newPictures.map(convertFileToBase64))
                    .then(base64Pictures => base64Pictures.map(picture64 => ({
                        src: picture64.src,
                        title: picture64.title,
                    })))
                    .then(transformedNewPictures => requestHandler(type, resource, {
                        ...params,
                        data: {
                            ...params.data,
                            [key]: [...transformedNewPictures],
                        },
                    }));
            }
        }
    }

    return requestHandler(type, resource, params);
};

export default addUploadCapabilities;