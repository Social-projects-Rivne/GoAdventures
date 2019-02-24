import React from 'react';
import { Link } from 'react-router-dom';

export const Navbar = () => {
  return (
    <nav className='navbar navbar-expand-lg navbar-dark bg-primary'>
      <div className='container'>
        <Link className='navbar-brand' to='/'>
          GoAdventures
        </Link>
        <button
          className='navbar-toggler'
          type='button'
          data-toggle='collapse'
          data-target='#navbarColor01'
          aria-controls='navbarColor01'
          aria-expanded='false'
          aria-label='Toggle navigation'
        >
          <span className='navbar-toggler-icon' />
        </button>

        <div className='collapse navbar-collapse' id='navbarColor01'>
          <ul className='navbar-nav mr-auto'>
            {/* <li className="nav-item active"> */}
            {/* Set className active on state  */}
            <li className='nav-item'>
              <Link className='nav-link' to='/'>
                Home {/* <span className="sr-only">(current)</span> */}
              </Link>
            </li>
            <li className='nav-item'>
              {/* Activate on state authorized */}
              <Link className='nav-link' to='/profile'>
                Profile
              </Link>
            </li>
            <li className='nav-item'>
              <Link className='nav-link' to='/events'>
                Events
              </Link>
            </li>
            <li className='nav-item'>
              <Link className='nav-link' to='/about'>
                About
              </Link>
            </li>
          </ul>
          <button type='button' className='btn btn-success'>Sign In</button>
          <button type='button' className='btn btn-warning'>Sign Up</button>
        </div>
      </div>
    </nav>
  );
};
