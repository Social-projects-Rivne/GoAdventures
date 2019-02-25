import React, { Component } from 'react';
import { CSSProperties } from 'react';
import { Dialog } from '../../components/';
import { InputSettings } from '../../components/dialog-window/interfaces/input.interface';
import './Home.scss';

export class Home extends Component {

private inputSettings: InputSettings[] = [
    { label_value: 'Label value', placeholder: 'Name', type: 'text' },
    { label_value: 'Label value', placeholder: 'Name', type: 'text' },
    { label_value: 'Label value', placeholder: 'Name', type: 'text' },
  ];



  public testFunc(event: any) {
    event.preventDefault();
  }

  public render() {
    return (
      <div className='Home-content'>
        <div className='Home-heading d-flex flex-column align-items-baseline'>
          <h2>GO</h2>
        </div>
        <div className='Home-heading d-flex flex-column align-items-end'>
          <h2>Adventures!</h2>
        </div>
        <div className='Home__signup'>
          <Dialog
            handleSubmit={this.testFunc}
            label='Your name'
            inputs={this.inputSettings}
            button_text='Sign up'
            header='Custom header'
          />
        </div>
      </div>
    );
  }
}
