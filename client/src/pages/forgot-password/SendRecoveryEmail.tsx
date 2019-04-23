import React, { Component } from 'react';
import { sentRecoveryEmail } from '../../api/auth.service';
import { Redirect } from 'react-router';

export class SendRecoveryEmail extends Component<any, any> {
  private token: string;
  private error: boolean;

  constructor(props: any) {
    super(props);
    this.token = '';
    this.error = false;
  }

  public componentWillMount() {
    this.token = window.location.toString();
  }

  public componentDidMount() {
    (async () => {
      this.error = await sentRecoveryEmail(
        this.token.slice(this.token.indexOf('?'))
      );
    })();
  }

  public render(): React.ReactNode {
    return (
      <div>
        {this.error ? (
          <div className='page-container'>
            <h2 className='danger'>Something went wrong, try again later</h2>
          </div>
        ) : (
          <Redirect to='/' />
        )}
      </div>
    );
  }
}
