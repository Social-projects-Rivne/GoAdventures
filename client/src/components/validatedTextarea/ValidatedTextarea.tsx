import React from 'react';
import {
  Formik,
  Form,
  Field,
  FieldProps,
  FormikProps,
  FormikValues
} from 'formik';
import { textareSchema } from '../../validationSchemas/textareaValidation';
import { MdDone } from 'react-icons/md';

interface ValidatedTextareaProps {
  initialValue?: { textarea: string | undefined };
  handleSubmit: any;
}

export function ValidatedTextarea(props: ValidatedTextareaProps) {
  return (
    <Formik
      validationSchema={textareSchema}
      initialValues={props.initialValue ? props.initialValue : { value: '' }}
      enableReinitialize={true}
      validateOnBlur={true}
      validateOnChange={true}
      onSubmit={(value: any, actions) => {
        props.handleSubmit(value);
        actions.setSubmitting(false);
      }}
      render={(props: FormikProps<FormikValues>) => (
        <Form className='w-100'>
          <Field
            name='comment'
            render={({ field, form }: FieldProps<FormikValues>) => {
              return (
                <div>
                  <textarea
                    className='form-control rounded w-100'
                    aria-label='textarea'
                    {...field}
                    name='textarea'
                  />
                  {form.touched.comment &&
                  form.errors.comment &&
                  form.errors.comment ? (
                    <div className='invalid-feedback'>
                      {form.errors.comment}
                    </div>
                  ) : (
                    <div className='valid-feedback'>
                      <MdDone /> Press enter to add comment
                    </div>
                  )}
                </div>
              );
            }}
          />
        </Form>
      )}
    />
  );
}
