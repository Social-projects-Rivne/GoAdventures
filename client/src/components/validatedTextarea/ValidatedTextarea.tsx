import React, { ChangeEvent } from 'react';
import { Formik, Form, Field, FieldProps, FormikValues } from 'formik';
import { textAreaSchema } from '../../validationSchemas/textareaValidation';
import { MdDone } from 'react-icons/md';

interface ValidatedTextareaProps {
  labelText: string;
  initialValue?: { textarea: string | undefined };
  handleSubmit: any;
  bindSubmit?: (formikSubmit: any) => void;
}

export function ValidatedTextarea(props: ValidatedTextareaProps) {
  const { bindSubmit } = props;
  return (
    <label className='w-100'>
      {props.labelText}
      <Formik
        validationSchema={textAreaSchema}
        initialValues={
          props.initialValue ? { ...props.initialValue } : { textarea: '' }
        }
        enableReinitialize={true}
        validateOnBlur={true}
        validateOnChange={true}
        onSubmit={(value: any, actions) => {
          console.debug('Formic on submit ', value);
          props.handleSubmit(value.textarea);
          actions.setSubmitting(false);
        }}
        render={(formikProps: any) => {
          if (!!bindSubmit) {
            bindSubmit(formikProps.submitForm);
          }
          return (
            <Form className='w-100'>
              <Field
                name='textarea'
                render={({ field, form }: FieldProps<FormikValues>) => {
                  return (
                    <div>
                      <textarea
                        className='form-control rounded w-100'
                        aria-label='textarea'
                        {...field}
                      />
                      {form.touched.textarea && form.errors.textarea ? (
                        <div className='invalid-feedback'>
                          {form.errors.textarea}
                        </div>
                      ) : (
                        <div className='valid-feedback'>
                          <MdDone /> Edit your description
                        </div>
                      )}
                    </div>
                  );
                }}
              />
            </Form>
          );
        }}
      />
    </label>
  );
}
