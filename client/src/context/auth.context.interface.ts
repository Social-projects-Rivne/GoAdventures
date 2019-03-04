export interface Auth {
    authorized: boolean;
    authorize: (reqType: (data: object) => any, data: object) => void;
    authType: 'signUp' | 'signIn';
    toggleAuthType: () => void;
  }
