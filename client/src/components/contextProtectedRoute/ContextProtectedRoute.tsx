import React from 'react';
import { RouterProps } from 'react-router';
import { Redirect, Route } from 'react-router-dom';
import { AuthContext } from '../../context/auth.context';

export const ContextProtectedRoute = ({ component, ...rest }: any) => (
  <AuthContext.Consumer>
    {({ authorized, authorize }) => (
      <Route
        render={(routerProps: RouterProps) =>
          authorized ? (
            React.createElement(component, {
              authorize,
              authorized,
              routerProps
            })
          ) : (
            <Redirect to='/' />
          )
        }
        {...rest}
      />
    )}
  </AuthContext.Consumer>
);
