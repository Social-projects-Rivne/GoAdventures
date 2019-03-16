import React, { Component } from 'react';



import { AxiosResponse } from 'axios';
import { eventList } from '../../api/event.service';
import { EventsListBuild } from '../../components/eventsListBuild/EventsListBuild';
import { EventDto } from '../../interfaces/Event.dto';




interface EventState {
  events: EventDto[];
}

export class Events extends Component<EventDto, EventState> {
  constructor(props: any) {
    super(props);
    this.state = {
      events: [
        {
          description: '',
          endDate: '',
          id: 0,
          location: '',
          startDate: '',
          topic: '',


        }
      ]
    };
  }





  public componentDidMount() {
    eventList().then((response: AxiosResponse<EventDto[]>) => {
      if(response.status <= 200 && response.status >= 300) {
        this.setState({ events: [...response.data] });
      }
    }
    ).catch((error) => {
      console.debug(error);
    });

  }

  public render() {
    return (
      <div className='container-fluid'>
        <h1 className='text-center'>Event List</h1>

        <div className='row'>

          {this.state.events.map((event, index) =>
            (<EventsListBuild {...event} key={index} />)
          )}

        </div>
      </div>
    );
  }
}