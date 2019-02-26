import axios from 'axios';
import { async } from 'q';
import { serverUrl } from '../../url.config';

export const API_SIGN_UP = 'API_SIGN_UP';
export const API_SIGN_UP_SUCCESS = 'API_SIGN_IN_SUCCESS';
export const API_SIGN_UP_FAILURE = 'API_SIGN_IN_FAILURE';
export const API_SIGN_IN = 'API_SIGN_IN';
export const API_SIGN_IN_SUCCESS = 'API_SIGN_IN_SUCCESS';
export const API_SIGN_IN_FAILURE = 'API_SIGN_IN_FAILURE';
export const SIGN_OUT = 'SIGN_OUT';

export const apiSignUpSuccess = (status: string) => ({
    payload: status,
    type: API_SIGN_UP_SUCCESS
});

export const apiSignUp = (user: string) => ({
    payload: user,
  type: API_SIGN_UP,
});

export const apiSignUpFailure = (errorMessage: string) => ({
    payload: errorMessage,
  type: API_SIGN_UP_FAILURE,
});


export const apiSignIn = (user: any) => ({
    payload: user,
  type: API_SIGN_IN,
});




export const signUp = (data: any) => {
    return async (dispatch: any) => {
        return await axios.post(`${serverUrl}/auth/sign-up`, {...data}).then((res) => {
            dispatch(apiSignUpSuccess(res.data));
        }).catch((error) => {
            dispatch(apiSignUpFailure(error));
        });
    };

};
