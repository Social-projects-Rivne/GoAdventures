import React from 'react';
import { Route } from 'react-router';
import { PrivateRouteProps } from './private-route.interface';

export const PrivateRoute = ({component: Component, ...rest}: PrivateRouteProps) => {
  return (
    <Route {...rest} render={(props) => {}}
    />
  );
};
