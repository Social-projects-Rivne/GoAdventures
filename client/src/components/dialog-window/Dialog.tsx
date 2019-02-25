import React, { Component } from 'react';
import './Dialog.scss';
import { DialogSettings } from './interfaces/dialog.interface';

export class Dialog extends Component<DialogSettings> {
    // private styles: CSSProperties;
    constructor(props: DialogSettings) {
    super(props);
    // this.styles = {...this.props};
    this.state = {};

    //  :*D
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  public render() {
      return (
      <div className='Dialog__window card border-success mb-3'>
        <div className='card-header'><h3>{this.props.header}</h3></div>
        <div className='card-body'>
          <form>
                {this.props.inputs.map((input, index) => {
                    return <label>
                        { this.props.label }
                        <input type={input.type} className='form-control'
                        placeholder={input.placeholder} key={index}/></label>;
                })}

            <button type='submit' onClick={ this.props.handleSubmit }
             className='btn btn-success'>{this.props.button_text}</button>
          </form>
        </div>
      </div>
    );
  }

  private handleSubmit(event: Event): void {
    event.preventDefault();
  }
}
