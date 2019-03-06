import React from 'react';
import { Redirect } from 'react-router';
// import { SecureRoute } from 'react-route-guard';
import { Route, Switch } from 'react-router-dom';
import { AuthContext } from '../../context/auth.context';
import { About, Confirm, Events, Home, Profile, ValidateUser } from '../../pages';

export const Content = (props: any) => {
  return (
    <main>
      <div className='container-fluid'>
        <div className='row'>
          <Switch>
            <Route exact path='/about' component={About} />
            <Route exact path='/confirm-yor-yo' component={ValidateUser}/>
            <Route exact path='/confirm-account' component={Confirm} />
            <Route exact path='/home' component={Home} />
            <Route exact path='/about' component={About} />
            <Route exact path='/confirm-yor-yo' component={ValidateUser}/>
            <Route exact path='/confirm-account'>
            <AuthContext.Consumer>
                {({authorize, authorized}) =>
                  (<Confirm context={{authorize, authorized}} />)
                }
              </AuthContext.Consumer>
            </Route>
            {/* <SecureRoute path='/profile' routeGuard={AuthGuard}
             component={Profile} redirectToPathWhenFail='/' /> */}
            {/* <SecureRoute path='/events' routeGuard={AuthGuard}
             component={Events} redirectToPathWhenFail='/' /> */}
             {props.authorized ? (
               <Route exact path='/profile' component={Profile} />
             ) : (<Redirect to='/home' />)};
             {props.authorized ? (
               <Route exact path='/events' component={Events} />
             ) : (<Redirect to='/home' />)}
          </Switch>
        </div>
      </div>
    </main>
  );
};
