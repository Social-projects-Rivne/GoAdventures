import React from 'react';
import { Link, Redirect } from 'react-router-dom';
import { signOut } from '../../api/auth.service';
import { AuthContext } from '../../context/auth.context';
import { SearchEvent } from '../events/searchEvent/SearchEvent';

export const Navbar = (props: any) => {
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
            {/* Activate on state authorized */}
            {props.authorized ? (
              <li className='nav-item'>
                <Link className='nav-link' to='/profile'>
                  Profile
                </Link>
              </li>
            ) : null}
            {props.authorized ? (
              <li className='nav-item'>
                <Link className='nav-link' to='/events'>
                  Events
                </Link>
              </li>
            ) : null}
            <li className='nav-item'>
              <Link className='nav-link' to='/about'>
                About
              </Link>
            </li>
          </ul>
          <AuthContext.Consumer>
            {({ authorized, authType, toggleAuthType, authorize }) =>
              authorized ? (
                <div>
                  <ul className='navbar-nav mr-auto'>
                    <li className='nav-item'>
                      {/* <SearchEvent></SearchEvent> */}
                    </li>
                    <li className='nav-item'>
                      <button type='button'
                        className='btn btn-danger'
                        onClick={(): void => {
                          authorize((): Promise<string> => signOut());
                        }}>
                        Sign Out
              </button>
                    </li>
                  </ul>
                </div>
              ) :
                authType === 'signUp' && !authorized ? (
                  <button
                    onClick={toggleAuthType}
                    type='button'
                    className='btn btn-success'>
                    Sign In
                </button>
                ) : (
                    <button
                      onClick={toggleAuthType}
                      type='button'
                      className='btn btn-warning'>
                      Sign Up
                </button>
                  )
            }
          </AuthContext.Consumer>
        </div>
      </div>
    </nav>
  );
};
