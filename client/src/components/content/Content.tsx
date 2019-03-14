import React from 'react';
import { Route, Switch } from 'react-router-dom';
import { About, Confirm, Events, Home, Profile, ValidateUser, ForgotPassword, SendRecoveryEmail } from '../../pages';
import { ContextProtectedRoute, ContextRoute } from '../../components';

export const Content = (props: any) => {
  return (
    <main>
      <div className='container-fluid'>
        <div className='row'>
          <Switch>
            <Route exact path='/' component={Home} />
            <Route exact path='/about' component={About} />
            <Route exact path='/confirm-yor-yo' component={ValidateUser} />
            <Route exact path='/recovery-password' component={ForgotPassword} />
            {/*<Route exact path='/send-recovery-email' component={SendRecoveryEmail} />*/}
            <ContextRoute exact path='/send-recovery-email' component={SendRecoveryEmail} />
            <ContextRoute exact path='/confirm-account' component={Confirm} />
            <ContextProtectedRoute exact path='/profile' component={Profile} />
            <ContextProtectedRoute exact path='/events' component={Events} />
          </Switch>
        </div>
      </div>
    </main>
  );
};
