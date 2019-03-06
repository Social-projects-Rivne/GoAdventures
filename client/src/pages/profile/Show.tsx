import axios from 'axios';
import React, { ChangeEvent, Component } from 'react';
import { ProfileSetting } from '../../components/dialog-window/interfaces/profile.interface';
import avatar from '../../data/image/Person.jpg';
import './Show.scss';

export class Show extends Component<any, ProfileSetting> {
  constructor(props: any) {
    super(props);
    this.state = {
        email: '',
        fullName: '',
        userName: '',
        userUrl: ''
    };
  }


  public componentDidMount() {
    axios.get('http://localhost:8080/profile/page', {
        headers: {
          'Authorization': `Bearer ${localStorage.getItem('tkn879')}`,
          'Content-Type': 'application/json'
        }
      })
      .then((response) =>
        this.setState({
          fullName: response.data.fullName,
          userName: response.data.userName,
          email: response.data.email
        })
      );
  }

  public render() {
    return (
      <div>
        <div className='User'>
          <div className='User-image'>
            <img className='User__avatar' src={avatar} alt='avatar' />
          </div>

          <div className='User-Data'>
            <div className='user-detail'>
              <p className=''>{this.state.fullName}</p>
              <p className=''>{this.state.userName}</p>
              <p className=''>{this.state.email}</p>
            </div>
          </div>
        </div>
      </div>
    );
  }
}
export default Show;
