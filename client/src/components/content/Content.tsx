import React from 'react';
import { Redirect } from 'react-router';
// import { SecureRoute } from 'react-route-guard';
import { Route, Switch } from 'react-router-dom';
import { About, Confirm, Events, Home, Profile, ValidateUser } from '../../pages';

export const Content = (props: any) => {
  return (
    <main>
      <div className='container-fluid'>
        <div className='row'>
          <Switch>
            <Route exact path='/home' component={Home} />
            {/* <SecureRoute path='/profile' routeGuard={AuthGuard}
             component={Profile} redirectToPathWhenFail='/' /> */}
            {/* <SecureRoute path='/events' routeGuard={AuthGuard}
             component={Events} redirectToPathWhenFail='/' /> */}
             {props.authorized ? (
               <Route exact path='/profile' component={Profile} />
             ) : (<Redirect to='/home' />)};
             {props.authorized ? (
               <Route exact path='/events' component={Events} />
             ): (<Redirect to='/home' />)}
            <Route exact path='/about' component={About} />
            <Route exact path='/confirm-yor-yo' component={ValidateUser}/>
            <Route exact path='/confirm-account' component={Confirm} />
          </Switch>
        </div>
      </div>
    </main>
  );
};
