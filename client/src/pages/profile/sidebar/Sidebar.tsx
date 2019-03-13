import React, { Component, SyntheticEvent, ChangeEvent } from 'react';

import './Sidebar.scss';
import { UserDto } from '../../../interfaces/User.dto';
import axios, { AxiosResponse } from "axios";
import { EventDto } from '../../../interfaces/Event.dto';


export class Sidebar extends React.Component<any, UserDto>{

    constructor(props: any) {
        super(props);
        this.state = {
            fullname: '',
            username: '',
            email: '',
            avatarUrl: '',

        };
        this.hadleEmailChange = this.hadleEmailChange.bind(this);
        this.hadleUserNameChange = this.hadleUserNameChange.bind(this);
        this.hadleFullNameChange = this.hadleFullNameChange.bind(this);

        this.handleSubmit = this.handleSubmit.bind(this);


    }

    handleSubmit(event: SyntheticEvent) {
        event.preventDefault();
        console.log('form is submitted')

        const config = { headers: { 'Authorization': `Bearer ${localStorage.getItem('tkn879')}`, 'Content-Type': 'application/json' } };

        axios.post('http://localhost:8080/profile/edit-profile', { ...this.state }, config)
            .then((response) => {
                localStorage.setItem('tkn879', response.headers.authorization.replace('Bearer ', '')),
                    this.setState(response.data)
            }
            );


        console.log('saved successfully')



    }
    hadleEmailChange(event: ChangeEvent<HTMLInputElement>) {                        //викликається при зміні інпута емейлa
        console.log('email is changed ' + event.target.value)
        this.setState({ email: event.target.value });
    }
    hadleUserNameChange(event: ChangeEvent<HTMLInputElement>) {
        console.log('username is changed ' + event.target.value)
        this.setState({ username: event.target.value });
    }
    hadleFullNameChange(event: ChangeEvent<HTMLInputElement>) {
        console.log('fullname is changed' + event.target.value)
        this.setState({ fullname: event.target.value });
    }

    render() {
        return (
            <div className='Sidebar-wrapper' >

                <ul className="list-group list-group-flush">

                    <li className="list-group-item">
                        <h2 className="title"> My Profile  </h2>

                    </li>

                    <li className="list-group-item">

                        <div className='Sidebar__avatar'>
                            <img src="https://i.ytimg.com/vi/aVeCYjAiQHo/maxresdefault.jpg" alt="user_avatar" />
                        </div>
                    </li>

                    <li className="list-group-item">email: {this.props.email}</li>
                    <li className="list-group-item">username: {this.props.userName} </li>
                    <li className="list-group-item">fullname: {this.props.fullName}</li>
                    <li className="list-group-item"> <button type="button" className="btn edit" > Edit Profile </button></li>

                </ul>
                <div>

                    <form onSubmit={this.handleSubmit} >

                        <label className='list-group-item' htmlFor="exampleInputEmail1">Email address
                            <input type="email" className="form-control" id="exampleInputEmail1"
                                aria-describedby="emailHelp" placeholder="enter email" onChange={this.hadleEmailChange} value={this.state.email} />
                        </label>

                        <label className='list-group-item' htmlFor="exampleInput">Username
                            <input type="text" className="form-control" id="exampleInput" onChange={this.hadleUserNameChange} value={this.state.username} aria-describedby="inputHelp" placeholder="Enter new username" />
                        </label>

                        <label className='list-group-item' htmlFor="exampleInput">Fullname
                            <input type="text" className="form-control" id="exampleInput" onChange={this.hadleFullNameChange} value={this.state.fullname} aria-describedby="inputHelp" placeholder="Enter new fullname" />
                            <button type="button" className="btn btn-primary" onClick={this.handleSubmit} > Save </button>
                        </label>





                    </form>
                </div>


            </div>





        );

    }
}