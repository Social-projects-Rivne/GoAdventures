import axios, { AxiosResponse } from 'axios';
import { Cookies } from 'react-cookie';
import { EventDto } from '../interfaces/Event.dto';
import { serverUrl } from './url.config';

const cookies: Cookies = new Cookies();

export const getEventList = async (nextPage?: string | null, search?: string | null): Promise<any> => {
  const defaultUrl = '/event/all?page=0';
  // const searchUrl = `${nextPage}&search=${search}`;

  return await axios
    .get(`${serverUrl}${!!nextPage ? nextPage : defaultUrl}`, {
      headers: {
        'Authorization': `Bearer ${cookies.get('tk879n')}`,
        'Content-Type': 'application/json'
      }


    })
    .then(
      (res: AxiosResponse<EventDto[]>): any => {
        if (res.status >= 200 && res.status <= 300) {
          sessionStorage.setItem('nextpage', res.headers.nextpage);
          return res.data;
        } else {
          return { responseStatus: res.status.toString(10) };
        }
      }
    )
    .catch((error) => {
      console.debug(error);
      return error;
    });
};



export const searchForEvents = async (nextPage?: string | null, search?: string | null): Promise<any> => {
  const defaultUrl = '/event/all?page=0';
  const searchUrl = `&search=${search}`;
  console.debug(nextPage + "" + searchUrl);
  return await axios
    .get(`${serverUrl}${!!nextPage ? nextPage + "" + searchUrl : defaultUrl + "" + searchUrl}`, {
      headers: {
        'Authorization': `Bearer ${cookies.get('tk879n')}`,
        'Content-Type': 'application/json'
      }
    })
    .then(
      (res: AxiosResponse<EventDto[]>): any => {
        if (res.status >= 200 && res.status <= 300) {
          console.log(searchUrl);
          sessionStorage.setItem('nextpage', res.headers.nextpage);
          return res.data;
        } else {
          return { responseStatus: res.status.toString(10) };
        }
      }
    )
    .catch((error) => {
      console.debug(error);
      return error;
    });
};


export const getOwnerEventList = async (
  nextPage?: string | null
): Promise<any> => {
  const defaultUrl = '/profile/all-events';
  return await axios
    .get(`${serverUrl}${!!nextPage ? nextPage : defaultUrl}`, {
      headers: {
        'Authorization': `Bearer ${cookies.get('tk879n')}`,
        'Content-Type': 'application/json'
      }
    })
    .then(
      (res: AxiosResponse<EventDto[]>): any => {
        if (res.status >= 200 && res.status <= 300) {
          sessionStorage.setItem('nextpage', res.headers.nextpage);
          return res.data;
        } else {
          return { responseStatus: res.status.toString(10) };
        }
      }
    )
    .catch((error) => {
      console.debug(error);
      return error;
    });
};

export const getEventData = async (): Promise<AxiosResponse> =>
  await axios.get(`${serverUrl}/profile/getevent`, {
    headers: {
      'Authorization': `Bearer ${cookies.get('tk879n')}`,
      'Content-Type': 'application/json'
    }
  });

export const deleteEvent = async (data: number): Promise<any> =>
  await axios.delete(`${serverUrl}/event/delete`, {
    headers: {
      'EventId': data,
      'Authorization': `Bearer ${cookies.get('tk879n')}`,
      'Content-Type': 'application/json'
    }
  });

export const closeEvent = async (data: number): Promise<any> =>
  await axios.post(`${serverUrl}/event/close`, { data }, {
    headers: {
      'EventId': data,
      'Authorization': `Bearer ${cookies.get('tk879n')}`,
      'Content-Type': 'application/json'
    }
  });

export const openEvent = async (data: number): Promise<any> =>
  await axios.post(`${serverUrl}/event/open`, { data }, {
    headers: {
      'EventId': data,
      'Authorization': `Bearer ${cookies.get('tk879n')}`,
      'Content-Type': 'application/json'
    }
  });

export const updateEvent = async (data: EventDto): Promise<EventDto | object> =>
  await axios
    .put(
      `${serverUrl}/event/update/${data.id}`,
      { ...data },
      {
        headers: {
          'Authorization': `Bearer ${cookies.get('tk879n')}`,
          'Content-Type': 'application/json'
        }
      }
    )
    .then((res: AxiosResponse<EventDto>) => {
      if (res.status >= 200 && res.status <= 300) {
        return res.data;
      } else {
        throw Error('Something went wrong, try again later');
      }
    })
    .catch((err) => {
      console.error(err);
      return { responseStatus: err };
    });

export const isOwner = async (data: number): Promise<any> =>
  await axios.post(`${serverUrl}/event/isOwner`, null, {
    headers: {
      'EventId': data,
      'Authorization': `Bearer ${cookies.get('tk879n')}`,
      'Content-Type': 'application/json'
    }
  });

export const createEvent = async (
  data: any
): Promise<string> => {
  return await axios
    .post(
      `${serverUrl}/event/create`,
      { ...data },
      {
        headers: {
          'Authorization': `Bearer ${cookies.get('tk879n')}`,
          'Content-Type': 'application/json'
        }
      }
    )
    .then((res) => {
      if (res.status === 200) {
        return 'ok';
      } else {
        return res.status.toString();
      }
    })
    .catch((error) => {
      console.error(error);
      return 'server error';
    });
};

export const getEventDetail = async (topic: any): Promise<EventDto | any> => {
  return await axios.get(
    `${serverUrl}/event/event-detail/${topic}`,
  ).then((res: AxiosResponse<EventDto>) => {
    console.debug(res.data);
    return res.data;

  }).catch((error) => {
    return error;
  });
};
