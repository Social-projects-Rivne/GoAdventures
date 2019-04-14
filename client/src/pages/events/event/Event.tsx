import React, { useState, useEffect } from 'react';
import { RouteComponentProps } from 'react-router';
import { EventDetail } from '../EventsDetail/EventDetail';
import { EditEvent } from '../editEvent/EditEvent';
import { EventDto } from '../../../interfaces/Event.dto';
import { getEventDetail } from '../../../api/event.service';
import { async } from 'rxjs/internal/scheduler/async';
interface EventProps {
  routerProps: RouteComponentProps;
}

export const Event = (props: EventProps) => {
  const currentUrl = document.location.href;
  const arrTopic: string[] = currentUrl.split(`detail/`);
  const [g, setG] = useState();

  const [isLoading, setIsLoading] = useState(false);
  const [edit, setEdit] = useState(false);
  const [event, setEvent] = useState(props.routerProps.location.state ? {
    ...props.routerProps.location.state,
  } as EventDto :
    { ...getEventDetail(arrTopic[1]) } as EventDto | any
  );

  debugger
  useEffect(() => {
    setG('Pzdc');
  }, []);


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
