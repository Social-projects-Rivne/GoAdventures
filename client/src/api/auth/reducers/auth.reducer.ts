import {
  API_SIGN_IN,
  API_SIGN_IN_FAILURE,
  API_SIGN_IN_SUCCESS,
  API_SIGN_UP,
  API_SIGN_UP_FAILURE,
  API_SIGN_UP_SUCCESS,
  SIGN_OUT
} from '../actions';
import { AuthAction } from '../actions/action.interface';

const initialState = {};

const authReducer = (state = initialState, action: AuthAction) => {
  switch (action.type) {
    case API_SIGN_UP:
      return { ...state, ...action.payload };
    case API_SIGN_UP_SUCCESS:
      return { ...state, ...action.payload };
    case API_SIGN_UP_FAILURE:
      return { ...state, ...action.payload };

    default:
      return state;
  }
};

export default authReducer;
