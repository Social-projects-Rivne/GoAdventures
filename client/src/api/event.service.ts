import axios, { AxiosResponse } from 'axios';
import { Cookies } from 'react-cookie';
import { serverUrl } from './url.config';

const cookies: Cookies = new Cookies();

export const eventList = async (): Promise<AxiosResponse> =>
  await axios.get(`${serverUrl}/event/all/`, {
    headers: {
      'Authorization': `Bearer ${cookies.get('tk879n')}`,
      'Content-Type': 'application/json'
    }
  });

export const getEventData = async (): Promise<AxiosResponse> =>
  await axios.get(`${serverUrl}/profile/getevent`, {
    headers: {
      'Authorization': `Bearer ${cookies.get('tk879n')}`,
      'Content-Type': 'application/json'
    }
  });
