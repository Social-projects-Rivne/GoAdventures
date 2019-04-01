import React, { Component } from 'react';
import InfiniteScroll from 'react-infinite-scroll-component';
import { getEventList } from '../../api/event.service';
import { AddEventBtn } from '../../components/addEventBtn/AddEventBtn';
import { EventsListBuild } from '../../components/eventsListBuild/EventsListBuild';
import { EventDto } from '../../interfaces/Event.dto';

interface EventState {
  events: EventDto[];
  pageSettings: {
    isLast: true | false | undefined
    nextPage: string | null
  };
}

export class Events extends Component<EventDto, EventState> {
  constructor(props: any) {
    super(props);
    this.state = {
      events: [],
      pageSettings: {
        isLast: undefined,
        nextPage: ''
      }
    };
    this.fetchEvents = this.fetchEvents.bind(this);
  }

  public componentDidMount() {
    this.fetchEvents();
  }

  public async fetchEvents(): Promise<void> {
    console.debug(this.state);
    const response = await getEventList(this.state.pageSettings.nextPage);
    this.setState({
      events: [...this.state.events, ...response.content],
      pageSettings: {
        isLast: response.last,
        nextPage: !!sessionStorage.getItem('nextpage')
          ? sessionStorage.getItem('nextpage')
          : '/event/all'
      }
    });
  }

  public render() {
    return (
      <div className='container-fluid'>
        <h1 className='text-center'>Event List</h1>
        <AddEventBtn />
        <div className='row'>
          <div className='col'>
            <InfiniteScroll
              style={{
                MozColumnGap: '0.5em',
                MozColumnWidth: '15em',
                WebkitColumnGap: '0.5em',
                WebkitColumnWidth: '15em'
              }}
              dataLength={this.state.events ? this.state.events.length : 0}
              next={this.fetchEvents}
              hasMore={!this.state.pageSettings.isLast}
              loader={<h4>Loading...</h4>}
              endMessage={
                <p style={{ textAlign: 'center' }}>
                  <b>Yay! You have seen it all</b>
                </p>
              }
            >
              {this.state.events.map((event, index) => {
                if (event) {
                  return <EventsListBuild {...event} key={index} />;
                } else {
                  return null;
                }
              })}
            </InfiniteScroll>
          </div>
        </div>
      </div>
    );
  }
}
