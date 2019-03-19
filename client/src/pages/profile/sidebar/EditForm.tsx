import React, { ChangeEvent, Component, ReactNode, SyntheticEvent } from 'react';
import { UserDto } from '../../../interfaces/User.dto';
import axios, { AxiosResponse } from 'axios';
import { Cookies, withCookies } from 'react-cookie';

export class EditForm extends Component<any, UserDto>{
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
            repeatNewPassword: ''
        };

        this.hadleEmailChange = this.hadleEmailChange.bind(this);
        this.hadleUserNameChange = this.hadleUserNameChange.bind(this);
        this.hadleFullNameChange = this.hadleFullNameChange.bind(this);
        this.hadlePasswordChange = this.hadlePasswordChange.bind(this);
        this.hadleNewPasswordChange = this.hadleNewPasswordChange.bind(this);
        this.handleRepeatNewPasswordChange = this.handleRepeatNewPasswordChange.bind(this);

        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleSubmit(event: SyntheticEvent) {
        event.preventDefault();
        console.log('form is submitted');


        if (this.state.password === '' && this.state.newPassword === '' && this.state.email === ''         //це рофл
            && this.state.fullName === '' && this.state.userName === '') {
            alert('Data not changed, pls enter new data')
        } else if (this.state.newPassword !== '' && this.state.password === '') {
            alert('Pls enter current password!')
        } else if (this.state.newPassword === '' && this.state.password !== '') {
            alert('Pls enter new password!')
        } else if(this.state.newPassword !== this.state.repeatNewPassword) {
            alert('New password and Repeat password don\'t same')
        } else {
            const config = {
                headers: {
                    'Authorization': `Bearer ${this.cookies.get('tk879n')}`,
                    'Content-Type': 'application/json'
                }
            };

            axios.post('http://localhost:8080/profile/edit-profile', { ...this.state }, config)
                .then((response: AxiosResponse) => {
                    this.cookies.set('tk879n', response.headers.authorization.replace('Bearer ', '')),
                        this.setState(response.data)
                });
            alert('Saved successfully')
        }
        console.log('saved successfully')
    }

    hadleEmailChange(event: ChangeEvent<HTMLInputElement>) {
        console.log('email is changed ' + event.target.value);
        this.setState({ email: event.target.value });
    }
    hadleUserNameChange(event: ChangeEvent<HTMLInputElement>) {
        console.log('userName is changed ' + event.target.value);
        this.setState({ userName: event.target.value });
    }
    hadleFullNameChange(event: ChangeEvent<HTMLInputElement>) {
        console.log('fullName is changed ' + event.target.value);
        this.setState({ fullName: event.target.value });
    }
    hadlePasswordChange(event: ChangeEvent<HTMLInputElement>) {
        console.log('password is changed ' + event.target.value);
        this.setState({ password: event.target.value });
    }
    hadleNewPasswordChange(event: ChangeEvent<HTMLInputElement>) {
        console.log('new password is changed ' + event.target.value);
        this.setState({ newPassword: event.target.value });
    }

    handleRepeatNewPasswordChange(event: ChangeEvent<HTMLInputElement>) {
        console.log('repeat password is ' + event.target.value);
        this.setState({ repeatNewPassword: event.target.value});
    }

    render() {
        return (
            <div className='card content'>
            <form onSubmit={this.handleSubmit} >
                <div className='card border-light mb-3' >
                    <div className='card-header'><h3> Edit Profile </h3></div>
                    <div className='card-body'>
                        <div className='row'>
                            <div className='col'>
                                <label className='' htmlFor='exampleInputEmail'>
                                    Email address
                                </label>
                                <br />
                                <input type='email' id='exampleInputEmail'
                                    aria-describedby='emailHelp' placeholder='enter email'
                                    onChange={this.hadleEmailChange} value={this.state.email} />
                            </div>

                            <div className='col'>
                                <label className='' htmlFor='examplefullName'>
                                    fullName
                                </label>
                                <br />

                                <input type='text' id='examplefullName'
                                    aria-describedby='emailHelp' placeholder='enter fullname'
                                    onChange={this.hadleFullNameChange} value={this.state.fullName} />

                            </div>
                            
                            <div className='w-100' ></div>

                            <div className='col'>
                                <label className='' htmlFor='exampleUserName'>
                                    UserName  </label>
                                <br />
                                <input type='text' id='exampleUserName'
                                    aria-describedby='emailHelp' placeholder='enter username'
                                    onChange={this.hadleUserNameChange} value={this.state.userName} />
                            </div>
                            <div className='col'>
                                <label className='' htmlFor=' exampleInputCurrentPassword'>
                                    Current password
                                </label>
                                <br />
                                <input type='password' className='exampleInputEmail1 ' id='exampleInputCurrentPassword'
                                    aria-describedby='emailHelp' placeholder='enter current password'
                                    onChange={this.hadlePasswordChange} value={this.state.password} />
                            </div>
                            <div className='w-100' >

                            </div>
                            <div className='col'>
                                <label className='' htmlFor=' exampleInputNewPassword'>
                                    New password
                                </label>
                                <br />
                                <input type='password' id='exampleInputNewPassword'
                                    aria-describedby='emailHelp' placeholder='enter new password'
                                    onChange={this.hadleNewPasswordChange} value={this.state.newPassword} />
                            </div>

                            <div className='col'>
                                <label className='' htmlFor=' exampleInputRepeatNewPass'>
                                    Repeat new password
                                </label>
                                <br />
                                <input type='password' id='exampleInputRepeatNewPass'
                                    aria-describedby='emailHelp' placeholder='repeat new password'
                                    onChange={this.handleRepeatNewPasswordChange} value={this.state.repeatNewPassword} />
                            </div>


                            <div className='w-100' ></div>


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
            </div >
        );
    }
}
export default withCookies(EditForm);