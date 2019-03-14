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
