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
import axios, { AxiosResponse } from 'axios';

interface ProfileState {
  userProfile: UserDto;
  userEventList: any;
  errorMesage: {
    publicError: string;

  };
  choose: 'edit-profile' | 'events' | 'default' | 'account-overview';
  togleEditProfile: () => void;
  togleMyEvents: () => void;
  toogleAccountOverView: () => void;
  setAvatar: (url: string) => void;
  isShowModal: boolean;

}

export class Profile extends Component<UserDto, ProfileState> {
  fileInput: any;
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
      isShowModal: false,
      errorMesage: {
        publicError: '',
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
      choose: 'account-overview',
      togleEditProfile: () => {
        this.setState(state => ({
          choose: 'edit-profile'
        }));
        console.debug(this.state.choose)
      },
      togleMyEvents: () => {
        this.setState(state => ({
          choose: 'events'
        }));
        console.debug(this.state.choose)
      },
      toogleAccountOverView: () => {
        this.setState(state => ({
          choose: 'account-overview'
        }));

        console.debug(this.state.choose)
      },
      setAvatar: (avatar) => {
        this.setState({
          userProfile: { ...this.state.userProfile, avatarUrl: avatar }
        })
        console.debug("profile state", this.state.userProfile)
      }


    };

    this.clearErrorMessage = this.clearErrorMessage.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);

  }
  public handleSubmit(data: UserDto): Promise<any> {   //change user data
    return changeUserData({ ...data })
      .then((res) => {
        console.debug(res)
        this.setState({
          userProfile: {
            ...res.data
          },
          errorMesage: { publicError: 'saved successfully' }
        }, () => {
          window.setTimeout(() => {
            this.setState({ errorMesage: { publicError: '' } })
          }, 3500)
        });
      })
      .catch((err) => {
        this.setState({ errorMesage: { ...err.response.data } }, () => {
          window.setTimeout(() => {
            this.setState({ errorMesage: { publicError: '' } })
          }, 3500)
        });
      });
  }
  public componentDidMount() {
    getUserData()
      .then((response: AxiosResponse<UserDto>) =>
        this.setState({
          userProfile: { ...response.data }
        })
      );
  }
  public clearErrorMessage() {
    this.setState({
      errorMesage: { publicError: '' }
    }
    );
  }

  public render() {
    return (
      <ProfileContext.Provider value={this.state}>
        <div className='profile-page'>
          <div className='sidebar'>
            <Sidebar {...this.state.userProfile} />
          </div>
          <div className='Profile__content'>
            {

              this.state.choose === 'events'
                ?
                <div className='container page-container'>
                  <ShowEvents {...this.state.userEventList} />
                </div>
                : (this.state.choose === 'edit-profile'
                  ? <div className='container page-container'>
                    <Dialog
                      validationSchema={editProfileSchema}
                      handleSubmit={this.handleSubmit}
                      inputs={this.editFormInputSettings}
                      button_text='Update'
                      header='Edit your profile'
                      inline_styles={this.editFormDialogStyles}
                      childComponents={
                        <div className="Errors-messages">
                          {
                            this.state.errorMesage.publicError !== ''
                              ? <div className="alert alert-warning alert-dismissible fade show errProfile"
                                data-auto-dismiss role="alert">
                                <strong>{this.state.errorMesage.publicError}</strong>
                                <button
                                  onClick={this.clearErrorMessage}
                                  type="button"
                                  className="close"
                                  data-dismiss="alert"
                                  aria-label="Close">
                                  <span aria-hidden="true">&times;</span>
                                </button>
                              </div>
                              : null}
                        </div>

                      }

                    />
                  </div>
                  : (this.state.choose === 'account-overview'
                    ? <AccountOverwiew {...this.state.userProfile} />
                    : null

                  )
                )
            }
          </div>
        </div>
      </ProfileContext.Provider>

    );
  }
}