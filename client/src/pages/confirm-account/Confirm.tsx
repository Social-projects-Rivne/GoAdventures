import React, { Component } from 'react';
import { Redirect } from 'react-router';
import { confirmAccount } from '../../api/auth.service';


export class Confirm extends Component {

    private validated: Promise<boolean> | undefined;

    constructor(props: any) {
        super(props);
        this.validated = undefined;
    }


    public componentWillMount() {
        const currentUrl: string = window.location.toString();
        console.debug(currentUrl);
        this.validated = confirmAccount(currentUrl.slice(currentUrl.indexOf('?')));
    }


  public render() {
      if(this.validated) {
        return (
            <Redirect to='/profile' />
          );
      } else {
        return (
            <Redirect to='/' />
        );
      }
  }
}
