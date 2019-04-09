import { AxiosResponse } from 'axios';

export default function(response: AxiosResponse<any>) {
  return response.status >= 200 && response.status <= 300;
}
