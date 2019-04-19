import React, { useState, useEffect } from 'react';
import { RouteComponentProps } from 'react-router';
import { EventDetail } from '../EventsDetail/EventDetail';
import { EditEvent } from '../editEvent/EditEvent';
import { EventDto } from '../../../interfaces/Event.dto';
import { getEventDetail } from '../../../api/event.service';

interface EventProps {
  routerProps: RouteComponentProps;
}

export const Event = (props: EventProps) => {
   
  const currentUrl = document.location.href;
  const arrTopic: string[] = currentUrl.split(`detail/`);
  
  const [fetch, setFetch] = useState(false);
  const [isLoading, setIsLoading] = useState(false);
  const [edit, setEdit] = useState(false);
  const [event, setEvent] = useState({} as EventDto);

  /* https://overreacted.io/a-complete-guide-to-useeffect/ */
  console.debug(props.routerProps.location.state);

  const fetchData = async (): Promise<void> => {
    await setFetch(false);
    setEvent({ ...await getEventDetail(arrTopic[1]) } as EventDto | any)
  }
  
  useEffect(() => {
    if(!!props.routerProps.location.state) {
      console.debug('yo da')
      setEvent({
        ...props.routerProps.location.state,
      } as EventDto)
    } else {
      console.debug('yo ne')
      fetchData()
    }
    return () => {}
  }, [fetch]);


  return (
    <div className='container'>
      {edit ? (
        isLoading ? (
          <div className='spinner-border text-primary' role='status'>
            <span className='sr-only'>Loading...</span>
          </div>
        ) : (
            <EditEvent {...{ event, setEvent, setIsLoading, setEdit }} />
          )
      ) : (
          <EventDetail {...{ event, setEdit, setIsLoading }} />
        )}
    </div>
  );
};
