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
      <div onClick={this.redirectTo.bind(this)} className='col card Events_card '>
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
          src='https://botw-pd.s3.amazonaws.com/styles/logo-thumbnail/s3/0016/1400/brand.gif?itok=AelJnUfh'
          alt='Card image cap'
        />
        <div className='card-body'>
          <h5 className='card-title'>{this.props.topic}</h5>
          <div className='row'>
            <h6 className='col-6'> {this.props.location}</h6>
            <div className='col-6'>
              <p>Category</p>
            </div>
          </div>
        </div>
      </div>
    );
  }
}
