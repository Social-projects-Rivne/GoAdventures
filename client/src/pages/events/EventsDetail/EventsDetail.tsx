import React, { Component } from 'react';
import { SettingsPanel } from '../../../components';
import { EventDto } from '../../../interfaces/Event.dto';


export class EventsDetail extends Component<EventDto, any> {
  constructor(props: EventDto) {
    super(props);
  }

  public render() {
    console.debug(this.props);
    return (
      <div>
        <div className='row'>
        <div className='col-12'>
          {/* <SettingsPanel {...[(<div>Huilysha</div>), (<div>Huilysha</div>)]} />  yano */}
          <SettingsPanel />
        </div>
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
