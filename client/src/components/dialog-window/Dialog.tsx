import { Field, Form, Formik, FormikProps } from 'formik';
import React, { Component } from 'react';
import { MdDone } from 'react-icons/md';
import './Dialog.scss';
import { DialogSettings } from './interfaces/dialog.interface';

export class Dialog extends Component<DialogSettings, any> {
  constructor(props: DialogSettings) {
    super(props);
    this.state = { category: '', isLoading: false };
    //  :*D
    this.getInitialValues = this.getInitialValues.bind(this);
  }

  public getInitialValues = (): object => {
    const initialValues: { [key: string]: string } = {};
    this.props.inputs.forEach((input) => {
      if (!initialValues[input.field_name.toString()]) {
        initialValues[input.field_name.toString()] = '';
      }
    });
    return initialValues;
  }

  public render(): JSX.Element {
    return (
      <div
        className='Dialog__window card border-success mb-3 mt-3'
        style={this.props.inline_styles ? this.props.inline_styles : {}}
      >
        <div className='card-header'>
          <h3>{this.props.header}</h3>
        </div>
        <div className='card-body d-flex justify-content-center align-items-center flex-column'>
          {this.state.isLoading ? (
            <div className='d-flex justify-content-center'>
              <div className='spinner-grow' role='status'>
                <span className='sr-only'>Loading...</span>
              </div>
            </div>
          ) : (
            <Formik
              enableReinitialize={true}
              initialValues={this.getInitialValues()}
              validateOnBlur={true}
              validationSchema={this.props.validationSchema}
              onSubmit={async (values: any, actions) => {
                this.setState(() => {
                  return {
                    isLoading: true
                  };
                });
                const valuesMutadet = { ...values };
                if (valuesMutadet.hasOwnProperty('confirmPassword')) {
                  delete valuesMutadet!.confirmPassword;
                }
                if (this.props.context && this.props.context.authorize) {
                  await this.props.context.authorize(this.props.handleSubmit, {
                    ...valuesMutadet
                  });
                  this.setState(() => {
                    return {
                      isLoading: false
                    };
                  });
                } else {
                  await this.props.handleSubmit(
                    { ...valuesMutadet },
                    this.state.category
                  );
                  this.setState(() => {
                    return {
                      isLoading: false
                    };
                  });
                }
                if (this.props.redirect && !this.state.isLoading) {
                  this.props.redirect.routerProps.history.push(
                    `${this.props.redirect.redirectURL}`
                  );
                }
                actions.resetForm();
                actions.setSubmitting(false);
              }}
            >
              {({
                errors,
                touched,
                handleBlur,
                handleChange,
                values
              }: FormikProps<any>) => {
                return (
                  <Form id='dialog'>
                    {this.props.inputs.map((input, index) => {
                      return (
                        <label key={index}>
                          {input.label_value}
                          <Field
                            value={values[input.field_name]}
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
                          ) : (
                            <div className='valid-feedback'>
                              <MdDone />
                            </div>
                          )}
                        </label>
                      );
                    })}
                  </Form>
                );
              }}
            </Formik>
          )}
          {this.props.childComponents ? this.props.childComponents : null}
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
