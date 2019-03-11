import React, { Component } from 'react';
import { Sidebar } from '../../components';

import { UserDto } from '../../interfaces/User.dto';
import { getUserData } from '../../api/user.service';
import { AxiosResponse } from 'axios';
import {UserEventList} from "../../components/userevenlist/UserEventList";
import {EventDto} from "../../interfaces/Event.dto";
import {getEventData} from "../../api/event.service";







export class Profile extends Component<UserDto, any> {        //початкова ініціалізація(null)
  constructor(props: any) {
    super(props)
    // this.state = {
    //   fullName: '',
    //   userName: '',
    //   email: '',
    //   avatarUrl: '',
    //
    //
    // }
    this.state = {
      userProfile: {
        fullName: '',
        userName: '',
        email: '',
        avatarUrl: '',
      },
      userEventList: {
        description: '',
        topic:'',
        start_date: '',
      }

    }



  }





  public componentDidMount() {                                  //сеттер на пропси зверху з api
    getUserData().then((response: AxiosResponse<UserDto>) =>
      this.setState({ userProfile: { ...response.data }
      })
    );
    // getEventData().then((response: AxiosResponse<EventDto>) =>
    //     this.setState({
    //       ...response.data
    //     })
    // );
  }





  public render() {                                           //рендер екземпляров сайдбар і юзерівенліст
    return (
      <div className="row">
        <div className="col"><Sidebar {...this.state.userProfile} />
        </div>
        <div className="col">
          <UserEventList {...this.state.userEventList}/>
      </div>
      </div>
    );

  }
}

