import axios from 'axios';
import { serverUrl } from './url.config';

export const signUp = async (data: any) => {
    return await axios.post(`${serverUrl}/auth/sign-up`, {...data},
         { headers: {'Content-Type': 'application/json'}}).then((res) => {
             return res.statusText === 'OK' && res.status === 200;
          }).catch((error) => {
            console.error(error);
            return false;
          });
};

export const signIn = async (data: any) => {
    return await axios.post(`${serverUrl}/auth/sign-in`, {...data}, { headers: {'Content-Type': 'application/json'}})
    .then((res) => {
        if (res.status === 200  && res.headers.hasOwnProperty('authorization')) {
            localStorage.setItem('tkn879', res.headers.authorization.replace('Bearer', ''));
            return true;
        } else {
            return false;
        }
    }).catch((error) => {
        console.error(error);
        return false;
    });
};


export const confirmAccount = async (param: string): Promise<boolean> => {
    return await axios.get(`${serverUrl}/auth/confirm-account${param}`, {headers :
        {'Content-Type': 'application/text'}})
        .then((res) => {
            // choto ne DRY :(
            if (res.status === 200 && res.headers.hasOwnProperty('authorization')) {
                localStorage.setItem('tkn879', res.headers.authorization.replace('Bearer', ''));
                return true;
            } else {
                return false;
            }
        }).catch((error) => {
            console.debug(error);
            return false;
        });
};
