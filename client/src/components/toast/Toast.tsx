import React from 'react';

/*  WIll change! */

export const Toast = (props: any) => {
    return (
        <div className='toast' role='alert' aria-live='assertive' aria-atomic='true'>
            <div className='toast-header'>
                <img src='...' className='rounded mr-2' alt='...' />
                <strong className='mr-auto'>Username</strong>
                <small className='text-muted'>user status</small>
                <button type='button' className='ml-2 mb-1 close' data-dismiss='toast' aria-label='Close'>
                {/* Delete user from event ?? */}
                    <span aria-hidden='true'>&times;</span>
                </button>
            </div>
            <div className='toast-body'>
                User info
  </div>
        </div>
    );
};


