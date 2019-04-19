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

  const [isLoading, setIsLoading] = useState(true);
  const [edit, setEdit] = useState(false);
  const [event, setEvent] = useState({} as EventDto);

  /* https://overreacted.io/a-complete-guide-to-useeffect/ */
  console.debug('deamn', event);

  useEffect(() => {
    const fetchData = async (): Promise<void> => {
      setEvent({ ...(await getEventDetail(arrTopic[1])) } as EventDto | any);
      setIsLoading(false);
    };
    if (Object.keys(event).length > 0) {
      console.debug('yo da');
      setEvent({
        ...props.routerProps.location.state
      } as EventDto);
    } else {
      console.debug('yo ne');
      fetchData();
    }
    return () => {};
  }, []);

  return (
    <div className='container'>
      {Object.keys(event).length === 0 ? (
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
