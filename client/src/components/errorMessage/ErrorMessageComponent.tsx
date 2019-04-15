import React from 'react';
import { ErrorMessage } from '../../interfaces/ErrorMessage';

export const ErrorMessageComponent = (props: ErrorMessage) => {
  return (
    <div className='alert alert-danger' role='alert'>
      <b>Fog!</b> {props.errorMessage}
    </div>
  );
};
