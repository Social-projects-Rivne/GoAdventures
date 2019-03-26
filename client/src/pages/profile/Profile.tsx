import { AxiosResponse } from 'axios';
import React, { Component, CSSProperties } from 'react';
import { changeUserData, getUserData } from '../../api/user.service';
import { Dialog } from '../../components';
import { InputSettings } from '../../components/dialog-window/interfaces/input.interface';
import { UserDto } from '../../interfaces/User.dto';
import { editProfileSchema } from '../../validationSchemas/authValidation';
import './Profile.scss';
import { Sidebar } from './sidebar/Sidebar';
interface ProfileState {
  userProfile: UserDto;
  userEventList: any;
  showEditForm: boolean;
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
        avatarUrl: ''
      },
      userEventList: {
        description: '',
        topic: '',
        start_date: ''
      }
    };
  }

  public handleSubmit(data: UserDto): Promise<string> {
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
      <div className='profile-page'>
        <div className='sidebar'>
          <Sidebar {...this.state.userProfile} />
        </div>
        <div className='Profile__content'>
          {this.state.showEditForm ? (
            <Dialog
              validationSchema={editProfileSchema}
              handleSubmit={this.handleSubmit}
              inputs={this.editFormInputSettings}
              button_text='Update'
              header='Edit your profile'
              inline_styles={this.editFormDialogStyles}
            />
          ) : (
            <div>User Event List</div>
          )}
        </div>
      </div>
    );
  }
}
