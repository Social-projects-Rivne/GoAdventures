import axios, { AxiosResponse } from 'axios';
import { Cookies } from 'react-cookie';
import { serverUrl } from './url.config';

const cookies: Cookies = new Cookies();

const currentDate: Date = new Date();
const expiresIn: Date = new Date(currentDate.setDate(currentDate.getDate() + 10));
const cookieMaxAge: number = 864000;
function setCookie(res: AxiosResponse) {
  cookies.set('tk879n', res.headers.authorization.replace('Bearer ', ''), {
    expires: expiresIn,
    maxAge: cookieMaxAge,
    path: '/',
  });
}
export const getUserData = async (): Promise<AxiosResponse> =>
  axios.get(`${serverUrl}/profile/page`, {
    headers: {
      'Authorization': `Bearer ${cookies.get('tk879n')}`,
      'Content-Type': 'application/json'
    }
  });

export const changeUserData = async (data: any): Promise<any> => {
  return axios
    .put(
      `${serverUrl}/profile/edit-profile`,
      { ...data },
      {
        headers: {
          'Authorization': `Bearer ${cookies.get('tk879n')}`,
          'Content-Type': 'application/json'
        }
      }
    )
    .then((res) => {
      console.debug(res);
      setCookie(res);
      return res;
    });
};
