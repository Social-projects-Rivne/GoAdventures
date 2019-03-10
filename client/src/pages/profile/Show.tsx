import axios from 'axios';
import React, { Component } from 'react';
import { UserDto } from '../../interfaces/User.dto';
import './Show.scss';

export class Show extends Component<any, UserDto> {
  constructor(props: any) {
    super(props);
    this.state = {
      avatar: '',
        email: '',
        fullName: '',
        userName: '',
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
          fullName: response.data.fullName,
          userName: response.data.userName,
        })
      );
  }

  public render() {
    return (
      <div>
        <div className='User'>
          <div className='User-image'>
            <img className='User__avatar' src={this.state.avatar} alt='avatar' />
          </div>

          <div className='User-Data'>
            <div className='user-detail'>
              <p className=''>{this.state.fullName}</p>
              <p className=''>{this.state.userName}</p>
              <p className=''>{this.state.email}</p>
            </div>
          </div>
        </div>
      </div>
    );
  }
}
export default Show;
