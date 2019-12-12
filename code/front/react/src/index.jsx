import React from 'react';
import { render } from 'react-dom';
import { Provider } from 'react-redux';
import { ConnectedRouter } from 'connected-react-router';
import zh_CN from './_locale/zh_CN';
import en_US from './_locale/en_US';
import zh from 'react-intl/locale-data/zh';
import { addLocaleData, IntlProvider } from 'react-intl';
import { history } from './_helpers';
import { configureFakeBackend, objectToArray } from './_helpers';
import { languageService } from './_services';
import configureStore from './_config/configureStore';
import 'typeface-roboto';
import { RootRouter } from './_router/RootRouter';

addLocaleData([...zh, ...objectToArray(en_US), ...objectToArray(zh_CN)]);
// // setup fake backend

configureFakeBackend();


// // initialize
const initialState = {};
const store = configureStore(initialState);

render(
  <Provider store={store}>
    <IntlProvider
      locale={navigator.language}
      messages={languageService.getLanguage()}
    >
      <ConnectedRouter history={history}>
        <RootRouter></RootRouter>
      </ConnectedRouter>
    </IntlProvider>
  </Provider>,
  document.getElementById('root')
);