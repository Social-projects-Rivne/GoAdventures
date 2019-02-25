import React, { Component } from 'react';
import Dialog from '../../components/dialog-window/Dialog';
import { InputSettings } from '../../components/dialog-window/interfaces/input.interface';
import './Home.scss';

export class Home extends Component {
  private inputSettings: InputSettings[] = [
    {label_value: 'Label value', placeholder: 'Name', type: 'text' }
  ];
  public render() {
    return (
      <div className='Home-content'>
        <div className='Home-heading d-flex flex-column align-items-baseline'>
          <h2>GO</h2>
        </div>
        <div className='Home-heading d-flex flex-column align-items-end'>
          <h2>Adventures!</h2>
        </div>
        <Dialog label='custom label' inputs={ this.inputSettings } header='Custom header'></Dialog>
      </div>
    );
  }
}
