import React, { SyntheticEvent, ChangeEvent } from 'react';
import { withCookies, Cookies } from 'react-cookie';
import { UserDto } from '../../../interfaces/User.dto';
import './Sidebar.scss';
import axios, { AxiosResponse } from 'axios';
import { serverUrl } from '../../../api/url.config';


interface SidebarState {
  userProfile: UserDto;
  showEditForm: boolean;
  showInfoForm: boolean;
  avatar: any;
}


const cookies: Cookies = new Cookies();
class Sidebar extends React.Component<any, SidebarState>{
  constructor(props: any) {
    super(props);
    this.state = {
      avatar: null,
      showEditForm: false,
      showInfoForm: true,
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
    !!event.target.files ?
      this.setState({
        avatar: event.target.files[0]
      }) : null;

  }
  public uploadHandler() {
    const formdata = new FormData();
    formdata.append('image', this.state.avatar, "name");
    const config = {
      headers: {
        'Content-Type': 'multipart/form-data',
        'Authorization': `Bearer ${cookies.get('tk879n')}`
      }
    };
    axios.post(
      `${serverUrl}/uploadAvatar`,
      { params: { file: formdata } },
      config
    ).then(response => {
      console.log(response);
    }
    )

  }
  public render() {

    return (
      <div className='Sidebar__card card text-white bg-dark'>
        <div className='card-header'>
          <div className='Sidebar__avatar'>
            <img src={this.props.avatarUrl || "..images/Person.png"} alt='user_avatar' />
          </div>
          <input
            type='file'
            onChange={this.fileSelectHandler}

          >
          </input>
          <button
            onClick={this.uploadHandler}
          >Upload</button>
        </div>

        <div className='card-body'>
          <div ><button
            type="button"
            id="special"
            onClick={() => {
              this.props.updateData(this.state.showInfoForm);
            }
            }
            className="btn btn-secondary">
            Account Overview
                </button>

          </div>
          <div ><button
            type="button"
            onClick={() => {
              this.props.updateData(this.state.showEditForm);
            }
            }
            id="special"
            className="btn btn-secondary">Edit Profile
                </button>

          </div>
          <div ><button
            type="button"
            id="special"
            className="btn btn-secondary">Change password
                </button>

          </div>
        </div>
      </div>
    );
  }
}

export default withCookies(Sidebar);
