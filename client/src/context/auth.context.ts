import React from 'react';
import { Auth } from './auth.context.interface';
export const user = {
  authType: 'signUp' as Auth['authType'],
  authorized: false as Auth['authorized'],
  messages: '' as Auth['messages'],
};

export const AuthContext = React.createContext({
  authorize: (reqType: () => any, data?: object): void => { },
  toggleAuthType: () => { },
  ...user
});
