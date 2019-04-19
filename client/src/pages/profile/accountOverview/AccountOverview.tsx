import React from 'react';
import { UserDto } from '../../../interfaces/User.dto';
import './AccountOverwiew.scss';

const AccountOverwiew = (props: UserDto) => {
  return (
    <div className='container page-container'>
    <div className='card border-primary mb-3 accOverCont'>
      <div className='card-header'>
        <h2 className='header'>Account overview</h2>
      </div>
      <div className='card-body'>
        <h4 className='card-title'>Profile</h4>

        <div className='fullname'>
          <label className='label-info'>Fullname</label>
          <p className='card-text info'>{props.fullname}</p>
        </div>

        <div className='username'>
          <label className='label-info'>Username </label>
          <p className='card-text info'>{props.username}</p>
        </div>

        <div className='email'>
          <label className='label-info'>Email </label>
          <p className='card-text info'>{props.email}</p>
        </div>
        {props.location != undefined ? (
          <div className='location'>
            <label className='label-info'>Location </label>
            <p className='card-text info'>{props.location}</p>
          </div>
        ) : null}
        {props.phone != '0' ? (
          <div className='phone'>
            <label className='label-info'>Phone </label>
            <p className='card-text info'>{props.phone}</p>
          </div>
        ) : null}
      </div>
      </div>
      </div>
  );
};

export default AccountOverwiew;
