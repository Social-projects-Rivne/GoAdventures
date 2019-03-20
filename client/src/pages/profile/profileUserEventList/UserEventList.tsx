import React from 'react';
import { EventDto } from '../../../interfaces/Event.dto';
import './UserEventList.scss';

export const UserEventList = (props: EventDto) => {
    return (
        <div className='page-content-wrapper'>
            <div className='col-lg-12'>
                <div className='list-group'>
                    <h2 className='list-group-item'> Your events </h2>
                    <a href='#' className='list-group-item list-group-item-action active'>
                        Topic: {props.topic}
                    </a>
                    <a href='#' className='list-group-item list-group-item-action disabled'>
                        decription: {props.description}
                    </a>
                    <a href='#' className='list-group-item list-group-item-action disabled'>
                        start date: {props.startDate}
                    </a>
                </div>
            </div>
        </div>
    );
};
