import React, { Component } from 'react';
import './UserEventList.scss';
import { EventDto } from '../../interfaces/Event.dto';


export const UserEventList = (props: EventDto) => {
    return (
        <div className="list-group">
            <a href="#" className="list-group-item list-group-item-action active">
                Topic: {props.topic}
            </a>
            <a href="#" className="list-group-item list-group-item-action">
                decription: {props.description}

            </a>
            <a href="#" className="list-group-item list-group-item-action disabled">
                start date: {props.start_date}
            </a>
        </div>






    );


}