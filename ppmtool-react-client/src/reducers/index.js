import { combineReducers } from 'redux';
import errorReducer from './errorReducer';
import projectReducer from './projectReducer';
import backlogReducer from './backlogReducer';
import securityRecucer from './securityReducer';
import userReducers from './userReducers';

export default combineReducers({
    errors: errorReducer,
    project: projectReducer,
    backlog: backlogReducer,
    security: securityRecucer,
    user: userReducers
})