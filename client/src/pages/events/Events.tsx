import React, { Component } from 'react';
import InfiniteScroll from 'react-infinite-scroll-component';
import { getEventList } from '../../api/event.service';
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


  public async fetchEvents(): Promise<void> {
    const response = await getEventList();
    this.setState({ events: [...response.content] });
  }



  public  componentDidMount() {
    this.fetchEvents();
  }

  public render() {
    return (
      <div className='container-fluid'>
        <h1 className='text-center'>Event List</h1>

        <div className='row'>
              <InfiniteScroll
        dataLength={this.state.events.length} // This is important field to render the next data
        next={this.fetchEvents}
        hasMore={true}
        loader={<h4>Loading...</h4>}
        endMessage={
          <p style={{textAlign: 'center'}}>
            <b>Yay! You have seen it all</b>
          </p>
        }
        // below props only if you need pull down functionality
        // refreshFunction={this.refresh}
        // pullDownToRefresh
        // pullDownToRefreshContent={
        //   <h3 style={{textAlign: 'center'}}>&#8595; Pull down to refresh</h3>
        // }
        // releaseToRefreshContent={
        //   <h3 style={{textAlign: 'center'}}>&#8593; Release to refresh</h3>}
          >
        {this.state.events.map((event, index) =>
            (<EventsListBuild {...event} key={index} />)
          )}
      </InfiniteScroll>



        </div>
      </div>
    );
  }
}
