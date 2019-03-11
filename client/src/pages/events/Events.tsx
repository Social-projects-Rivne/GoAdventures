
import React, { Component } from 'react';



import { eventList } from '../../api/event.service';
import { AxiosResponse } from 'axios';
import {EventsListBuild} from '../../components/eventsListBuild/EventsListBuild';
import {EventDto} from "../../interfaces/Event.dto";


// export class Events extends Component<EventDto, any> {   
// constructor(props:any) {
//   super(props)
//   this.state = {
//     list:[]
//   }
// }


export class Events extends Component<EventDto, EventDto[]> {       
  constructor(props: any) {
    super(props)   
    this.state = [{    
        description: '',
        topic:'',
        start_date: '',
        end_date: '',
        location: '',

 
    }]
  }





  public componentDidMount() {                                  
    eventList().then((response: AxiosResponse<EventDto[]>) =>{
    console.log(response.data);  
    this.setState({ ...response.data }
        )
    }
    );

  }

  public render() {                                          
    return (
      
      <div className="row">
        <div className="col">
        {{...this.state}.map((event, index)=> {
          <EventsListBuild {...event} key={index} />
        })}
          
        </div>      
      </div>
    );
  }
}
