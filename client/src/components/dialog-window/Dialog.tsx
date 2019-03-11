import { Field, FieldProps, Form, Formik, FormikActions, FormikProps } from 'formik';
import React, { Component, SyntheticEvent } from 'react';
import fb from '../../assets/icons/fb.svg';
import google from '../../assets/icons/google.svg';
import './Dialog.scss';
import { DialogSettings } from './interfaces/dialog.interface';

/* DON'T RE-DEFINE COMPONENT! USE IT! */

export class Dialog extends Component<DialogSettings, any> {
  constructor(props: DialogSettings) {
    super(props);

    this.state = {
    };

    //  :*D
    this.handleChange = this.handleChange.bind(this);
    this.submitForm = this.submitForm.bind(this);
    this.getInitialValues = this.getInitialValues.bind(this);
    this.props.redirect.bind(this);
  }
  public getInitialValues = (): object => {
    const initialValues: {[key: string]: string} = {};
    this.props.inputs.forEach((input) => {
      if (!initialValues[input.field_name.toString()]) {
        initialValues[input.field_name.toString()] = '';
        console.debug(initialValues);
      }
    });
    return initialValues;
  }

  public render(): JSX.Element {
    if (this.props.context.authorized) {
      return this.props.redirect(this.props.context.authType);
    } else {
      return (
        <div
          className='Dialog__window card border-success mb-3 mt-3'
          style={this.props.inline_styles ? this.props.inline_styles : {}}
        >
          <div className='card-header'>
            <h3>{this.props.header}</h3>
          </div>
          <div className='card-body'>
            <Formik
              initialValues={this.getInitialValues()}
              validateOnBlur={true}
              validationSchema={this.props.validationSchema}
              onSubmit={(values, actions) => {
                console.debug(values);
                // this.setState({ ...values });
                actions.setSubmitting(false);
              }}
            >
              {({ errors, touched, handleBlur, handleChange }: any) => {
                return (
                  <Form id='dialog'>
                    {this.props.inputs.map((input, index) => {
                  return (
                      <label key={index}>
                        {input.label_value}
                        <Field
                          type={input.type}
                          placeholder={input.placeholder}
                          className='form-control'
                          name={input.field_name}
                          key={index}
                          onBlur={handleBlur}
                          onChange={handleChange}
                        />
                        {errors[`${input.field_name}`] &&
                        touched[input.field_name] ? (
                          <div className='invalid-feedback'>
                            {errors[input.field_name]}
                          </div>
                        ) : null}
                      </label>
                    );}
                    )}
                  </Form>
                );
              }}
            </Formik>
            {this.props.context.authType === 'signIn' ? (
              <div className='text-center'>
                <h3>Sign in with</h3>
                <div className='d-flex flex-row justify-content-around align-self-center'>
                  <a href='#'>
                    <img src={fb} />
                    <p>Facebook</p>
                  </a>
                  <a href='#'>
                    <img src={google} />
                    <p>Google</p>
                  </a>
                </div>
              </div>
            ) : null}
          </div>
          <div className='card-footer text-muted d-flex justify-content-center'>
            <button type='submit' form='dialog' className='btn btn-success'>
              {this.props.button_text}
            </button>
          </div>
        </div>
      );
    }
  }

  private submitForm(event: SyntheticEvent<EventTarget>) {
    event.preventDefault();
    if (Object.keys(this.state).length !== 0 && this.compareFields()) {
    }
    {
      const data = { ...this.state };
      delete data.confirmPassword;
      this.props.context.authorize(this.props.handleSubmit, data);
    }
  }

  private handleChange(event: any): void {
    const objKey: any = event.target.getAttribute('name');
    const stateObj: any = {};
    stateObj[objKey.toString()] = event.target.value;
    this.setState({ ...stateObj });
  }

  // Refactor
  private compareFields(): boolean {
    const { password } = this.state;
    if (this.state.hasOwnProperty('confirmPassword')) {
      const { confirmPassword } = this.state;
      return (
        password === confirmPassword &&
        password !== '' &&
        confirmPassword !== ''
      );
    } else {
      return password !== '';
    }
  }
}
