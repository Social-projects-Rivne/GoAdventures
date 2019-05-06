import axios, { AxiosResponse } from 'axios';
import { cookies } from './cookies.service';
import { EventDto } from '../interfaces/Event.dto';
import { serverUrl } from './url.config';
import { date } from 'yup';
import errorHandle from './error.service';


export const getEventList = async (
  nextPage?: string | null,
  search?: string | null
): Promise<any> => {
  const defaultUrl = '/event/all?page=0';
  // const searchUrl = `${nextPage}&search=${search}`;

  return axios
    .get(`${serverUrl}${!!nextPage ? nextPage : defaultUrl}`, {
      headers: {
        'Authorization': `Bearer ${cookies.get('tk879n')}`,
        'Content-Type': 'application/json'
      }
    })
    .then(
      (res: AxiosResponse<EventDto[]>): any => {
        sessionStorage.setItem('nextpage', res.headers.nextpage);
        return res.data;
      }
    )
    .catch((error) => {
      console.debug(error);
      return errorHandle(error);
    });
};

export const searchForEvents = async (
  nextPage?: string | null,
  search?: string | null,
  category?: string | null
): Promise<any> => {
  const defaultUrl = '/event/all?page=0';
  const searchUrl = `&search=${search}`;
  const categoryUrl = `&filter=${category}`;
  console.debug(nextPage + '' + searchUrl);
  return axios
    .get(
      `${serverUrl}${
      !!nextPage
        ? nextPage + '' + searchUrl
        : defaultUrl + '' + searchUrl + '' + categoryUrl
      }`,
      {
        headers: {
          'Authorization': `Bearer ${cookies.get('tk879n')}`,
          'Content-Type': 'application/json'
        }
      }
    )
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
  return axios
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
  axios.get(`${serverUrl}/profile/getevent`, {
    headers: {
      'Authorization': `Bearer ${cookies.get('tk879n')}`,
      'Content-Type': 'application/json'
    }
  });

export const deleteEvent = async (data: number): Promise<any> =>
  axios.delete(`${serverUrl}/event/delete`, {
    headers: {
      'EventId': data,
      'Authorization': `Bearer ${cookies.get('tk879n')}`,
      'Content-Type': 'application/json'
    }
  });

export const closeEvent = async (data: number): Promise<any> =>
  axios.post(
    `${serverUrl}/event/close`,
    { data },
    {
      headers: {
        'EventId': data,
        'Authorization': `Bearer ${cookies.get('tk879n')}`,
        'Content-Type': 'application/json'
      }
    }
  );

export const openEvent = async (data: number): Promise<any> =>
  axios.post(
    `${serverUrl}/event/open`,
    { data },
    {
      headers: {
        'EventId': data,
        'Authorization': `Bearer ${cookies.get('tk879n')}`,
        'Content-Type': 'application/json'
      }
    }
  );

export const updateEvent = async (data: EventDto): Promise<any> => {
  console.debug('request data', data);
  return axios
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
};

export const isOwner = async (data: number): Promise<any> =>
  axios.post(`${serverUrl}/event/isOwner`, null, {
    headers: {
      'EventId': data,
      'Authorization': `Bearer ${cookies.get('tk879n')}`,
      'Content-Type': 'application/json'
    }
  });
export const isSubscribe = async (data: number): Promise<any> =>
  axios.get(`${serverUrl}/event/is-subscriber`, {
    headers: {
      'EventId': data,
      'Authorization': `Bearer ${cookies.get('tk879n')}`,
      'Content-Type': 'application/json'
    }
  });

export const subscribe = async (data: number): Promise<any> =>
  axios.post(`${serverUrl}/event/subscribe`, null, {
    headers: {
      'EventId': data,
      'Authorization': `Bearer ${cookies.get('tk879n')}`,
      'Content-Type': 'application/json'
    }
  });

export const unSubscribe = async (data: number): Promise<any> =>
  axios.post(`${serverUrl}/event/unsubscribe`, null, {
    headers: {
      'EventId': data,
      'Authorization': `Bearer ${cookies.get('tk879n')}`,
      'Content-Type': 'application/json'
    }
  });


export const createEvent = async (data: any): Promise<string> => {
  return axios
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
  return await axios
    .get(`${serverUrl}/event/event-detail/${topic}`)
    .then((res: AxiosResponse<EventDto>) => {
      console.debug(res.data);
      return res.data;
    })
    .catch((error) => {
      return error;
    });
};

export const scheduleEmail = async (data: any, Role: string, timeToAlert: string): Promise<any> => {
  await axios.post(
    `${serverUrl}/scheduleEmail`,
    { ...data },
    {
      headers: {
        'Authorization': `Bearer ${cookies.get('tk879n')}`,
        'Role': Role,
        "timeToAlert": timeToAlert,
        'Content-Type': 'application/json'
      }
    }
  )

};
export const deleteScheduleEmail = async (data: any): Promise<any> => {
  await axios.post(
    `${serverUrl}/deleteSchedule`,
    { ...data },
    {
      headers: {
        'Authorization': `Bearer ${cookies.get('tk879n')}`,
        'Content-Type': 'application/json'
      }
    }
  );
}
export const updateSchedule = async (data: any): Promise<any> => {
  await axios.post(
    `${serverUrl}/updateSchedule`,
    { ...data }

  )
}
