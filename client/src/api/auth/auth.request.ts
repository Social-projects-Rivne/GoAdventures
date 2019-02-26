import axios from 'axios';
import { serverUrl } from '../url.config';
const tempURL = 'localhost:8080';
export const signUp = (data: any) => {
    axios.post(`${tempURL}/auth/sign-up`, {...data}).then((res) => {
        return res.data;
    }).catch((error) => {
        return error;
    });
};
