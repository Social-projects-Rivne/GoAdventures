import { AxiosResponse } from 'axios';
import React, { Component, CSSProperties } from 'react';
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



interface ProfileState {
  userProfile: UserDto;
  userEventList: any;
  showEditForm: boolean;

  choose: 'edit-profile' | 'events' | 'default' | 'account-overview';
  togleEditProfile: () => void;
  togleMyEvents: () => void;
  toogleAccountOverView: () => void;
  // toogleSetProfileState: () => void;


}

export class Profile extends Component<UserDto, ProfileState> {
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
      field_name: 'email',
      label_value: 'New email',
      placeholder: 'example@example.com',
      type: 'email'
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
      userProfile: {
        fullname: '',
        username: '',
        email: '',
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
    };
  }

  public handleSubmit(data: UserDto): Promise<any> {
    return changeUserData({ ...data });
  }


  public componentDidMount() {
    // сеттер на пропси зверху з api
    getUserData().then((response: AxiosResponse<UserDto>) =>
      this.setState({
        userProfile: { ...response.data }
      })
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
                ? <ShowEvents {...this.state.userEventList} />
                : (this.state.choose === 'edit-profile'
                  ? <Dialog
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
