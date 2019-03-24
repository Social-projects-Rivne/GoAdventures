import axios from 'axios';
import { serverUrl } from './url.config';
import { Cookies } from 'react-cookie';

const cookies: Cookies = new Cookies();

export const createEventReq = async (data: any, categ?:string): Promise<string> => {
    return await axios.post(`${serverUrl}/event/create/${categ}`, { ...data },
        { headers: { 'Authorization': `Bearer ${cookies.get('tk879n')}`,
                    'Content-Type': 'application/json' } }).then((res) => {
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
