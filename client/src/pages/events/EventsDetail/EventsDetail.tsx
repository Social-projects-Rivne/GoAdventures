import React, { Component } from 'react';
import { Gallery, SettingsPanel } from '../../../components';
import { EventDto } from '../../../interfaces/Event.dto';

export class EventsDetail extends Component<any, any> {
  constructor(props: EventDto) {
    super(props);
    this.state = {
      eventProps: { ...this.props.routerProps.location.state }
    };
  }

  public render() {
    console.debug(this.props);
    return (
      <div className='container-fluid'>
        <SettingsPanel>
          {{
            left: <h2>{this.state.eventProps.topic}</h2>,
            middle: <h2>{this.state.eventProps.location}</h2>,
            right: (
              <button type='button' className='btn btn-success'>
                Edit
              </button>
            )
          }}
        </SettingsPanel>
        <div className='row'>
          <div className='col-3'>
            <div className='jumboton jumbotron-fluid'>
              <h2>Description</h2>
              <p className='lead'>{this.state.eventProps.description}</p>
              <hr className='my-4' />
              <p>
                Start: {this.state.eventProps.startDate} -{' '}
                {this.state.eventProps.endDate}
              </p>
              <hr className='my-4' />
              <p>
                Location:{this.state.eventProps.location}
                <a>
                  {this.state.eventProps.location} <small>View map</small>
                </a>{' '}
              </p>
            </div>
          </div>
          <div className='col-6'>
            <div className='jumboton jumbotron-fluid'>
              <Gallery />
            </div>
          </div>
          <div className='col-3'>Participants</div>
        </div>
      </div>
    );
  }
}
