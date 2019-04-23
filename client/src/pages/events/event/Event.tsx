import React, { useState } from "react";
import { RouteComponentProps } from "react-router";
import EventDetail from "../EventsDetail/EventDetail";
import { EditEvent } from "../editEvent/EditEvent";
import { EventDto } from "../../../interfaces/Event.dto";
interface EventProps {
  routerProps: RouteComponentProps;
}

export const Event = (props: EventProps) => {
  const [isLoading, setIsLoading] = useState(false);
  const [edit, setEdit] = useState(false);
  const [event, setEvent] = useState({
    ...props.routerProps.location.state
  } as EventDto);
  return (
    <div className="container">
      {edit ? (
        isLoading ? (
          <div className="spinner-border text-primary" role="status">
            <span className="sr-only">Loading...</span>
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
