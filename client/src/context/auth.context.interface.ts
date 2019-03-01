export interface Auth {
    authorized: boolean;
    authorize: (reqType: () => any) => void;
    authType: 'signUp' | 'signIn';
    toggleAuthType: () => void;
  }
