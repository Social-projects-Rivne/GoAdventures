import React from 'react';
import { Route, Switch } from 'react-router-dom';
import { About, Events, Home, Profile } from '../../pages';

export const Content = () => {
  return (
    <main>
      <div className='container-fluid'>
        <div className='row'>
          <Switch>
            <Route exact path='/' component={Home} />
            <Route exact path='/profile' component={Profile} />
            <Route exact path='/events' component={Events} />
            <Route exact path='/about' component={About} />
          </Switch>
        </div>
      </div>
    </main>
  );
};
