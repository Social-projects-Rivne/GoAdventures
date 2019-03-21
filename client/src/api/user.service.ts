import axios, { AxiosResponse } from 'axios';
import { Cookies } from 'react-cookie';
import { serverUrl } from './url.config';

const cookies: Cookies = new Cookies();

export const getUserData = async (): Promise<AxiosResponse> => await axios.get(`${serverUrl}/profile/page`, {
    headers: {
        'Authorization': `Bearer ${cookies.get('tkn879')}`,
        'Content-Type': 'application/json'
    }
});

export const changeUserData = async (data: any): Promise<AxiosResponse> => await axios.post(`${serverUrl}/profile/edit-profile`, { data }, {
    headers: {
        'Authorization': `Bearer ${cookies.get('tk879n')}`,
        'Content-Type': 'application/json'
    }

});









