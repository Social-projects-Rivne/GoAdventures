import React, { Component } from 'react';
import './App.scss';
import { Content, Footer, Navbar } from './components';
import { AuthContext, user } from './context/auth.context';


interface Auth {
  authorized: boolean;
  authorize: (reqType: () => any) => void;
}

class App extends Component<{}, Auth> {
  constructor(props: any) {
    super(props);
    this.state = {
      ...user,
      authorize: async (reqType: () => any) => {
        const request = await reqType();
        request ? console.log('benis') : console.log('vegenis');
        this.setState((state) => ({
          authorized: state.authorized ? state.authorized : !state.authorized
        }));
      }
    };
  }

  public render() {
    console.log(this.state.authorized);
    return (
      <div>
        <AuthContext.Provider value={this.state}>
          <Navbar />
          <Content />
          <Footer />
        </AuthContext.Provider>
      </div>
    );
  }
}

export default App;
