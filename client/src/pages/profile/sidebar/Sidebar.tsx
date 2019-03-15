import React from 'react';

import './Sidebar.scss';
import { UserDto } from '../../../interfaces/User.dto';
import avatar from '../images/Person.png';
import { EditForm } from "./EditForm";

export class Sidebar extends React.Component<any, UserDto>{

    constructor(props: any) {
        super(props);
        this.state = {
            fullName: '',
            userName: '',
            email: '',
            avatarUrl: '',
            show: false
        };
    }

    toggle() {
        this.setState({ show: !this.state.show });
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
                            <img src={avatar} alt="user_avatar" />
                        </div>
                    </li>
                    <li className="list-group-item">email: {this.props.email}</li>
                    <li className="list-group-item">username: {this.props.userName} </li>
                    <li className="list-group-item">fullname: {this.props.fullName}</li>
                    <li className="list-group-item">
                        <button type="button" className="btn btn-success disabled" onClick={this.toggle.bind(this)}>
                            Edit Profile
                        </button>
                        {this.state.show ? <EditForm /> : null}
                    </li>
                </ul>
            </div>
        );
    }
}