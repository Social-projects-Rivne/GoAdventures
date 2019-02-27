export const serverUrl: string | undefined = (() => {
    if(process.env.NODE_ENV === 'production') {
        return process.env.REACT_APP_SERVER_URL_PROD;
    } else {
        return process.env.REACT_APP_SERVER_URL_DEV;
    }
})();





