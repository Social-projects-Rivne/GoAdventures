import React, { Component, CSSProperties, SyntheticEvent } from 'react';
import { changeUserData } from '../../../api/user.service';
import { Dialog } from '../../../components/dialog-window/Dialog';
import { InputSettings } from '../../../components/dialog-window/interfaces/input.interface';
import { UserDto } from '../../../interfaces/User.dto';
import { editProfileSchema } from '../../../validationSchemas/authValidation';
import './EditForm.scss';

export class EditForm extends Component<any, UserDto> {
  private editFormInputSettings: InputSettings[] = [
    {
      field_name: 'fullName',
      label_value: 'Your name',
      placeholder: 'John',
      type: 'text'
    },
    {
      field_name: 'email',
      label_value: 'New email',
      placeholder: 'example@example.com',
      type: 'email'
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
      email: '',
      fullName: '',
      newPassword: '',
      password: '',
      repeatNewPassword: '',
      userName: ''
    };

    this.handleSubmit = this.handleSubmit.bind(this);
  }

  public handleSubmit(event: SyntheticEvent): Promise<string> {
    event.preventDefault();
    return changeUserData({ ...this.state });
    // console.log('form is submitted');
  }
  public render() {
    return (
      <div>
        <Dialog
          validationSchema={editProfileSchema}
          handleSubmit={this.handleSubmit}
          inputs={this.editFormInputSettings}
          button_text='Update'
          header='Edit your profile'
          inline_styles={this.editFormDialogStyles}
        />
      </div>
    );
  }
}
