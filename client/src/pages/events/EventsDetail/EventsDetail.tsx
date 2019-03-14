import React, { Component } from 'react';
import { EventDto } from '../../../interfaces/Event.dto';

export class EventsDetail extends Component<EventDto, any> { // will change
  constructor(props: EventDto) {
    super(props);
  }
  public render() {
    console.debug(this.props);
    return (
      <div>
        <div className='row'>
          Event Detail nav bar with event name
        </div>
        <div className='row'>
        <div className='col-3'>
          Event text
        </div>
        <div className='col-6'>
          Photos
        </div>
        <div className='col-3'>
          Participants
        </div>
      </div>
      </div>

    );
  }
}
