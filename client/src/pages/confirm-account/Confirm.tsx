import React, { Component } from 'react';
import { Redirect } from 'react-router';
import { confirmAccount } from '../../api/auth.service';
import { RequestStatus } from '../../api/requestStatus.inreface';

export class Confirm extends Component<any, any> {
  private url: string;

  constructor(props: any) {
    super(props);
    this.url = '';
  }

  public componentWillMount() {
    this.url = window.location.toString();
  }

  public async componentDidMount() {
    this.props.context.authorize(
      confirmAccount, { param: this.url.slice(this.url.indexOf('?')) }
    );
  }

  public render() {
    return (
      <div>
        {this.props.context.authorized ? (
          <Redirect to='/profile' />
        ) : (
          <div>
            <h5>Loading...</h5>
          </div>
        )}
      </div>
    );
  }
}
