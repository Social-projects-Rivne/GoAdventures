import axios, { AxiosResponse } from 'axios';
import { ErrorMessage } from 'formik';
import React, { ChangeEvent, Component, ReactNode, SyntheticEvent } from 'react';
import { Cookies, withCookies } from 'react-cookie';
import { changeUserData } from '../../../api/user.service';
import { UserDto } from '../../../interfaces/User.dto';
import './EditForm.scss';

// interface EditFormState {
//     userProfile: UserDto;
//     show: boolean;
//     errorForm: string;
//   }
export class EditForm extends Component<any, UserDto> {
    private cookies: Cookies;
    constructor(props: any) {
        super(props);
        this.cookies = props.cookies;
        this.state = {
            fullName: '',
            userName: '',
            email: '',
            password: '',
            newPassword: '',
            repeatNewPassword: '',
            errorMesage: false
        };

        this.hadleEmailChange = this.hadleEmailChange.bind(this);
        this.hadleUserNameChange = this.hadleUserNameChange.bind(this);
        this.hadleFullNameChange = this.hadleFullNameChange.bind(this);
        this.hadlePasswordChange = this.hadlePasswordChange.bind(this);
        this.hadleNewPasswordChange = this.hadleNewPasswordChange.bind(this);
        this.handleRepeatNewPasswordChange = this.handleRepeatNewPasswordChange.bind(this);

        this.handleSubmit = this.handleSubmit.bind(this);
    }


    public handleSubmit(event: SyntheticEvent) {
        event.preventDefault();
        console.log('form is submitted');


        if (this.state.password === '' && this.state.newPassword === '' && this.state.email === ''         // це рофл
            && this.state.fullName === '' && this.state.userName === '' && this.state.repeatNewPassword === '') {
            alert('Data not changed, pls enter new data');
        } else if (this.state.newPassword !== '' && this.state.password === '') {
            alert('Pls enter current password!');
        } else if (this.state.newPassword === '' && this.state.password !== '') {
            alert('Pls enter new password!');
        } else if (this.state.newPassword !== this.state.repeatNewPassword) {
            alert('New password and Repeat password don\'t same');
        } else {
            const config = {
                headers: {
                    'Authorization': `Bearer ${this.cookies.get('tk879n')}`,
                    'Content-Type': 'application/json'
                }
            };

        }
    }

    public hadleEmailChange(event: ChangeEvent<HTMLInputElement>) {
        console.log('email is changed ' + event.target.value);
        this.setState({ email: event.target.value });
    }
    public hadleUserNameChange(event: ChangeEvent<HTMLInputElement>) {
        console.log('userName is changed ' + event.target.value);
        this.setState({ userName: event.target.value });
    }
    public hadleFullNameChange(event: ChangeEvent<HTMLInputElement>) {
        console.log('fullName is changed ' + event.target.value);
        this.setState({ fullName: event.target.value });
    }
    public hadlePasswordChange(event: ChangeEvent<HTMLInputElement>) {
        console.log('password is changed ' + event.target.value);
        this.setState({ password: event.target.value });
    }
    public hadleNewPasswordChange(event: ChangeEvent<HTMLInputElement>) {
        console.log('new password is changed ' + event.target.value);
        this.setState({ newPassword: event.target.value });
    }

    public handleRepeatNewPasswordChange(event: ChangeEvent<HTMLInputElement>) {
        console.log('repeat password is ' + event.target.value);
        this.setState({ repeatNewPassword: event.target.value });
    }

    public render() {
        return (
            <div className='card content' >
                <form onSubmit={this.handleSubmit} >
                    <div className='card border-light mb-3' >
                        <div className='card-header'><h3> Edit Profile </h3></div>
                        <div className='card-body'>
                            <div className='row'>
                                <div className='col'>
                                    <label id='label_on_input' htmlFor='exampleInputEmail'>
                                        Email address
                                </label>
                                    <br />

                                    <input
                                        className='form-control form-control-lg'
                                        type='email'
                                        placeholder='Enter new email'
                                        id='inputLarge'
                                        onChange={this.hadleEmailChange}
                                        value={this.state.email} />
                                    <div className='form-field-error'>errors.email</div>

                                </div>

                                <div className='col'>
                                    <label id='label_on_input' htmlFor='examplefullName'>
                                        FullName
                                    </label>
                                    <br />

                                    <input
                                        className='form-control form-control-lg'
                                        type='text'
                                        placeholder='Enter fullname'
                                        id='inputLarge'
                                        onChange={this.hadleFullNameChange}
                                        value={this.state.fullName} />
                                </div>

                                <div className='w-100' />

                                <div className='col'>
                                    <label id='label_on_input' htmlFor='exampleUserName'>
                                        UserName  </label>
                                    <br />
                                    <input
                                        className='form-control form-control-lg'
                                        type='text'
                                        placeholder='Enter username'
                                        id='inputLarge'
                                        onChange={this.hadleUserNameChange}
                                        value={this.state.userName} />
                                </div>
                                <div className='col'>
                                    <label id='label_on_input' htmlFor=' exampleInputCurrentPassword'>
                                        Current password
                                    </label>
                                    <br />
                                    <input
                                        className='form-control form-control-lg'
                                        type='password'
                                        placeholder='Enter current password'
                                        id='inputLarge'
                                        onChange={this.hadlePasswordChange}
                                        value={this.state.password} />

                                </div>
                                <div className='w-100' />


                                <div className='col'>
                                    <label id='label_on_input' htmlFor=' exampleInputNewPassword'>
                                        New password
                                </label>
                                    <br />
                                    <input
                                        className='form-control form-control-lg'
                                        type='password'
                                        placeholder='Enter new password'
                                        id='inputLarge'
                                        onChange={this.hadleNewPasswordChange}
                                        value={this.state.newPassword} />
                                </div>

                                <div className='col'>
                                    <label id='label_on_input' htmlFor=' exampleInputRepeatNewPass'>
                                        Repeat new password
                                </label>
                                    <br />
                                    <input
                                        className='form-control form-control-lg'
                                        type='password'
                                        placeholder='Enter new password'
                                        id='inputLarge'
                                        onChange={this.handleRepeatNewPasswordChange}
                                        value={this.state.repeatNewPassword} />
                                </div>


                                <div className='w-100' />

                                <label id='error mesge' htmlFor=' exampleInputRepeatNewPass'>
                                    {this.state.errorMesage}
                                </label>


                                <div className='col'>
                                    <button type='button' className='btn btn-primary'
                                        onClick={this.handleSubmit} > Save </button>
                                </div>

                                <div className='col'>
                                    <button type='button' className='btn btn-primary'
                                    > Cancel </button>
                                </div>

                            </div>
                        </div>
                    </div>


                </form>
            </  div >
        );
    }
}
export default withCookies(EditForm);
