import React from 'react';
import './ValidateUser.scss';

export const ValidateUser = () => {
  return (
    <div className='container ValidateUser_container'>
      <div className='jumbotron'>
        <div className='text-center'>
          <h1 className='text-success'>Success</h1>
          <hr />
          <h2>Confirmation Email sent</h2>
        </div>
      </div>
    </div>
  );
};
