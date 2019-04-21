import axios, { AxiosResponse } from 'axios';
import React, { ChangeEvent } from 'react';
import { Cookies, withCookies } from 'react-cookie';
import { serverUrl } from '../../../api/url.config';
import { ProfileContext } from '../../../context/profile.context';
import { UserDto } from '../../../interfaces/User.dto';
import avatar from '../images/Person.png';
import './Sidebar.scss';
import { uploadAvatar } from '../../../api/user.service';

interface SidebarState {
  userProfile: UserDto;
  avatar: string | Blob;
  tempAvatar: string | Blob;
  errorMesage: {
    publicError: string;

  };

}
class Sidebar extends React.Component<UserDto, SidebarState> {

  fileInput: any;
  constructor(props: any) {
    super(props);
    this.state = {
      avatar: '',
      tempAvatar: '',
      errorMesage: {
        publicError: '',
      },
      userProfile: {
        fullname: '',
        username: '',
        email: '',
        avatarUrl: ''
      },
    };
    this.clearErrorMessage = this.clearErrorMessage.bind(this);
    this.uploadHandler = this.uploadHandler.bind(this);
    this.fileSelectHandler = this.fileSelectHandler.bind(this);
  }

  componentWillReceiveProps(newProps: any) {
    this.setState({ userProfile: newProps })
  }

  public clearErrorMessage() {
    this.setState({
      errorMesage: { publicError: '' }
    }
    );
  }
  public fileSelectHandler(event: ChangeEvent<HTMLInputElement>): void {
    console.debug(event.target.files);
    !!event.target.files ?
      this.setState({
        avatar: event.target.files[0]
      }) : null;
  }

  async uploadHandler() {
    const formdata: FormData = new FormData();
    formdata.set('file', this.state.avatar);
    await uploadAvatar(formdata)
      .then(response => {
        this.setState({
          userProfile: {
            ...this.state.userProfile,
            avatarUrl: response.data,

          }
        });
        console.debug('avatar uploaded', this.state.userProfile.avatarUrl);

      })
      .catch((err) => {
        this.setState({ errorMesage: { ...err.response.data } }, () => {
          window.setTimeout(() => {
            this.setState({ errorMesage: { publicError: '' } })
          }, 3500)
        });
      });
  }

  public render() {
    return (
      <ProfileContext.Consumer>
        {
          ({ togleMyEvents, togleEditProfile, toogleAccountOverView, setAvatar }) => (
            <div className='Sidebar__card card text-white bg-dark'>
              <div className='card-header'>
                <h2 id="sidebarTitle" className='title sidebarTitle'>{this.state.userProfile.fullname}</h2>
                <div className='Sidebar__avatar'>

                  <img
                    src={(this.state.userProfile.avatarUrl != undefined && this.state.userProfile.avatarUrl != '') ? this.state.userProfile.avatarUrl : avatar}
                    alt='user_avatar' />
                </div>
                <div className="change_avatar_btn">
                  <input
                    style={{ display: 'none' }}
                    type='file'
                    onChange={this.fileSelectHandler}
                    ref={(fileInput) => this.fileInput = fileInput}
                  />
                  <button
                    className="btn btn-success changeAvatarBtn"
                    onClick={() => this.fileInput.click()} > {(this.state.userProfile.avatarUrl !== undefined && this.state.userProfile.avatarUrl !== '') ? <div>Update avatar</div> : <div>Change avatar</div>}
                  </button>
                  <button
                    style={this.state.avatar == '' ? { display: 'none' } : { display: 'flex' }}
                    className="btn btn-warning"
                    onClick={

                      async () => {
                        await this.uploadHandler();
                        if (this.state.userProfile.avatarUrl !== undefined) {
                          setAvatar(this.state.userProfile.avatarUrl)
                        }

                      }
                    }

                  >Upload</button>
                </div>
                <div className="Errors-messages avtErrorsWraper">
                  {

                    this.state.errorMesage.publicError !== ''
                      ? <div className="alert alert-danger alert-dismissible fade show  errAvatarMessage"
                        role="alert">
                        <strong>{this.state.errorMesage.publicError}</strong>
                        <button type="button"
                          onClick={this.clearErrorMessage}
                          className="close"
                          data-dismiss="alert"
                          aria-label="Close">

                          <span aria-hidden="true">&times;</span>
                        </button>
                      </div>
                      : null}
                </div>
              </div>

              <div className='card-body' id="sdb111">

                <div className='btn-choice'>
                  <button className='btn btn-secondary'
                    id='sidebarBtn'
                    onClick={togleEditProfile}>
                    Edit profile
                </button>
                  <button className='btn btn-secondary'
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
            </div >


          )
        }
      </ProfileContext.Consumer >
    );
  }
}
export default withCookies(Sidebar);