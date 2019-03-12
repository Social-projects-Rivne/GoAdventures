import axios from 'axios';
import { serverUrl } from './url.config';

export const signUp = async (data: any) => {
    return await axios.post(`${serverUrl}/auth/sign-up`, {...data},
         { headers: {'Content-Type': 'application/json'}}).then((res) => {
             return res.status === 200;
          }).catch((error) => {
            console.error(error);
            return false;
          });
};

export const signIn = async (data: any) => {
    return await axios.post(`${serverUrl}/auth/sign-in`, {...data}, { headers: {'Content-Type': 'application/json'}})
    .then((res) => {
// <<<<<<< Updated upstream
        if (res.status === 200  && res.headers.hasOwnProperty('authorization')) {
            localStorage.setItem('tkn879', res.headers.authorization.replace('Bearer ', ''));
            return true;
        } else {
            localStorage.setItem('token', res.headers.token);
            return false;
        }
    }).catch((error) => {
        console.error(error);
        return false;
    });
};


export const confirmAccount = async (data: any): Promise<boolean> => {
    const { param } = data;
    return await axios.get(`${serverUrl}/auth/confirm-account${param}`, {headers :
        {'Content-Type': 'application/text'}})
        .then((res) => {
            // choto ne DRY :(
            if (res.status === 200 && res.headers.hasOwnProperty('authorization')) {
                localStorage.setItem('tkn879', res.headers.authorization.replace('Bearer ', ''));
                return true;
            } else {
                return false;
            }
        }).catch((error) => {
            console.debug(error);
            return false;
        });
};

export const sentRecoveryEmail = async (data: any): Promise<boolean> => {
    const { param } = data;
    return await axios.get(`${serverUrl}/auth/recovery${param}`, {headers :
            {'Content-Type': 'application/text'}})
        .then((res) => {
            if (res.status === 200) {
                console.log("email recovery sent");
                return true;
            } else {
                return false;
            }
        }).catch((error) => {
            console.debug(error);
            return false;
        });
};


export const signOut = async (): Promise<boolean> => {
    return await axios.put(`${serverUrl}/auth/sign-out`, null, {headers: {
        Authorization: `Bearer ${localStorage.getItem('tkn879')}`
    }})
    .then((res) => {
        if (res.status === 200) {
            localStorage.removeItem('tkn879');
            return true;
        } else {
            return false;
        }
    }).catch((error) => {
        console.error(`Something went wrong ${error}`);
        return false;
    });
};
