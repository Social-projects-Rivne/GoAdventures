import { combineReducers } from 'redux';
import authReducer from '../../api/auth/reducers';


export default combineReducers({
    auth: authReducer
});
