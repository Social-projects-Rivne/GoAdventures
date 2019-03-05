import React from 'react';
// import { SecureRoute } from 'react-route-guard';
import { Route, Switch } from 'react-router-dom';
import { About, Confirm, Events, Home, Profile, ValidateUser } from '../../pages';

export const Content = () => {
  return (
    <main>
      <div className='container-fluid'>
        <div className='row'>
          <Switch>
            <Route exact path='/' component={Home} />
            {/* <SecureRoute path='/profile' routeGuard={AuthGuard}
             component={Profile} redirectToPathWhenFail='/' /> */}
            {/* <SecureRoute path='/events' routeGuard={AuthGuard}
             component={Events} redirectToPathWhenFail='/' /> */}
            <Route exact path='/profile' component={Profile} />
            <Route exact path='/events' component={Events} />
            <Route exact path='/about' component={About} />
            <Route exact path='/confirm-yor-yo' component={ValidateUser}/>
            <Route exact path='/confirm-account' component={Confirm} />
          </Switch>
        </div>
      </div>
    </main>
  );
};
