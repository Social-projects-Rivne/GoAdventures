import React from 'react';
import { Auth } from './auth.context.interface';
export const user = {
    authType: 'signUp' as Auth['authType'],
  authorized: false,
};

export const AuthContext = React.createContext({
  authorize: (reqType: () => any): void => {},
  toggleAuthType: () => {},
  ...user
});
