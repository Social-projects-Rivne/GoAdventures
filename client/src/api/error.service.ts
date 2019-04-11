import { AxiosError } from 'axios';
import { ErrorMessage } from '../interfaces/ErrorMessage';

export default function(error: AxiosError) {
  if (error.response) {
    if (error.response.status === 500) {
      return {
        errorMessage: ['Oops, something went wrong, try again later']
      } as ErrorMessage;
    } else {
      return error.response.data;
    }
  } else if (error.request) {
    console.error('The request was made but no response was received');
  }
}
