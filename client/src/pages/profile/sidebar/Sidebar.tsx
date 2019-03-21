import React from 'react';
import { withCookies } from 'react-cookie';
import { UserDto } from '../../../interfaces/User.dto';
import avatar from '../images/Person.png';
import './Sidebar.scss';

interface SelectProtected {
  readonly wrapperElement: HTMLDivElement;
  readonly inputElement: HTMLInputElement;
}

class Sidebar extends React.Component<any, UserDto> {
  //   private cookies: Cookies;
  constructor(props: any) {
    super(props);
    // this.cookies = props.cookies;
    this.state = {
      fullName: '',
      userName: '',
      email: '',
      avatarUrl: '',
      password: '',
      newPassword: '',
      show: false
    };
  }
  public toggleEdit() {
    this.setState({ show: true });
  }
  public toggleEvents() {
    this.setState({ show: false });
  }

  public render() {
    // const { cookies } = this.props;
    return (
      <div className='card text-white bg-dark'>
        <div className='card-header'>
          <h2 className='title'> My Profile</h2>
          <div className='Sidebar__avatar'>
            <img src={avatar} alt='user_avatar' />
          </div>
          {/* <input type='file' onClick={this.fileSelectHandler}></input> */}
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
                  {this.props.userName}
                </h5>
              </div>
            </a>
          </div>
          <div className='list-group'>
            <a className='list-group-item list-group-item-action active'>
              <div className='d-flex w-100 justify-content-between'>
                <h5 className='mb-1'>
                  <p>Fullname:</p>
                  {this.props.fullName}
                </h5>
              </div>
            </a>
          </div>
        </div>
      </div>
    );
  }
}

export default withCookies(Sidebar);
