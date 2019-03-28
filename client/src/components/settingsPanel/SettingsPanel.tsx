import React, { Component, ReactNode } from 'react';
import './SettingsPanel.scss';

interface PanelProps {
  children: {
    left?: ReactNode
    middle?: ReactNode
    right?: ReactNode
  };
}

export class SettingsPanel extends Component<PanelProps, any> {
  constructor(props: any) {
    super(props);
  }

  public render() {
    const { left, middle, right } = this.props.children;
    return (
      <div className='SettingsPanel'>
        {left ? (
          <div className='content-column d-flex flex-column h-100'>{left}</div>
        ) : null}
        {middle ? (
          <div className='content-column d-flex flex-column h-100'>
            {middle}
          </div>
        ) : null}
        {right ? (
          <div className='content-column d-flex flex-column h-100'>{right}</div>
        ) : null}
      </div>
    );
  }
}
