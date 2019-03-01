import React, { ChangeEvent, Component } from 'react';
import avatar from '../../data/image/Person.jpg';
import './Dialog.scss';
import {ProfileSetting} from '../../components/dialog-window/interfaces/profile.interface';
import axios from 'axios';

export class Dialog extends Component<any, ProfileSetting> {
    constructor (props: any) {
        super(props)
        this.state = {
          fullName: '',
          userName: '',
          email: '',
          userUrl: ''
        }
        
        axios.get('http://localhost:8080/profile/1first_user1')
          .then(response => this.setState(
            {
              fullName: response.data.fullName,
              userName: response.data.userName,
              email: response.data.email
            }))
      }

    public render() {
        return(
            <div>
               <div className="User">
                    <div className="User-image">
                        <img src={avatar} alt="avatar"/>
                    </div>
    
                    <div className="User-Data">
                        <div className="user-detail">
                            <p className="">{this.state.fullName}</p>
                            <p className="">{this.state.userName}</p>
                            <p className="">{this.state.email}</p>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
} 
export default Dialog;