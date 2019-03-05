import React, { Component } from 'react';
import { CSSProperties } from 'react';
import { Redirect } from 'react-router';
import { signIn, signUp } from '../../api/auth.service';
import { Dialog } from '../../components/';
import { InputSettings } from '../../components/dialog-window/interfaces/input.interface';
import { AuthContext } from '../../context/auth.context';
import './Home.scss';

export class Home extends Component {
  private signUpDialogStyles: CSSProperties = {
    height: '27rem',
    maxWidth: '20rem',
    opacity: 0.9
  };
  private signUpSnputSettings: InputSettings[] = [
    {
      field_name: 'fullname',
      label_value: 'Your name',
      placeholder: 'John',
      type: 'text'
    },
    {
      field_name: 'email',
      label_value: 'Your email',
      placeholder: 'example@example.com',
      type: 'email'
    },
    {
      field_name: 'password',
      label_value: 'Your password',
      placeholder: '********',
      type: 'password'
    },
    {
      field_name: 'confirmPassword',
      label_value: 'Repeat your password',
      placeholder: '********',
      type: 'password'
    }
  ];

  private signInSnputSettings: InputSettings[] = [
    {
      field_name: 'email',
      label_value: 'Your email',
      placeholder: 'example@example.com',
      type: 'email'
    },
    {
      field_name: 'password',
      label_value: 'Your password',
      placeholder: '********',
      type: 'password'
    }
  ];



  public submitSignUpRequest(data: object): Promise<boolean> {
    return signUp(data);
  }

  /**
   * submitSignInRequest
   */
  public submitSignInRequest(data: object): Promise<boolean> {
    return signIn(data);
  }

  public render() {
    return (
      <div className='Home-content'>
        <div className='container'>
        <div className='row'>
          </div>
          <div className='row'>
            <div className='col-xl-6 col-lg-6 col-md-6 col-sm-12 d-sm-flex col d-none align-self-center'>
              <div className='Home-heading d-flex flex-column align-items-baseline'>
                <h2>GO</h2>
                <h2>Adventures</h2>
              </div>
            </div>
            <div className='col-xl-6 col-lg-6 col-md-6 col-sm-12'>
              <div className='Home__signup'>
                <AuthContext.Consumer>
                  {({ authorized, authType, authorize }) =>
                    !authorized && authType === 'signUp' ? (
                      <Dialog
                        context={{ authorized, authorize, authType }}
                        handleSubmit={this.submitSignUpRequest}
                        inputs={this.signUpSnputSettings}
                        button_text='Sign up'
                        header='Sign up for adventures'
                        inline_styles={this.signUpDialogStyles}
                        redirect={() => {
                          return <Redirect to='/confirm-yor-yo' />;
                        }}
                      />
                    ) : (
                      <Dialog
                        context={{ authorized, authorize, authType }}
                        handleSubmit={this.submitSignInRequest}
                        inputs={this.signInSnputSettings}
                        button_text='Sign in'
                        header='Sign in for adventures'
                        inline_styles={this.signUpDialogStyles}
                        redirect={() => {
                          return <Redirect to='/profile' />;
                        }}
                      />
                    )
                  }
                </AuthContext.Consumer>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }
}
