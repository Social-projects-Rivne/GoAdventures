import axios from 'axios';
import { serverUrl } from './url.config';

export const signUp = (data: any) => {
    return async (dispatch: any) => {
        return await axios.post(`${serverUrl}/auth/sign-up`, {...data}).then((res) => {
            console.log(res);
        }).catch((error) => {
            console.log(error);
        });
    };

};
