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
                    this.cookies.set('tk879n', response.headers.authorization.replace('Bearer ', '')),
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

    public render() {
        const { cookies } = this.props;
        return (
            <div className=''>
                <div className='' >
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
                        </div>
                    </div>
                </div>
                <div className=''>
                    {/*this.state.show ? this.editForm() : this.eventList()*/}
                </div>
            </div>
        );
    }
}

export default withCookies(Sidebar);
