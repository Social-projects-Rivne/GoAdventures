import React, { ChangeEvent, Component, ReactNode, SyntheticEvent } from 'react';
import { UserDto } from "../../../interfaces/User.dto";
import axios from "axios";
import { Sidebar } from './Sidebar';

export class EditForm extends Component<any, UserDto>{

    constructor(props: UserDto) {
        super(props);
        this.state = {
            fullname: '',
            username: '',
            email: '',
            avatarUrl: '',
            password: ''


        };

        this.hadleEmailChange = this.hadleEmailChange.bind(this);
        this.hadleUserNameChange = this.hadleUserNameChange.bind(this);
        this.hadleFullNameChange = this.hadleFullNameChange.bind(this);
        this.hadlePasswordChange = this.hadlePasswordChange.bind(this);

        this.handleSubmit = this.handleSubmit.bind(this);
    }


    handleSubmit(event: SyntheticEvent) {
        event.preventDefault();
        console.log('form is submitted');
        if (this.state.avatarUrl == "" && this.state.email == "" && this.state.fullname == "" && this.state.username == "") {
            alert("Data not changed, pls enter new data")
        }
        else {
            const config = {
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem('tkn879')}`,
                    'Content-Type': 'application/json'
                }
            };

            axios.post('http://localhost:8080/profile/edit-profile', { ...this.state }, config)
                .then((response) => {
                    localStorage.setItem('tkn879', response.headers.authorization.replace('Bearer ', '')),
                        this.setState(response.data)
                }
                );
            alert("Saved successfully")


        }
        console.log('saved successfully')
    }

    hadleEmailChange(event: ChangeEvent<HTMLInputElement>) {                //викликається при зміні інпута емейлa
        console.log('email is changed ' + event.target.value);
        this.setState({ email: event.target.value });
    }
    hadleUserNameChange(event: ChangeEvent<HTMLInputElement>) {
        console.log('username is changed ' + event.target.value);
        this.setState({ username: event.target.value });
    }
    hadleFullNameChange(event: ChangeEvent<HTMLInputElement>) {
        console.log('fullname is changed' + event.target.value);
        this.setState({ fullname: event.target.value });
    }
    hadlePasswordChange(event: ChangeEvent<HTMLInputElement>) {
        console.log('password is changed' + event.target.value);
        this.setState({ password: event.target.value });
    }


    render() {
        return (
            <div>
                <form onSubmit={this.handleSubmit} >
                    <label className='list-group-item' htmlFor="exampleInputEmail1">
                        Email address
                        <input type="email" className="form-control" id="exampleInputEmail1"
                            aria-describedby="emailHelp" placeholder="enter email"
                            onChange={this.hadleEmailChange} value={this.state.email} />
                    </label>
                    <label className='list-group-item' htmlFor="exampleInputUsername">
                        Username
                        <input type="text" className="form-control" id="exampleInputUsername"
                            onChange={this.hadleUserNameChange} value={this.state.username}
                            aria-describedby="inputHelp" placeholder="Enter new username" />
                    </label>
                    <label className='list-group-item' htmlFor="exampleInputFullname">
                        Fullname
                        <input type="text" className="form-control" id="exampleInputFullname"
                            onChange={this.hadleFullNameChange} value={this.state.fullname}
                            aria-describedby="inputHelp" placeholder="Enter new fullname" />
                    </label>
                    <label className='list-group-item' htmlFor="exampleInputPassword">
                        Password
                        <input type="password" className="form-control" id="exampleInputPassword"
                            onChange={this.hadlePasswordChange} value={this.state.password}
                            aria-describedby="inputHelp" placeholder="Enter new password" />
                    </label>
                    <button type="button" className="btn btn-primary" onClick={this.handleSubmit} > Save </button>
                </form>
            </div>
        );
    }
}