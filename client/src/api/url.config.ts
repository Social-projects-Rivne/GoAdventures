export const serverUrl: string | undefined = (() => {
    if(process.env.NODE_ENV === 'production') {
        return process.env.SERVER_URL_PROD;
    } else {
        return process.env.SERVER_URL_DEV;
    }
})();





