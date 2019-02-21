import React, { Component } from 'react';
import './App.scss';
import { Content, Footer, Navbar } from './components';

class App extends Component {
  public render() {
    return (
      <div>
        <Navbar />
        <Content />
        <Footer />
      </div>
    );
  }
}

export default App;
