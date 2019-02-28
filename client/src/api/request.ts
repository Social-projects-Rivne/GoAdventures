import axios from 'axios';
import { serverUrl } from './url.config';

export const signUp = async (data?: any) => {
        await axios.post(`${serverUrl}/auth/sign-up`, {...data},
         { headers: {'Content-Type': 'application/json'}}).then((res) => {
            console.log(res);
        }).catch((error) => {
            console.error(error);
        });
};
