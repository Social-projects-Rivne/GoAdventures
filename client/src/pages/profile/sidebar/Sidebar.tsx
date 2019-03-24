import React from 'react';
import { withCookies } from 'react-cookie';
import { UserDto } from '../../../interfaces/User.dto';
import avatar from '../images/Person.png';
import './Sidebar.scss';

interface SelectFlags {
  userProfile: UserDto;
  showEditForm: boolean;
  
}

class Sidebar extends React.Component<any, SelectFlags>{
  //   private cookies: Cookies;
  constructor(props: any) {
    super(props);
    // this.cookies = props.cookies;
    this.state = {
      showEditForm: true,
      userProfile: {
        fullname: '',
        username: '',
        email: '',
        avatarUrl: ''
      },
  
    };
  }
  

  public render() {
    // const { cookies } = this.props;
    return (
      <div className='Sidebar__card card text-white bg-dark'>
        <div className='card-header'>
          <div className='Sidebar__avatar'>
            <img src={avatar} alt='user_avatar' />
          </div>
          {/* <input type='file' onClick={this.fileSelectHandler}></input> */}
        </div>

        <div className='card-body'>
          <div ><button 
                type="button"
                
                className="btn btn-secondary sidebarBt">
                Account Overview
                </button>

          </div>
          <div ><button 
                type="button" 
                 
                className="btn btn-secondary sidebarBt">Edit Profile
                </button>

          </div>
          <div ><button 
                type="button" 
                className="btn btn-secondary sidebarBt">Change password
                </button>

          </div>
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
        </div>
      </div>
    );
  }
}

export default withCookies(Sidebar);
