import React from 'react';
import { Route, Switch } from 'react-router-dom';
import { ContextProtectedRoute, ContextRoute } from '../../components';
import {
  About,
  Confirm,
  CreateEvent,
  Event,
  Events,
  ForgotPassword,
  Home,
  Profile,
  SendRecoveryEmail,
  ValidateUser
} from '../../pages';

export const Content = () => {
  return (
    <main>
      <div className='container-fluid'>
        <div className='row'>
          <Switch>
            <Route exact={true} strict={true} path='/' component={Home} />
            <Route strict={true} path='/about' component={About} />
            <Route
              strict={true}
              exact={true}
              path='/confirm-yor-yo'
              component={ValidateUser}
            />
            <Route exact={true} path='/recovery-password' component={ForgotPassword} />
            <ContextRoute strict={true} path='/confirm-account' component={Confirm} />
            <ContextProtectedRoute path='/profile' component={Profile} />
            <ContextRoute
              exact={true}
              path='/send-recovery-email'
              component={SendRecoveryEmail}
            />
            <ContextProtectedRoute exact={true} path='/events' component={Events} />
            <ContextProtectedRoute
              exact={true}
              path='/events/detail/:name'
              component={Event}
            />
            <ContextProtectedRoute
              exact={true}
              path='/create-event'
              component={CreateEvent}
            />
          </Switch>
        </div>
      </div>
    </main>
  );
};
