import { AxiosError } from 'axios';
import { ErrorMessage } from '../interfaces/ErrorMessage';

export default function (error: AxiosError): ErrorMessage {
  if (error.response) {
    if (error.response.status === 500) {
      return {
        errorMessage: ['Oops, something went wrong, try again later']
      } as ErrorMessage;
    } else {
      console.debug(error.response.data);
      return {
        errorMessage: [`Oops, request is malformed`]
      } as ErrorMessage;
    }
  } else if (error.request) {
    console.error('The request was made but no response was received');
    return {
      errorMessage: [
        'Oops, the request was made but no response was received, check your internet conection'
      ]
    } as ErrorMessage;
  }
  return {
    errorMessage: [`Oops, something went wrong, code ${error.code}`]
  } as ErrorMessage;
}
