import axios from 'axios';
import { serverUrl } from './url.config';

export const signUp = async (data?: any) => {
        return await axios.post(`${serverUrl}/auth/sign-up`, {...data},
         { headers: {'Content-Type': 'application/json'}});
};

export const signIn = async (data?: any) => {
    return await axios.post(`${serverUrl}/auth/sign-in`, {...data}, { headers: {'Content-Type': 'application/json'}})
    .then((res) => {
        localStorage.setItem('tkn879', res.headers.authorization);
    }).catch((error) => {
        console.error(error);
    }

    );
};
