import React, { Component } from 'react';
import { Redirect } from 'react-router';
import { confirmAccount } from '../../api/auth.service';
import { Auth } from '../../context/auth.context.interface';


interface ConfirmPropsTypes {
  context: {
    authorized: Auth['authorized'];
    authorize: Auth['authorize'];
  };
}

export class Confirm extends Component<ConfirmPropsTypes, any> {
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
