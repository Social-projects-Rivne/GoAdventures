import React, { Component } from 'react';
import { Gallery, SettingsPanel } from '../../../components';
import { EventDto } from '../../../interfaces/Event.dto';
import { deleteEvent, isOwner } from '../../../api/event.service';
import { AxiosResponse } from 'axios';

export class EventsDetail extends Component<any, any> {
  constructor(props: EventDto) {
    super(props);
    this.state = {
      eventProps: { ...this.props.routerProps.location.state },
      isOwner: false
    };
    
    this.handleDelete = this.handleDelete.bind(this);
  }

  public componentDidMount() {
    isOwner(this.state.eventProps.id).then(
      (res: AxiosResponse): any => {
        console.log(res.status + " | " + res.statusText);
        if(res.status >= 200 && res.status <= 300) {
          this.setState({
            isOwner: true
          })
        } else {
          this.setState({
            isOwner: false
          })
        }
      }
    )
  }

  public handleDelete() {
    deleteEvent(this.state.eventProps.id)
      .then(
        (res: AxiosResponse): any => {
          console.log(res.status);
          if(res.status >= 200 && res.status <= 300) {
            this.props.routerProps.history.push('/profile');
          } else {
            
          }
        }
      )
  }

  public render() {
    return (
      <div className='container-fluid'>
        <SettingsPanel>
          {{
            left: <h2>{this.state.eventProps.topic}</h2>,
            middle: <h2>{this.state.eventProps.location}</h2>,
            right: (
              <div>
                {
                  this.state.isOwner ? 
                    (<div>
                      <button type='button' className='btn btn-success'>
                        Edit
                      </button>
                      <button onClick={this.handleDelete} type='button' className='btn btn-danger'>
                        Delete
                      </button>
                    </div>) : null
                }
              </div> 
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
