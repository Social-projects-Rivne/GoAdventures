import React, { Component } from 'react';
import { Redirect, Route } from 'react-router-dom';
import { AuthContext } from '../../context/auth.context';

export const ContextProtectedRoute = ({ component, ...rest }: any) => (
    <AuthContext.Consumer>
      {({ authorized, authorize}) => (
        <Route
          render={
            () =>
              authorized
              ? React.createElement(component, {authorize, authorized})
              : <Redirect to='/home' />
          }
          {...rest}
        />
      )}
    </AuthContext.Consumer>
);
