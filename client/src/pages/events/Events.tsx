import React, { Component } from 'react';



import { AxiosResponse } from 'axios';
import { eventList } from '../../api/event.service';
import { EventsListBuild } from '../../components/eventsListBuild/EventsListBuild';
import { EventDto } from '../../interfaces/Event.dto';


// export class Events extends Component<EventDto, any> {
// constructor(props:any) {
//   super(props)
//   this.state = {
//     list:[]
//   }
// }


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
          topic: '',
          startDate: '',
          endDate: '',
          location: '',


        }
      ]
    };
  }





  public componentDidMount() {
    eventList().then((response: AxiosResponse<EventDto[]>) => {
      console.log(response.data);

      this.setState({ events: [...response.data] });
      console.debug(this.state);
    }
    ).catch((err) => {

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
