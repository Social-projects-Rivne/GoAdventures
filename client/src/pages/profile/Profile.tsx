import { AxiosResponse } from 'axios';
import React, { Component } from 'react';
import { getUserData } from '../../api/user.service';
import { UserDto } from '../../interfaces/User.dto';
import Sidebar from './sidebar/Sidebar';

interface ProfileState {
  userProfile: UserDto;
  userEventList: any;
}

export class Profile extends Component<UserDto, ProfileState> {        // початкова ініціалізація(null)
  constructor(props: any) {
    super(props);

    this.state = {
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
      <div><Sidebar {...this.state.userProfile} />
      </div>
    );
  }
}

