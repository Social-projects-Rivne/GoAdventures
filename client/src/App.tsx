import { stat } from 'fs';
import React, { Component } from 'react';
import { signUp } from './api/requests';
import './App.scss';
import { Content, Footer, Navbar } from './components';
import { AuthContext, user } from './context/auth.context';
import { Auth } from './context/auth.context.interface';


class App extends Component<{}, Auth> {
  constructor(props: any) {
    super(props);
    this.state = {
      ...user,
      authorize: async (reqType: () => any) => {
        if (await reqType()) {
          this.setState((state) => ({
            authorized: state.authorized ? state.authorized : !state.authorized
          }));
        }
      },
      toggleAuthType: (): void => {
        this.setState((state) => ({
          authType: state.authType === 'signUp' ? 'signIn' : 'signUp',
        }));
      }
    };
  }

  public render() {
    console.log(this.state.authorized);
    return (
      <div>
        <AuthContext.Provider value={this.state}>
          <Navbar authorized={this.state.authorized} />
          <Content />
          <Footer />
        </AuthContext.Provider>
      </div>
    );
  }
}

export default App;
