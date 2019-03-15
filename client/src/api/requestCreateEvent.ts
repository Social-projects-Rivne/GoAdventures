import axios from 'axios';
import { serverUrl } from './url.config';

export const createEventReq = async (data: any) : Promise<string> => {
    return await axios.post(`${serverUrl}/event/create/SUMMER`, {...data},
        { headers: {'Content-Type': 'application/json'}}).then((res) => {
        return "ok";
    }).catch((error) => {
        console.error(error);
        return "server error";
    });
};
