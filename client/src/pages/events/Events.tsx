import React, { Component } from 'react';
import InfiniteScroll from 'react-infinite-scroll-component';
import { getEventList, searchForEvents } from '../../api/event.service';
import { AddEventBtn } from '../../components/addEventBtn/AddEventBtn';
import { EventsListBuild } from '../../components/eventsListBuild/EventsListBuild';
import { EventDto } from '../../interfaces/Event.dto';
import './Events.scss';
import { MdSearch } from 'react-icons/md';

interface EventState {
  events: EventDto[];
  pageSettings: {
    isLast: true | false | undefined
    nextPage: string | null
  };
  search: string | null | undefined;
}

export class Events extends Component<EventDto, EventState> {
  constructor(props: any) {
    super(props);
    this.state = {
      events: [],
      pageSettings: {
        isLast: undefined,
        nextPage: ''
      },
      search: ''
    };
    this.fetchEvents = this.fetchEvents.bind(this);
  }
  handleChange(value: string) {
    if (window.location.pathname != '/events') {
      // <Link ></Link>

    }
    this.setState({ search: value });
  }

  public componentDidMount() {
    this.fetchEvents();
  }

  public async fetchSearchEvent(): Promise<void> {
    this.state = {
      events: [],
      pageSettings: {
        isLast: undefined,
        nextPage: ''
      },
      search: this.state.search
    };
    const response = await searchForEvents(null, this.state.search);
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

  public async fetchEvents(): Promise<void> {


    const response = await getEventList(this.state.pageSettings.nextPage);

    this.setState({
      events: [...this.state.events, ...response.content],
      pageSettings: {
        isLast: response.last,
        nextPage: !!sessionStorage.getItem('nextpage')
          ? sessionStorage.getItem('nextpage')
          : '/event/all'
      }
    }); console.log(this.state);
  }

  public render() {
    return (
      <div className='container-fluid'>

        <AddEventBtn />
        <div className='row'>
          <div className='col'>
            <InfiniteScroll

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
              <div className='container page-container'>

                <form onSubmit={(e: any) => {
                  e.preventDefault();
                  this.fetchSearchEvent();
                }}>
                  <div className="input-group input-group-lg mb-3">
                   
                    <input type="text" className="form-control search-comp"  placeholder="search" aria-describedby="basic-addon1" onChange={(e: any) => {
                      this.handleChange(e.target.value);
                    }} />
                     
                  </div>
                </form>




                <div className='card-columns'>
                  {this.state.events.map((event, index) => {
                    if (event) {
                      return <EventsListBuild {...event} key={index} />;
                    } else {
                      return null;
                    }
                  })}
                </div>
              </div>
            </InfiniteScroll>

          </div>
        </div>
      </div>
    );
  }
}
