import axios from 'axios';
import { serverUrl } from './url.config';
import { cookies } from './cookies.service';

export const uploadGallery = async (data: any, eventId: number) => {
  return await axios
    .post(`${serverUrl}/event/gallery/add-new/${eventId}`, data, {
      headers: {
        'Authorization': `Bearer ${cookies.get('tk879n')}`,
        'Content-Type': 'application/json'
      }
    })
    .then((response) => {
      if (response.status > 200 && response.status < 300) {
        return response.data;
      } else {
        throw Error(response.data);
      }
    })
    .catch((error) => {
      console.debug(error);
      return error;
    });
};
