import { combineReducers } from 'redux';

import { authentication } from './authentication.reducer';
import { registration } from './registration.reducer';
import { user } from './user.reducer';
import { connectRouter } from 'connected-react-router';
import { history } from '../_helpers';

const rootReducer = combineReducers({
  router: connectRouter(history),
  authentication,
  registration,
  user,
});

export default rootReducer;