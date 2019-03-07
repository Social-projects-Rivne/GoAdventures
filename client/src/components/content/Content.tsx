import React from 'react';
import { Redirect } from 'react-router';
// import { SecureRoute } from 'react-route-guard';
import { Route, Switch } from 'react-router-dom';
import { AuthContext } from '../../context/auth.context';
import { About, Confirm, Events, Home, Profile, ValidateUser } from '../../pages';
import { ContextProtectedRoute } from '../contextRoute/ContextProtectedRoute';

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
            <ContextProtectedRoute exact path='/profile' component={Profile} />
            <ContextProtectedRoute exact path='/events' component={Events} />
          </Switch>
        </div>
      </div>
    </main>
  );
};
