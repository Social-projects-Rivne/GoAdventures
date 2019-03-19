import { AxiosResponse } from 'axios';
import React, { Component } from 'react';
import { getUserData } from '../../api/user.service';
import { UserDto } from '../../interfaces/User.dto';
import Sidebar from './sidebar/Sidebar';
import EditForm from './sidebar/EditForm'
import './Profile.scss'

interface ProfileState {
  userProfile: UserDto;
  userEventList: any;
  show: boolean;
}

export class Profile extends Component<UserDto, ProfileState> {        // початкова ініціалізація(null)
  constructor(props: any) {
    super(props);

    this.state = {
      show: true,
      userProfile: {
        fullName: '',
        userName: '',
        email: '',
        avatarUrl: '',
      },
      userEventList: {
        description: '',
        topic: '',
        start_date: '',
      }
    };
  }

  public componentDidMount() {                                  // сеттер на пропси зверху з api
    getUserData().then((response: AxiosResponse<UserDto>) =>
      this.setState({
        userProfile: { ...response.data }
      })
    );
  }

  public render() {                                           // рендер екземпляров сайдбар і юзерівенліст
    return (
      <div className="row profile-page container-fluid">
        <div id='sidebar' className=''>
          <Sidebar {...this.state.userProfile}/>
        </div>
        <div id='content' className=''>
          {this.state.show ? <EditForm /> : <div>edit</div>}
        </div>
      </div>
    );
  }
}

