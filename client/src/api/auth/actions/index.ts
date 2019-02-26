export const API_SIGN_UP = 'API_SIGN_UP';
export const API_SIGN_UP_SUCCESS = 'API_SIGN_IN_SUCCESS';
export const API_SIGN_UP_FAILURE = 'API_SIGN_IN_FAILURE';
export const API_SIGN_IN = 'API_SIGN_IN';
export const API_SIGN_IN_SUCCESS = 'API_SIGN_IN_SUCCESS';
export const API_SIGN_IN_FAILURE = 'API_SIGN_IN_FAILURE';
export const SIGN_OUT = 'SIGN_OUT';

export const apiSignUpSuccess = (status: string) => ({
    status,
    type: API_SIGN_UP_SUCCESS
});

export const apiSignUp = (payload: string) => ({
    payload,
  type: API_SIGN_UP,
});

export const apiSignUpFailure = (payload: string) => ({
    payload,
  type: API_SIGN_UP_FAILURE,
});