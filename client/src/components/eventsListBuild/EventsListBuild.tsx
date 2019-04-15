import React from 'react';
import { Redirect } from 'react-router';
import { EventDto } from '../../interfaces/Event.dto';

import './EventsListBuild.scss';

export class EventsListBuild extends React.Component<EventDto, any> {
  constructor(props: any) {
    super(props);
    this.state = {
      redirect: false
    };
  }
  /**
   * redirect
   */

  
  public redirectTo() {
    this.setState({ redirect: true });
  }

  public render() {
    return (
      <div
        onClick={this.redirectTo.bind(this)}
        className='Events_card col card'
      >
        {this.state.redirect ? (
          <Redirect
            push
            to={{
              pathname: `/events/detail/${this.props.topic}`,
              state: {
                ...this.props
              }
            }}
          />
        ) : null}

        <img
          className='card-img-top'
          src={
            this.props.gallery !== null &&
            this.props.gallery.imageUrls.length > 0
              ? this.props.gallery.imageUrls[0]
              : 'https://via.placeholder.com/250'
          }
          alt='Card image cap'
        />

        
        
        <div className='card-body'>
        <small className="text-muted text-right ">{this.props.statusId === 2 ? (
            <p style={{ color: 'red' }}>CLOSED</p>
          ) : null}
</small>
          <h5 className='card-title'>{this.props.topic}</h5>
          
       
          <div className='row category'>
            <p>Category:{this.props.category}</p>
          </div>
          <h6 className=' row location' > {this.props.location}</h6>

        </div>
        
          
          
      </div>
    );
  }
}
