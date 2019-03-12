import React, { Component } from 'react';
import Show from './Show';


export class Profile extends Component<any, any> {
  constructor(props: any) {
    super(props)
  }
  public render() {
    return (
      <div>
        <Show />
      </div>
    );

  }
}
