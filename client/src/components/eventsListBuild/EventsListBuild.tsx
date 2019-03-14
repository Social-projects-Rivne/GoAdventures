import React from 'react';
import { Redirect } from 'react-router';
import { EventDto } from '../../interfaces/Event.dto';
import './EventsListBuild.scss';


export const EventsListBuild = (props: EventDto) => {
    return (
        <div className='col card event_card'>
            <img className='card-img-top'
                src='https://botw-pd.s3.amazonaws.com/styles/logo-thumbnail/s3/0016/1400/brand.gif?itok=AelJnUfh'
                alt='Card image cap'></img>
            <div className='card-body'>
                <h5 className='card-title'>{props.topic}</h5>
                <div className='row'>
                    <h6 className='col-6'> {props.location}</h6>
                    <div className='col-6'>
                        <p>Category</p>
                    </div>
                </div>
                <button onClick={() =>
                    (<Redirect push to={{
                        pathname: `/events/detail/${props.topic}`,
                        state: {
                            ...props
                        }
                    }} />)
                } className='btn btn-primary'>Details</button>
            </div>
        </div>
    );
};
