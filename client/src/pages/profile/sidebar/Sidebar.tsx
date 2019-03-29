import axios from 'axios';
import React, { ChangeEvent } from 'react';
import { Cookies, withCookies } from 'react-cookie';
import { serverUrl } from '../../../api/url.config';
import { ProfileContext } from '../../../context/profile.context';
import { UserDto } from '../../../interfaces/User.dto';
import avatar from '../images/Person.png';
import './Sidebar.scss';

interface SidebarState {
  userProfile: UserDto;
  avatar: string | Blob;
}


const cookies: Cookies = new Cookies();
class Sidebar extends React.Component<UserDto, SidebarState> {

  constructor(props: any) {
    super(props);
    this.state = {
      avatar: '',
      userProfile: {
        fullname: '',
        username: '',
        email: '',
        avatarUrl: ''
      },

    };
    this.uploadHandler = this.uploadHandler.bind(this);
    this.fileSelectHandler = this.fileSelectHandler.bind(this);
  }
  public fileSelectHandler(event: ChangeEvent<HTMLInputElement>): void {
    //console.log(event.target.files[0]);
    console.debug(event.target.files);
    !!event.target.files ?
      this.setState({
        avatar: event.target.files[0]
      }) : null;
  }
  public uploadHandler() {
    const formdata: FormData = new FormData();
    formdata.set('file', this.state.avatar);
    const config = {
      headers: {
        'Content-Type': 'multipart/form-data',
        'Authorization': `Bearer ${cookies.get('tk879n')}`
      }
    };
    axios.post(
      `${serverUrl}/uploadAvatar`,
      formdata,
      config
    ).then(response => {
      console.log(response);
    }
    )

  }

  public render() {
    return (
      <ProfileContext.Consumer>
        {({ togleMyEvents, togleEditProfile, toogleAccountOverView }) => (
          <div className='Sidebar__card card text-white bg-dark'>
            <div className='card-header'>
              <h2 className='title'>My Profile</h2>
              <div className='Sidebar__avatar'>
                <img
                  src={this.props.avatarUrl !== '' ? this.props.avatarUrl : avatar}
                  alt='user_avatar' />
              </div>
              <input
                // style={{ display: 'none' }}
                type='file'
                onChange={this.fileSelectHandler}
              // ref={(fileInput) => this.inputFile = fileInput} 
              />
              {/* <button onClick={() => this.fileInput.click()} > Pick File </button> */}
              <button
                onClick={this.uploadHandler}
              >Upload</button>
            </div>

            <div className='card-body'>

              <div className='btn-choice'>
                <button className='btn btn-secondary disabled edit'
                  id='sidebarBtn'
                  onClick={togleEditProfile}>
                  Edit profile
                </button>
                <button className='btn btn-secondary disabled events'
                  id='sidebarBtn'
                  onClick={togleMyEvents}>
                  My events
                </button>
                <button className='btn btn-secondary'
                  id='sidebarBtn'
                  onClick={toogleAccountOverView}>
                  Account OverView
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
