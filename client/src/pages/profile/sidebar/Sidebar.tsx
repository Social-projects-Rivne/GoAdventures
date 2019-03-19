import axios, { AxiosResponse } from 'axios';
import React, { ChangeEvent, Component, ReactNode, SyntheticEvent } from 'react';
import { Cookies, withCookies } from 'react-cookie';
import { UserDto } from '../../../interfaces/User.dto';
import avatar from '../images/Person.png';
import './Sidebar.scss';


class Sidebar extends React.Component<any, UserDto> {
    private cookies: Cookies;
    constructor(props: any) {
        super(props);
        this.cookies = props.cookies;
        this.state = {
            fullName: '',
            userName: '',
            email: '',
            avatarUrl: '',
            password: '',
            newPassword: '',
            show: false,

        };
        this.hadleEmailChange = this.hadleEmailChange.bind(this);
        this.hadleUserNameChange = this.hadleUserNameChange.bind(this);
        this.hadleFullNameChange = this.hadleFullNameChange.bind(this);
        this.hadlePasswordChange = this.hadlePasswordChange.bind(this);
        this.hadleNewPasswordChange = this.hadleNewPasswordChange.bind(this);

        this.handleSubmit = this.handleSubmit.bind(this);
        this.hadleChancel = this.hadleChancel.bind(this);

    }
    public hadleChancel(event: SyntheticEvent) {
        console.log('cancel');
        this.setState({ show: false });
    }
    public handleSubmit(event: SyntheticEvent) {
        event.preventDefault();
        console.log('form is submitted');

        if (this.state.password == '' && this.state.newPassword == '' && this.state.email == ''         // це рофл
            && this.state.fullName === '' && this.state.userName === '') {
            alert('Data not changed, pls enter new data');
        } else if (this.state.newPassword !== '' && this.state.password == '') {   // це тоже
            alert('Pls enter current password!');
        } else if (this.state.newPassword == '' && this.state.password !== '') {
            alert('Pls enter new password!');
        } else {
            const config = {
                headers: {
                    'Authorization': `Bearer ${this.cookies.get('tk879n')}`,
                    'Content-Type': 'application/json'
                }
            };

            axios.post('http://localhost:8080/profile/edit-profile', { ...this.state }, config)
                .then((response: AxiosResponse) => {
                    this.cookies.set('tkn879', response.headers.authorization.replace('Bearer ', '')),
                        this.setState(response.data);

                });
            alert('Submited sucesfully!');

        }
        console.log('saved successfully');
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


    public toggleEdit() {
        this.setState({ show: true });
    }
    public toggleEvents() {
        this.setState({ show: false });
    }
    public editForm() {        // render inputs forms
        return (
            <div className='card content'>
                <form onSubmit={this.handleSubmit} >
                    <div className='card border-light mb-3' >
                        <div className='card-header'><h3> Edit Profile </h3></div>
                        <div className='card-body'>
                            <h4 className='card-title'>Поміняй свої дані вася</h4>
                            <p className='card-text'>тут буде форма валідації мб</p>

                            <div className='row'>
                                <div className='col'>
                                    <label className='' htmlFor='exampleInputEmail1'>
                                        Email address
                                    </label>
                                    <br />
                                    <input type='email' id='exampleInputEmail1'
                                        aria-describedby='emailHelp' placeholder='enter email'
                                        onChange={this.hadleEmailChange} value={this.state.email} />
                                </div>

                                <div className='col'>
                                    <label className='' htmlFor='examplefullName1'>
                                        fullName
                                    </label>
                                    <br />

                                    <input type='text' id='examplefullName1'
                                        aria-describedby='emailHelp' placeholder='enter fullname'
                                        onChange={this.hadleFullNameChange} value={this.state.fullName} />

                                </div>
                                <div className='w-100' >

                                </div>

                                <div className='col'>
                                    <label className='' htmlFor='exampleUserNameEmail1'>
                                        UserName  </label>
                                    <br />
                                    <input type='text' id='exampleUserNameEmail1'
                                        aria-describedby='emailHelp' placeholder='enter username'
                                        onChange={this.hadleUserNameChange} value={this.state.userName} />

                                </div>
                                <div className='w-100' >

                                </div>
                                <div className='col'>
                                    <label className='' htmlFor=' exampleInputEmail1'>
                                        Current password
                                    </label>
                                    <br />
                                    <input type='password' className='exampleInputEmail1 ' id='exampleInputEmail1'
                                        aria-describedby='emailHelp' placeholder='enter current password'
                                        onChange={this.hadlePasswordChange} value={this.state.password} />
                                </div>

                                <div className='col'>
                                    <label className='' htmlFor=' exampleInputEmail1'>
                                        New password
                                    </label>
                                    <br />
                                    <input type='password' id='exampleInputEmail1'
                                        aria-describedby='emailHelp' placeholder='enter new password'
                                        onChange={this.hadleNewPasswordChange} value={this.state.newPassword} />
                                </div>


                                <div className='w-100' ></div>


                                <div className='col'>
                                    <button type='button' className='btn btn-primary'
                                        onClick={this.handleSubmit} > Save </button>
                                </div>

                                <div className='col'>
                                    <button type='button' className='btn btn-primary'
                                        onClick={this.hadleChancel} > Cancel </button>
                                </div>

                            </div>
                        </div>
                    </div>


                </form>
            </div >
        );
    }
    public eventList() {
        return (
            <div className='page-content-wrapper'>
                <div className='col'>
                    <div className='list-group'>
                        <h2 className='list-group-item'> Your events </h2>
                        <a href='#' className='list-group-item list-group-item-action active'>
                            Topic:
                        </a>
                        <a href='#' className='list-group-item list-group-item-action disabled'>
                            decription:
                        </a>
                        <a href='#' className='list-group-item list-group-item-action disabled'>
                            start date:
                        </a>
                    </div>
                </div>
            </div>
        );

    }

    public render() {
        const { cookies } = this.props;
        return (
            <div className='row'>
                <div className='col' >
                    <div className='card text-white bg-dark mb-3 sidebar'>
                        <div className='card-header'>
                            <h2 className='title'> My Profile</h2>
                            <div className='Sidebar__avatar'>
                                <img src={avatar} alt='user_avatar' />
                            </div>
                        </div>
                        <div className='card-body'>
                            <div className='list-group'>
                                <a className='list-group-item list-group-item-action active'>
                                    <div className='d-flex w-100 justify-content-between'>
                                        <h5 className='mb-1'><p>Email:</p>{this.props.email}</h5>
                                    </div>
                                </a>
                            </div>
                            <div className='list-group'>
                                <a className='list-group-item list-group-item-action active'>
                                    <div className='d-flex w-100 justify-content-between'>
                                        <h5 className='mb-1'><p>Username:</p>{this.props.userName}</h5>
                                    </div>
                                </a>
                            </div>
                            <div className='list-group'>
                                <a className='list-group-item list-group-item-action active'>
                                    <div className='d-flex w-100 justify-content-between'>
                                        <h5 className='mb-1'><p>Fullname:</p>{this.props.fullName}</h5>
                                    </div>
                                </a>
                            </div>
                            <div >
                                <button type='button' className='btn btn-outline-secondary vidstup'
                                    onClick={this.toggleEdit.bind(this)}>
                                    <h4>Edit Profile </h4>
                                </button>
                            </div>

                            <div>
                                <button type='button' className='btn btn-outline-secondary'
                                    onClick={this.toggleEvents.bind(this)}>
                                    <h4>My Events </h4>
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
                <div className='col'>
                    {this.state.show ? this.editForm() : this.eventList()}
                </div>
            </div>
        );
    }
}

export default withCookies(Sidebar);
