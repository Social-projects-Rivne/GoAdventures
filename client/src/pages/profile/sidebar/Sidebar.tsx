import React from 'react';
import { withCookies } from 'react-cookie';
import { ProfileContext } from '../../../context/profile.context';
import { UserDto } from '../../../interfaces/User.dto';
import avatar from '../images/Person.png';
import './Sidebar.scss';

class Sidebar extends React.Component<UserDto, UserDto> {
  constructor(props: any) {
    super(props);
    this.state = {
      fullname: '',
      username: '',
      email: '',
      avatarUrl: '',
      password: '',
      newPassword: ''
    };
  }

  public render() {
    return (
      <ProfileContext.Consumer>
        {({ togleMyEvents, togleEditProfile }) => (
          <div className='Sidebar__card card text-white bg-dark'>
            <div className='card-header'>
              <h2 className='title'>My Profile</h2>
              <div className='Sidebar__avatar'>
                <img src={avatar} alt='user_avatar' />
              </div>
            </div>

            <div className='card-body'>
              <div className='list-group'>
                <a className='list-group-item list-group-item-action active'>
                  <div className='d-flex w-100 justify-content-between'>
                    <h5 className='mb-1'>
                      <p>Email:</p>
                      {this.props.email}
                    </h5>
                  </div>
                </a>
              </div>
              <div className='list-group'>
                <a className='list-group-item list-group-item-action active'>
                  <div className='d-flex w-100 justify-content-between'>
                    <h5 className='mb-1'>
                      <p>Username:</p>
                      {this.props.username}
                    </h5>
                  </div>
                </a>
              </div>
              <div className='list-group'>
                <a className='list-group-item list-group-item-action active'>
                  <div className='d-flex w-100 justify-content-between'>
                    <h5 className='mb-1'>
                      <p>Fullname:</p>
                      {this.props.fullname}
                    </h5>
                  </div>
                </a>
              </div>

              <div className='btn-choice'>
                <button
                  className='btn btn-secondary disabled edit'
                  onClick={togleEditProfile}
                >
                  Edit profile
                </button>
                <button
                  className='btn btn-secondary disabled events'
                  onClick={togleMyEvents}
                >
                  My events
                </button>
              </div>
            </div>
          </div>
        )}
      </ProfileContext.Consumer>
    );
  }
}
export default withCookies(Sidebar);
