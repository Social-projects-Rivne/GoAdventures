import React from 'react';
import { Route, Switch } from 'react-router-dom';
import { ContextProtectedRoute, ContextRoute } from '../../components';
import { About, Confirm, Events, EventsDetail, Home, Profile, ValidateUser } from '../../pages';

export const Content = () => {
  return (
    <main>
      <div className='container-fluid'>
        <div className='row'>
          <Switch>
            <Route exact strict path='/' component={Home} />
            <Route strict path='/about' component={About} />
            <Route strict exact path='/confirm-yor-yo' component={ValidateUser} />
            <ContextRoute strict path='/confirm-account' component={Confirm} />
            <ContextProtectedRoute path='/profile' component={Profile} />
            <ContextProtectedRoute exact path='/events' component={Events} />
            <ContextRoute exact path='/events/deatail/:name' component={EventsDetail} />
          </Switch>
        </div>
      </div>
    </main>
  );
};
