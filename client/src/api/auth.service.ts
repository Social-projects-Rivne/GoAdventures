import axios from 'axios';
import { AxiosResponse } from 'axios';
import { cookies } from './cookies.service';
import { UserDto } from '../interfaces/User.dto';
import { serverUrl } from './url.config';
import errorHandle from './error.service';

const currentDate: Date = new Date();
const expiresIn: Date = new Date(
  currentDate.setDate(currentDate.getDate() + 10)
);
const cookieMaxAge: number = 864000;

function setCookie(res: AxiosResponse) {
  cookies.set('tk879n', res.headers.authorization.replace('Bearer ', ''), {
    expires: expiresIn,
    maxAge: cookieMaxAge,
    path: '/'
  });
}

export const signUp = async (data: UserDto): Promise<string> => {
  return axios
    .post(
      `${serverUrl}/auth/sign-up`,
      { ...data },
      { headers: { 'Content-Type': 'application/json' } }
    )
    .then((res) => {
      return res.status === 200 ? 'ok' : res.status.toString();
    })
    .catch((error) => {
      console.error(error);
      return 'Server error';
    });
};

export const signIn = async (data: UserDto): Promise<string> => {
  return axios
    .post(
      `${serverUrl}/auth/sign-in`,
      { ...data },
      { headers: { 'Content-Type': 'application/json' } }
    )
    .then((res) => {
      if (res.status === 200 && res.headers.hasOwnProperty('authorization')) {
        setCookie(res);
        return 'ok';
      } else {
        return res.status.toString();
      }
    })
    .catch((error) => {
      console.error(error);
      return 'Incorect email or password';
    });
};

export const confirmAccount = async (data: any): Promise<string> => {
  const { param } = data;
  return axios
    .get(`${serverUrl}/auth/confirm-account${param}`, {
      headers: { 'Content-Type': 'application/text' }
    })
    .then((res: AxiosResponse) => {
      if (res.status === 200 && res.headers.hasOwnProperty('authorization')) {
        setCookie(res);
        return 'ok';
      } else {
        return res.status.toString();
      }
    })
    .catch((error) => {
      console.debug(error);
      return 'Server error';
    });
};

export const sentRecoveryEmail = async (data: any): Promise<any> => {
  return axios
    .get(`${serverUrl}/auth/recovery${data}`, {
      headers: { 'Content-Type': 'application/text' }
    })
    .catch((error) => {
      console.debug(error);
      return errorHandle(error);
    });
};

export const signOut = async (): Promise<string> => {
  return axios
    .put(`${serverUrl}/auth/sign-out`, null, {
      headers: {
        Authorization: `Bearer ${cookies.get('tk879n')}`
      }
    })
    .then((res: AxiosResponse) => {
      if (res.status === 200) {
        cookies.remove('tk879n');
        return 'ok';
      } else {
        return res.status.toString();
      }
    })
    .catch((error) => {
      console.error(`Something went wrong ${error}`, error);
      return 'Server error';
    });
};
