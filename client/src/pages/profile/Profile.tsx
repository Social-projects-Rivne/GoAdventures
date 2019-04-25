import { Cookies, withCookies } from 'react-cookie';
import React, { Component, CSSProperties, ChangeEvent } from 'react';

import { changeUserData, getUserData } from '../../api/user.service';
import { Dialog, Content } from '../../components';
import { InputSettings } from '../../components/dialog-window/interfaces/input.interface';
import { ProfileContext } from '../../context/profile.context';
import { UserDto } from '../../interfaces/User.dto';
import { editProfileSchema } from '../../validationSchemas/authValidation';
import AccountOverwiew from './accountOverview/AccountOverview';
import './Profile.scss';
import ShowEvents from './showEvents/ShowEvents';
import Sidebar from './sidebar/Sidebar';
import avatar from './images/Person.png';
import axios, { AxiosResponse } from 'axios';
import { serverUrl } from '../../api/url.config';

interface ProfileState {
  userProfile: UserDto;
  userEventList: any;
  avatar: string | Blob;
  // showSucessMessage: boolean;
  errorMesage: {
    publicError: string
  };
  context: {
    avatarUrl: string
  };
  choose: 'edit-profile' | 'events' | 'default' | 'account-overview';
  togleEditProfile: () => void;
  togleMyEvents: () => void;
  toogleAccountOverView: () => void;
}

const cookies: Cookies = new Cookies();
export class Profile extends Component<UserDto, ProfileState> {
  public fileInput: any;
  private editFormInputSettings: InputSettings[] = [
    {
      field_name: 'fullname',
      label_value: 'Changed your name?',
      placeholder: 'Vasyl',
      type: 'text'
    },
    {
      field_name: 'username',
      label_value: 'New username',
      placeholder: 'B4gr0vy',
      type: 'text'
    },
    {
      field_name: 'phone',
      label_value: 'New phone number',
      placeholder: '0631512412',
      type: 'tel'
    },
    {
      field_name: 'location',
      label_value: 'Moved to another city? Lucky one',
      placeholder: 'Rivne',
      type: 'text'
    },
    {
      field_name: 'password',
      label_value: 'Current password',
      placeholder: '********',
      type: 'password'
    },
    {
      field_name: 'newPassword',
      label_value: 'New password',
      placeholder: '********',
      type: 'password'
    },
    {
      field_name: 'confirmPassword',
      label_value: 'Repeat new password',
      placeholder: '********',
      type: 'password'
    }
  ];

  private editFormDialogStyles: CSSProperties = {
    opacity: 0.9,
    width: '100%'
  };
  constructor(props: any) {
    super(props);

    this.state = {
      // showSucessMessage: false,
      avatar: '',
      errorMesage: {
        publicError: ''
      },
      userProfile: {
        fullname: '',
        username: '',
        email: '',
        avatarUrl: ''
      },
      userEventList: {
        description: '',
        endDate: '',
        id: 0,
        location: '',
        startDate: '',
        topic: ''
      },
      context: {
        avatarUrl: ''
      },
      choose: 'account-overview',
      togleEditProfile: () => {
        this.setState((state) => ({
          choose: 'edit-profile'
        }));
      },
      togleMyEvents: () => {
        this.setState((state) => ({
          choose: 'events'
        }));
      },
      toogleAccountOverView: () => {
        this.setState((state) => ({
          choose: 'account-overview'
        }));

      }
    };
    this.clearErrorMessage = this.clearErrorMessage.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
    this.fileSelectHandler = this.fileSelectHandler.bind(this);
  }
  public handleSubmit(data: UserDto): Promise<any> {
    // change user data
    return changeUserData({ ...data })
      .then((res) => {
        this.setState({
          userProfile: {
            ...res.data
          },
          errorMesage: { publicError: 'saved successfully' }
        });
      })
      .catch((err) => {
        this.setState({ errorMesage: { ...err.response.data } });
      });
  }
  public fileSelectHandler(event: ChangeEvent<HTMLInputElement>): void {
    !!event.target.files
      ? this.setState({
          avatar: event.target.files[0]
        })
      : null;
  }
  public componentDidMount() {
    getUserData().then((response: AxiosResponse<UserDto>) =>
      this.setState({
        userProfile: { ...response.data }
      })
    );
  }
  public clearErrorMessage() {
    this.setState({
      errorMesage: { publicError: '' }
    });
  }
  public render() {
    return (
      <ProfileContext.Provider value={this.state}>
        <div className='profile-page'>
          <div className='sidebar'>
            <Sidebar {...this.state.userProfile} />
          </div>
          <div className='Profile__content'>
            {this.state.choose === 'events' ? (
              <ShowEvents {...this.state.userEventList} />
            ) : this.state.choose === 'edit-profile' ? (
              <Dialog
                validationSchema={editProfileSchema}
                handleSubmit={this.handleSubmit}
                inputs={this.editFormInputSettings}
                button_text='Update'
                header='Edit your profile'
                inline_styles={this.editFormDialogStyles}
                childComponents={
                  <div className='Errors-messages'>
                    {this.state.errorMesage.publicError !== '' ? (
                      <div
                        className='alert alert-warning alert-dismissible fade show errProfile'
                        data-auto-dismiss={true}
                        role='alert'
                        auto-close='3000'
                      >
                        <strong>{this.state.errorMesage.publicError}</strong>
                        <button
                          onClick={this.clearErrorMessage}
                          type='button'
                          className='close'
                          data-dismiss='alert'
                          aria-label='Close'
                        >
                          <span aria-hidden='true'>&times;</span>
                        </button>
                      </div>
                    ) : null}
                  </div>
                }
              />
            ) : this.state.choose === 'account-overview' ? (
              <AccountOverwiew {...this.state.userProfile} />
            ) : null}
          </div>
        </div>
      </ProfileContext.Provider>
    );
  }
}
