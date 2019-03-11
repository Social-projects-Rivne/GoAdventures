import React from 'react';
import { Route } from 'react-router-dom';
import { AuthContext } from '../../context/auth.context';
// will change
export const ContextRoute = ({ component, ...rest }: any) => (
  <AuthContext.Consumer>
    {(context) => (
      <Route
        {...rest}
        render={
          (props) =>
            React.createElement(component, { context, props })
        }
      />
    )}
  </AuthContext.Consumer>
);
