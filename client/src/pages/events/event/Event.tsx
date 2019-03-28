import React from 'react';
import { RouteComponentProps } from 'react-router';
interface EventProps {
  routerProps: RouteComponentProps;
}

const Event = (props: EventProps) => {
  const event = props.routerProps.location.state;
  return <div />;
};

export default Event;
