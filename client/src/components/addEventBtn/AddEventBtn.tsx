import React, { Component } from 'react';
import { FaPlus } from 'react-icons/fa';
import { Redirect } from 'react-router';
import { withRouter } from 'react-router-dom';
import './AddEventBtn.scss';

export class AddEventBtn extends Component<any, any> {
  constructor(props: any) {
    super(props);
    this.state = {
      redirect: false
    };
  }

  public redirectTo() {
    this.setState({ redirect: true });
  }
  public render() {
    return (
      <div className='btn-add '>
        <button
          type='button'
          className='btn btn-info btn-circle btn-xl fixed-bottom '
          onClick={this.redirectTo.bind(this)}
        >
          <FaPlus />
          {this.state.redirect ? (
            <Redirect
              push
              to={{
                pathname: `/create-event`,
                state: {
                  ...this.props
                }
              }}
            />
          ) : null}
        </button>
      </div>
    );
  }
}

withRouter(AddEventBtn);
