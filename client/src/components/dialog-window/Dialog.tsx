import React, { ChangeEvent, Component, SyntheticEvent } from 'react';
import { signUp } from '../../api/request';
import './Dialog.scss';
import { DialogSettings } from './interfaces/dialog.interface';

/* DON'T RE-DEFINE COMPONENT! USE IT! */

export class Dialog extends Component<DialogSettings, any> {
  constructor(props: DialogSettings) {
    super(props);
    this.state = {};

    //  :*D
    this.handleChange = this.handleChange.bind(this);
    this.submitForm = this.submitForm.bind(this);
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
                    className='form-control'
                    placeholder={input.placeholder}
                    onChange={this.handleChange}
                    key={index}
                  />
                </label>
              );
            })}
          </form>
        </div>
        <div className='card-footer text-muted d-flex justify-content-center'>
          <button className='btn btn-success' onClick={this.submitForm}>
            {this.props.button_text}
          </button>
        </div>
      </div>
    );
  }

  private submitForm() {
    if(Object.keys(this.state).length !== 0 && this.compareFields()) {
      const data = {...this.state};
      delete data.confirmPassword;
      console.log(data);
      this.props.context.authorize(this.props.handleSubmit);
      signUp(data);
    } else {
      return (
        <div>Not match</div>
      );
    }
  }

  private handleChange(event: any): void {
    const objKey: any = event.target.getAttribute('name');
    const stateObj: any = {};
    stateObj[objKey.toString()] = event.target.value;
    this.setState({ ...stateObj });
  }

  private compareFields(): boolean {
    const { password, confirmPassword } = this.state;
    return password === confirmPassword &&
    password !== '' &&
    confirmPassword !== '';
  }
}
