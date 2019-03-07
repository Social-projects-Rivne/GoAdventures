import React, { Component } from 'react';
import { Redirect } from 'react-router';
import { confirmAccount } from '../../api/auth.service';
import { RequestStatus } from '../../api/requestStatus.inreface';
import { Auth } from '../../context/auth.context.interface';

interface ConfirmProps {
  context: {
    authorize: Auth['authorize'];
    authorized: Auth['authorized'];
  };
}

export class Confirm extends Component<ConfirmProps, any> {
  private url: string;

  constructor(props: ConfirmProps) {
    super(props);
    this.url = '';
  }

  public componentWillMount() {
    this.url = window.location.toString();
  }

  public async componentDidMount() {
    this.props.context.authorize(confirmAccount, {
      param: this.url.slice(this.url.indexOf('?'))
    });
  }

  public render() {
    return (
      <div>
        {this.props.context.authorized ? (
          <Redirect to='/profile' />
        ) : (
          <h5>Loading...</h5>
        )}
      </div>
    );
  }
}
