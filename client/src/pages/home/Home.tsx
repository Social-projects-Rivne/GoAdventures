import React, { Component } from 'react';
import { CSSProperties } from 'react';
import { RouterProps } from 'react-router';
import { Link } from 'react-router-dom';
import { signIn, signUp } from '../../api/auth.service';
import fb from '../../assets/icons/fb.svg';
import google from '../../assets/icons/google.svg';
import { Dialog, Footer } from '../../components/';
import { InputSettings } from '../../components/dialog-window/interfaces/input.interface';
import { AuthContext } from '../../context/auth.context';
import { UserDto } from '../../interfaces/User.dto';
import { SigninSchema, SignupSchema } from '../../validationSchemas/authValidation';
import './Home.scss';

export class Home extends Component<RouterProps, any> {
  private signUpDialogStyles: CSSProperties = {
    height: '30rem',
    maxHeight: '30rem',
    maxWidth: '20rem',
    opacity: 0.9
  };
  private signUpSnputSettings: InputSettings[] = [
    {
      field_name: 'fullName',
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
  constructor(props: RouterProps) {
    super(props);
  }


  public submitSignUpRequest(data: UserDto): Promise<string> {
    return signUp(data);
  }

  /**
   * submitSignInRequest
   */
  public submitSignInRequest(data: UserDto): Promise<string> {
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
              <div className='Home__auth'>
                <AuthContext.Consumer>
                  {({ authorized, authType, authorize, messages }) =>
                    <div>
                      {!authorized && messages ?
                        (<div className='alert alert-danger alert-dismissible fade show' role='alert'>
                          <strong>Holy guacamole!</strong> {messages}
                          <button type='button' className='close' data-dismiss='alert' aria-label='Close'>
                            <span aria-hidden='true'>&times;</span>
                          </button>
                        </div>) : null}
                      {!authorized && authType === 'signUp' ? (
                        <Dialog
                          validationSchema={SignupSchema}
                          context={{ authorized, authorize, authType }}
                          handleSubmit={this.submitSignUpRequest}
                          inputs={this.signUpSnputSettings}
                          button_text='Sign up'
                          header='Sign up for adventures'
                          inline_styles={this.signUpDialogStyles}
                          redirect={{ routerProps: this.props, redirectURL: '/confirm-yor-yo' }}
                        />
                      ) : !authorized && authType === 'signIn' ? (
                        <Dialog
                          validationSchema={SigninSchema}
                          context={{ authorized, authorize, authType }}
                          handleSubmit={this.submitSignInRequest}
                          inputs={this.signInSnputSettings}
                          button_text='Sign in'
                          header='Sign in for adventures'
                          childСomponents={
                            <div className='text-center'>
                              <h3>Sign in with</h3>
                              <div className='d-flex flex-row justify-content-around align-self-center'>
                                <a href='#'>
                                  <img src={fb} />
                                  <p>Facebook</p>
                                </a>
                                <a href='#'>
                                  <img src={google} />
                                  <p>Google</p>
                                </a>
                              </div>
                              <Link to={'/recovery-password'}>Forgot password?</Link>
                            </div>
                          }
                          inline_styles={this.signUpDialogStyles}
                          redirect={{ routerProps: this.props, redirectURL: '/profile' }}
                        />
                      ) : null}

                    </div>}
                </AuthContext.Consumer>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }
}
