import axios from 'axios';
import { serverUrl } from './url.config';

export const signUp = (data?: any) => {
        // return axios.post(`${serverUrl}/auth/sign-up`, {...data}).then((res) => {
        //     console.log(res);
        // }).catch((error) => {
        //     console.error(error);
        // });
        return axios.post('https://jsonplaceholder.typicode.com/posts', {...data}).then((res) => {
            console.log(res);
        }).catch((error) => {
            console.log(error);

        });


};
