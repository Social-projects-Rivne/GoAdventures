export interface Auth {
  authorized: true | false;
  authorize: (reqType: (data?: object) => any, data?: object) => void;
  authType: 'signUp' | 'signIn';
  toggleAuthType: () => void;
  messages: string;
}
