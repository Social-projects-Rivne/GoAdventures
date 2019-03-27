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
        <div className='row'>
          <div className='col-6'>
            <div className='jumboton jumbotron-fluid'>
              <Gallery {...this.state.eventProps.gallery} />
            </div>
          </div>
          <div className='col-6'>
            <div className='jumboton jumbotron-fluid'>
              <SettingsPanel>
                {{
                  left: (
                    <div>
                      <div className='d-flex flex-row align-content-center'>
                        <img
                          className='rounded-avatar-sm'
                          src='https://www.kidzone.ws/animal-facts/whales/images/beluga-whale-3.jpg'
                        />
                        <h2>{this.state.eventProps.topic}</h2>
                      </div>
                      <p>Friday,19 April 2019</p>
                    </div>
                  ),
                  right: (
                    <div className='d-flex flex-column h-100'>
                      <button type='button' className='btn btn-success'>
                        Edit
                      </button>
                      <button type='button' className='btn btn-info'>
                        Subscribe
                      </button>
                    </div>
                  )
                }}
              </SettingsPanel>
              <hr className='my-4' />
              <h2>Description</h2>
              <p className='lead'>{this.state.eventProps.description}</p>
              <hr className='my-4' />
              <p>
                Start: {this.state.eventProps.startDate} -{' '}
                {this.state.eventProps.endDate}
              </p>
              <hr className='my-4' />
              <p>
                Location:
                <a>
                  {this.state.eventProps.location} <small>View map</small>
                </a>{' '}
              </p>
            </div>
          </div>
        </div>
      </div>
    );
  }
}
