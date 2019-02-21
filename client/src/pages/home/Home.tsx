import React, { Component } from 'react';
import './Home.scss';

export class Home extends Component {
  public render() {
    return (
      <div className='Home-content'>
        <div className='Home-heading d-flex flex-column align-items-baseline'>
          <h2>GO</h2>
        </div>
        <div className='Home-heading d-flex flex-column align-items-end'>
          <h2>Adventures!</h2>
        </div>
      </div>
    );
  }
}
