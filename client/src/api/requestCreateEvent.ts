import axios from 'axios';
import { serverUrl } from './url.config';

export const createEventReq = async (data: any) : Promise<boolean> => {
    return await axios.post(`${serverUrl}/event/create/SUMMER`, {...data},
        { headers: {'Content-Type': 'application/json'}}).then((res) => {
        return res.status === 200;
    }).catch((error) => {
        console.error(error);
        return false;
    });
};
