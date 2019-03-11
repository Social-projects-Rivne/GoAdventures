import React, { Component } from 'react';
import { EventDto } from '../../interfaces/Event.dto';
import { Events } from '../../pages/events/Events';


export const EventsListBuild = (props: EventDto) => {
    return(
        <h1>{props.topic}</h1>
    );

    
}