import React, { Component } from 'react';
import { Sidebar } from './sidebar/Sidebar';
import { UserDto } from '../../interfaces/User.dto';
import { getUserData } from '../../api/user.service';
import { AxiosResponse } from 'axios';
import { UserEventList } from './profileUserEventList/UserEventList';

export class Profile extends Component<UserDto, any> {        //початкова ініціалізація(null)
  constructor(props: any) {
    super(props);

    this.state = {
      userProfile: {
        fullname: '',
        username: '',
        email: '',
        avatarUrl: '',
      },
      userEventList: {
        description: '',
        topic: '',
        start_date: '',
      }
    }
  }

  public componentDidMount() {                                  //сеттер на пропси зверху з api
    getUserData().then((response: AxiosResponse<UserDto>) =>
      this.setState({
        userProfile: { ...response.data }
      })
    );
  }

  public render() {                                           //рендер екземпляров сайдбар і юзерівенліст
    return (
      <div className="row">
        <div className="col"><Sidebar {...this.state.userProfile} />
        </div>
        <div className="row">
          <UserEventList {...this.state.userEventList} />
        </div>
      </div>
    );
  }
}

