import axios, { AxiosResponse } from 'axios';
import { serverUrl } from './url.config';
import { cookies } from './cookies.service';
import { GalleryDto } from '../interfaces/Gallery.dto';
import errorHandle from './error.service';

export const uploadGallery = async (
  data: any,
  eventId?: number
): Promise<any> => {
  console.warn('Ce syka',eventId);
  return await axios
    .post(
      `${serverUrl}/event/gallery/add-new/${eventId ? eventId : ''}`,
      data,
      {
        headers: {
          'Authorization': `Bearer ${cookies.get('tk879n')}`,
          'Content-Type': 'application/json'
        }
      }
    )
    .then((res) => {
      return res.data;
    })
    .catch((err) => {
      console.debug(err);
      return errorHandle(err);
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
      return res.data;
    })
    .catch((err) => {
      console.debug(err);
      return errorHandle(err);
    });
};

export const deleteGallery = async (
  galleryId: number,
  mutatedGallery: GalleryDto
): Promise<any> => {
  return await axios
    .put(`${serverUrl}/event/gallery/deatach/${galleryId}`, mutatedGallery, {
      headers: {
        'Authorization': `Bearer ${cookies.get('tk879n')}`,
        'Content-Type': 'application/json'
      }
    })
    .then((res: AxiosResponse<any>) => {
      return res.data;
    })
    .catch((err) => {
      console.debug(err);
      return errorHandle(err);
    });
};
