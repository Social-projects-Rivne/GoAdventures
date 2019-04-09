import axios, { AxiosResponse } from 'axios';
import { serverUrl } from './url.config';
import { cookies } from './cookies.service';
import { GalleryDto } from '../interfaces/Gallery.dto';
import statusCheck from './statusCheck.service';

export const uploadGallery = async (
  data: any,
  eventId: number
): Promise<any> => {
  return await axios
    .post(`${serverUrl}/event/gallery/add-new/${eventId}`, data, {
      headers: {
        'Authorization': `Bearer ${cookies.get('tk879n')}`,
        'Content-Type': 'application/json'
      }
    })
    .then((res) => {
      if (statusCheck(res)) {
        return res.data;
      } else {
        throw Error(res.data);
      }
    })
    .catch((error) => {
      console.debug(...error);
      return error;
    });
};

export const getGallery = async (galleryId: number) => {
  return await axios
    .get(`${serverUrl}/event/gallery/get/${galleryId}`, {
      headers: {
        'Authorization': `Bearer ${cookies.get('tk879n')}`,
        'Content-Type': 'application/json'
      }
    })
    .then((res: AxiosResponse<any>) => {
      if (statusCheck(res)) {
        return res.data;
      } else {
        throw new Error(res.data);
      }
    })
    .catch((err) => {
      console.debug(err);
      return err;
    });
};

export const alterGallery = async (
  galleryId: number,
  mutatedGallery: GalleryDto
): Promise<any> => {
  console.debug('request', mutatedGallery);
  return await axios.put(
    `${serverUrl}/event/gallery/remove/${galleryId}`,
    mutatedGallery,
    {
      headers: {
        'Authorization': `Bearer ${cookies.get('tk879n')}`,
        'Content-Type': 'application/json'
      }
    }
  );
};
