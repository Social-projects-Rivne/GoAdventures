import { Cookies, withCookies } from 'react-cookie';
import React, { Component, CSSProperties, ChangeEvent } from 'react';

import { changeUserData, getUserData } from '../../api/user.service';
import { Dialog } from '../../components';
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
  showEditForm: boolean;
  errorMesage: {
    publicError: string;

  };
  choose: 'edit-profile' | 'events' | 'default' | 'account-overview';
  togleEditProfile: () => void;
  togleMyEvents: () => void;
  toogleAccountOverView: () => void;
  // toogleSetProfileState: () => void;


}

const cookies: Cookies = new Cookies();
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
      label_value: 'Old password',
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
      label_value: 'Confirm your password',
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
      showEditForm: true,
      avatar: '',
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
        console.log(this.state.choose)
      },
      togleMyEvents: () => {
        this.setState(state => ({
          choose: 'events'
        }));
        console.log(this.state.choose)
      },
      toogleAccountOverView: () => {
        this.setState(state => ({
          choose: 'account-overview'
        }));

        console.log(this.state.choose)
      },
      // toogleSetProfileState: () => {         
      //   this.setState(state => ({
      //     userProfile : {
      //         avatarUrl: value

      //     } 
      //   }));

      // },

    };

    this.handleSubmit = this.handleSubmit.bind(this);
    this.uploadHandler = this.uploadHandler.bind(this);
    this.fileSelectHandler = this.fileSelectHandler.bind(this);

  }
  public uploadHandler() {  //uploadAvatar
    const formdata: FormData = new FormData();
    formdata.set('file', this.state.avatar);
    const config = {
      headers: {
        'Content-Type': 'multipart/form-data',
        'Authorization': `Bearer ${cookies.get('tk879n')}`
      }
    };
    axios.post(
      `${serverUrl}/uploadAvatar`,
      formdata,
      config
    ).then(response => {
      this.setState({
        userProfile: {
          ...this.state.userProfile,
          avatarUrl: response.data,
        }
      });
    }
    ).catch((err) => {
      this.setState({ errorMesage: { ...err.response.data } });
    });
  }

  public handleSubmit(data: UserDto): Promise<any> {   //change user data
    return changeUserData({ ...data }).then((res) => {
      console.debug(res)
      this.setState({
        userProfile: {
          ...res.data
        }
      });
    })
      .catch((err) => {
        this.setState({ errorMesage: { ...err.response.data } });
      });

  }
  public fileSelectHandler(event: ChangeEvent<HTMLInputElement>): void {
    console.debug(event.target.files);
    !!event.target.files ?
      this.setState({
        avatar: event.target.files[0]
      }) : null;
  }
  public componentDidMount() {
    // сеттер на пропси зверху з api
    getUserData()
      .then((response: AxiosResponse<UserDto>) =>
        this.setState({
          userProfile: { ...response.data }
        })
      );
  }

  public render() {
    console.debug(this.state.userProfile)
    return (
      <ProfileContext.Provider value={this.state}>
        <div className='profile-page'>
          <div className='sidebar'>
            <Sidebar {...this.state.userProfile} />
          </div>
          <div className='Profile__content'>
            {

              this.state.choose === 'events'
                ? <ShowEvents {...this.state.userEventList} />
                : (this.state.choose === 'edit-profile'
                  ? <Dialog
                    childComponents={
                      <div>
                        <div className='Sidebar__avatar'>

                          <img
                            src={(this.state.userProfile.avatarUrl != undefined && this.state.userProfile.avatarUrl != '') ? this.state.userProfile.avatarUrl : avatar}
                            alt='user_avatar' />

                          <div className="change_avatar_btn">
                            <input
                              style={{ display: 'none' }}
                              type='file'
                              onChange={this.fileSelectHandler}
                              ref={(fileInput) => this.fileInput = fileInput}
                            />
                            <div>
                              <button
                                className="btn btn-success changeAvatarBtn"
                                onClick={() => this.fileInput.click()} > Change avatar
                          </button>
                            </div>
                            <div>
                              <button
                                style={this.state.avatar == '' ? { display: 'none' } : { display: 'flex' }}
                                className="btn btn-warning"
                                onClick={this.uploadHandler}
                              >Upload</button>
                            </div>

                          </div>
                        </div>

                        <div className="Errors-messages">
                          {
                            this.state.errorMesage.publicError !== ''
                              ? <div className="alert alert-warning alert-dismissible fade show errProfile"
                                role="alert">
                                <strong>{this.state.errorMesage.publicError}</strong>
                                <button type="button" className="close" data-dismiss="alert" aria-label="Close">

                                  <span aria-hidden="true">&times;</span>
                                </button>
                              </div>
                              : null}
                        </div>
                      </div>
                    }
                    validationSchema={editProfileSchema}
                    handleSubmit={this.handleSubmit}
                    inputs={this.editFormInputSettings}
                    button_text='Update'
                    header='Edit your profile'
                    inline_styles={this.editFormDialogStyles}
                  />
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