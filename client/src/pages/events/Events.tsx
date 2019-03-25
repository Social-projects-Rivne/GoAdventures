import React, { Component } from 'react';


import { DropDown } from '../../components/';
import { AxiosResponse } from 'axios';
import { getEventList } from '../../api/event.service';
import { EventsListBuild } from '../../components/eventsListBuild/EventsListBuild';
import { EventDto } from '../../interfaces/Event.dto';
import { Ng2FloatBtnModule } from 'ng2-float-btn'
import { AddEventBtn } from '../../components/addEventBtn/AddEventBtn';




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
    const b = getEventList();
    console.debug(b);
    // this.setState({ events: [...response.data] });
  }
  public render() {
    return (
      <div className='container-fluid'>

        <h1 className='text-center'>Event List</h1>
        <AddEventBtn />
        <div className='container'>

          <div className='row'>

            {this.state.events.map((event, index) =>
              (<EventsListBuild {...event} key={index} />)

            )}
          </div>
        </div>
      </div>
    );
  }
}
