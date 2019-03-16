import React, { Component } from 'react';
import { Cookies, withCookies } from 'react-cookie';
import './App.scss';
import { Content, Footer, Navbar } from './components';
import { AuthContext, user } from './context/auth.context';
import { Auth } from './context/auth.context.interface';

class App extends Component<any, Auth> {
  private cookies: Cookies;
  constructor(props: any) {
    super(props);
    this.cookies = props.cookies;
    this.state = {
      ...user,
      authorize: async (reqType: (data?: object) => any, data?: object) => {
        const request: string = await reqType({ ...data });
        if (request === 'ok') {
          this.setState({
            authorized: this.cookies.get(('tk879n')) && this.state.authorized === false ?
              !this.state.authorized : false
          });
        } else {
          this.setState({
            messages: request
          });
        }
      },
      toggleAuthType: (): void => {
        this.setState((state) => ({
          authType: state.authType === 'signUp' ? 'signIn' : 'signUp'
        }));
      }
    };
  }


  public componentWillMount() {
    this.setState({ authorized: !!this.cookies.get(('tk879n'))});
  }


  public render() {
    return (
      <div>
        <AuthContext.Provider value={this.state}>
          <Navbar authorized={this.state.authorized} />
          <Content />
          {this.state.authorized ? null : <Footer />}
        </AuthContext.Provider>
      </div>
    );
  }
}

export default withCookies(App);
