import axios from 'axios';
import { serverUrl } from './url.config';
import { cookies } from './cookies.service';
import { GalleryDto } from '../interfaces/Gallery.dto';

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
      if (res.status >= 200 && res.status <= 300) {
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

export const alterGallery = async (
  galleryId: number,
  mutatedGallery: GalleryDto
): Promise<any> => {
  return await axios.put(`${serverUrl}/remove/${galleryId}`, mutatedGallery, {
    headers: {
      'Authorization': `Bearer ${cookies.get('tk879n')}`,
      'Content-Type': 'application/json'
    }
  });
};
