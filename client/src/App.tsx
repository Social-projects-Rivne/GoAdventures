import React, { Component } from 'react';
import './App.scss';
import { Content, Footer, Navbar } from './components';
import { AuthContext, user } from './context/auth.context';
import { Auth } from './context/auth.context.interface';

class App extends Component<{}, Auth> {
  constructor(props: any) {
    super(props);
    this.state = {
      ...user,
      authorize: async (reqType: (data?: object) => any, data?: object) => {
        const request = await reqType({ ...data });
        console.debug(request);
        if (request) {
          this.setState({authorized: this.state.authorized && localStorage.getItem('tkn879') ?
        this.state.authorized : !this.state.authorized});
        }
      },
      toggleAuthType: (): void => {
        this.setState((state) => ({
          authType: state.authType === 'signUp' ? 'signIn' : 'signUp'
        }));
      }
    };
  }

  // public componentDidUpdate() {

  // }

  public componentDidMount() {
    this.setState({ authorized: !!localStorage.getItem('tkn879') });
    console.debug('Will mount with state ' + this.state.authorized);
  }

  public render() {
    console.debug(this.state);
    return (
      <div>
        <AuthContext.Provider value={this.state}>
          <Navbar authorized={this.state.authorized} />
          <Content authorized={this.state.authorized} />
          <Footer />
        </AuthContext.Provider>
      </div>
    );
  }
}

export default App;
