import React, { ChangeEvent, Component } from 'react';
import './Dialog.scss';
import { DialogSettings } from './interfaces/dialog.interface';

export class Dialog extends Component<DialogSettings> {

  constructor(props: DialogSettings) {
    super(props);
    this.state = {};

    //  :*D
    this.handleChange = this.handleChange.bind(this);
  }


  public render() {
    return (
      <div
        className='Dialog__window card border-success mb-3'
        style={this.props.inline_styles ? this.props.inline_styles : {}}
      >
        <div className='card-header'>
          <h3>{this.props.header}</h3>
        </div>
        <div className='card-body'>
          <form>
            {this.props.inputs.map((input, index) => {
              return (
                <label key={index}>
                  {input.label_value}
                  <input
                    name={input.field_name}
                    type={input.type}
                    className='form-control '
                    placeholder={input.placeholder}
                    onChange={this.handleChange}
                    key={index}
                  />
                  <div className='valid-feedback'>Success! You've done it.</div>
                </label>
              );
            })}
          </form>
        </div>
        <div className='card-footer text-muted d-flex justify-content-center'>
              <button
                type='submit'
                onClick={this.props.handleSubmit}
                className='btn btn-success'>
                {this.props.button_text}
              </button>
            </div>
      </div>
    );
  }

  private handleChange(event: any): void {
    const objKey: any = event.target.getAttribute('name');
    const stateObj: any = {};
    stateObj[objKey.toString()] = event.target.value;
    this.setState({...stateObj});
    console.log(this.state);
  }
}
