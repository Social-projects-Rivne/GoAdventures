import React from 'react';
import { Route, Switch } from 'react-router-dom';
import { ContextProtectedRoute, ContextRoute } from '../../components';
import {
  About,
  Confirm,
  CreateEvent,
  Events,
  EventsDetail,
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
            <Route exact strict path='/' component={Home} />
            <Route strict path='/about' component={About} />
            <Route strict exact path='/confirm-yor-yo' component={ValidateUser} />
            <Route exact path='/recovery-password' component={ForgotPassword} />
            <ContextRoute strict path='/confirm-account' component={Confirm} />
            <ContextProtectedRoute path='/profile' component={Profile} />
            <ContextRoute exact path='/send-recovery-email' component={SendRecoveryEmail} />
            <ContextProtectedRoute exact path='/events' component={Events} />
            <ContextRoute exact path='/events/deatail/:name' component={EventsDetail} />
            <ContextProtectedRoute exact path='/create-event' component={CreateEvent} />
          </Switch>
        </div>
      </div>
    </main>
  );
};
