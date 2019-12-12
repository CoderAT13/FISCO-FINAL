import { applyMiddleware, compose, createStore } from 'redux'
import { routerMiddleware } from 'connected-react-router'
import thunkMiddleware from 'redux-thunk';
import { createLogger } from 'redux-logger';
import rootReducer from '../_reducers';
import { history } from '../_helpers';

export default function configureStore(initialState = {}) {
    const loggerMiddleware = createLogger();
    const store = createStore(
        rootReducer, // root reducer with router state
        initialState,
        compose(
            applyMiddleware(
                routerMiddleware(history), // for dispatching history actions
                thunkMiddleware,
                loggerMiddleware
            ),
        ),
    )
    return store;
}

