import axios, { AxiosResponse } from 'axios';
import { Cookies } from 'react-cookie';
import { EventDto } from '../interfaces/Event.dto';
import { serverUrl } from './url.config';

const cookies: Cookies = new Cookies();

export const eventList = async (): Promise<AxiosResponse> => await axios.get(`${serverUrl}/event/all/`, {
    headers: {
        'Authorization': `Bearer ${cookies.get(('tk879n'))}`,
        'Content-Type': 'application/json'
    }
});


export const getEventData = async (): Promise<AxiosResponse> => await axios.get('http://localhost:8080/profile/getevent', {
    headers: {
        'Authorization': `Bearer ${cookies.get(('tk879n'))}`,
        'Content-Type': 'application/json'

    }
});

export const createEvent = async (data: any, categ?:string): Promise<string> => {
    return await axios.post(`${serverUrl}/event/create/${categ}`, { ...data },
        { headers: { 'Content-Type': 'application/json' } }).then((res) => {
            if (res.status === 200) {
                return 'ok';
            } else {
                return res.status.toString();
            }
        }).catch((error) => {
            console.error(error);
            return 'server error';
        });
};
