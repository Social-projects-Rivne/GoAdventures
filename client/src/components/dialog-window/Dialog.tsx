import React, { Component } from 'react';
import { CSSProperties } from 'react';
import './Dialog.scss';
import { DialogSettings } from './interfaces/dialog.interface';

export default class Dialog extends Component<DialogSettings> {
    // private styles: CSSProperties;
    constructor(props: DialogSettings) {
    super(props);
    // this.styles = {...this.props};
    this.state = {};

    // So much lol :D
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  public render() {
      return (
      <div className='Dialog__window card border-success mb-3'>
        <div className='card-header'>{this.props.header}</div>
        <div className='card-body'>
          <form>
            <label>
                { this.props.label }
                {this.props.inputs.map((input, index) => {
                    return <input type={input.type} className='form-control'
                         placeholder={input.placeholder} key={index}/>;
                })}
            </label>
            <button type='button' className='btn btn-success'>Sign Up</button>
          </form>
        </div>
      </div>
    );
  }

  private handleSubmit(event: Event): void {
    event.preventDefault();
  }
}
