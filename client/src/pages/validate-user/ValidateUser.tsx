import React from 'react';
import './ValidateUser.scss';

export const ValidateUser = (props: any) => {
  return (
    
    <div className='ValidateUser container'> 
      <h2>Confirmation Email sent</h2><hr />
      <h4>Please, confirm your account validation on email.</h4>
      <a href={props.email}>Link</a>
    </div>
  );
};


