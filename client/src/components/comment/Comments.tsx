import React from 'react';
import './Coments.scss';

interface Comment {
  avatar: string;
  participant: string;
  text: string;
  hashtags: string[];
}

export const Comments = (props: Comment): JSX.Element => (
  <div className='Comment'>
    <div className='row'>
      <div className='col'>
        <img className='rounded-avatar-sm' src={props.avatar} />
      </div>
      <div className='col'>
        <div className='Comment__wrapper'>
          <div className='toast-header'>
            <strong>{props.participant}</strong>
          </div>
          <div className='toast-body'>
            {props.text}
            <div className='Comment__hashtags'>
              {props.hashtags.map((hastag, index) => (
                <span key={index}>
                  <a href='#'>#{hastag} </a>
                </span>
              ))}
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
);
