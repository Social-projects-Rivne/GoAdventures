import axios from 'axios';
import React, { Component } from 'react';
import { UserDto } from '../../interfaces/User.dto';
import './Show.scss';

export class Show extends Component<any, UserDto> {
  constructor(props: any) {
    super(props);
    this.state = {
      avatarUrl: '',
        email: '',
        fullname: '',
        username: '',
    };
  }


  public componentDidMount() {
    axios.get('http://localhost:8080/profile/page', {
        headers: {
          'Authorization': `Bearer ${localStorage.getItem('tkn879')}`,
          'Content-Type': 'application/json'
        }
      })
      .then((response) =>
        this.setState({
          email: response.data.email,
          fullname: response.data.fullname,
          username: response.data.username,
        })
      );
  }

  public render() {
    return (
      <div>
        <div className='User'>
          <div className='User-image'>
            <img className='User__avatar' src={this.state.avatarUrl} alt='avatar' />
          </div>

          <div className='User-Data'>
            <div className='user-detail'>
              <p className=''>{this.state.fullname}</p>
              <p className=''>{this.state.username}</p>
              <p className=''>{this.state.email}</p>
            </div>
          </div>
        </div>
      </div>
    );
  }
}
export default Show;
