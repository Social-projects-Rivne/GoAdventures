import axios from 'axios';
import { serverUrl } from './url.config';
import { cookies } from './cookies.service';
import errorHandle from './error.service';
import { AxiosResponse, AxiosError } from 'axios';
import { FeedbackDTO } from '../interfaces/Feedback.dto';

export const getFeedbackRequest = async (eventId: number): Promise<any> => {
  return await axios
    .get(`${serverUrl}/feedback/get-feedback/${eventId}`, {
      headers: {
        Authorization: `Bearer ${cookies.get('tk879n')}`
      }
    })
    .then((res: AxiosResponse<any>) => {
      return res.data;
    })
    .catch((err: AxiosError) => {
      console.debug(err);
      return errorHandle(err);
    });
};

export const addFeedbackRequest = async (
  eventId: number,
  feedback: FeedbackDTO
): Promise<any> => {
  return await axios.post(
    `${serverUrl}/feedback/add-feedback/${eventId}`,
    feedback,
    {
      headers: {
        Authorization: `Bearer ${cookies.get('tk879n')}`
      }
    }
  );
};