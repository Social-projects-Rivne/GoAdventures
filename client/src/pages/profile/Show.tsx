import axios from 'axios';
import { AxiosResponse } from 'axios';
import React, { Component } from 'react';
import { Cookies, withCookies } from 'react-cookie';
import { UserDto } from '../../interfaces/User.dto';
import './Show.scss';

export class Show extends Component<any, UserDto> {
  private cookies: Cookies;
  constructor(props: any) {
    super(props);
    this.cookies = props.cookies;
    this.state = {
      avatarUrl: '',
        email: '',
        fullName: '',
        userName: '',
    };
  }


  public componentDidMount() {
    axios.get('http://localhost:8080/profile/page', {
        headers: {
          'Authorization': `Bearer ${this.cookies.get('tk879n')}`,
          'Content-Type': 'application/json'
        }
      })
      .then((response: AxiosResponse<UserDto>): void => {
        console.debug(response);
        this.setState({
          email: response.data.email,
          fullName: response.data.fullName,
          userName: response.data.userName,
        });}
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
export default withCookies(Show);
