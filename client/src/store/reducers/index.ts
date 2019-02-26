import { combineReducers } from 'redux';
import authReducer from '../../api/auth/reducers/auth.reducer';


export default combineReducers({
    auth: authReducer
});
