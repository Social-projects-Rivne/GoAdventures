import React, { Component, ReactNode } from 'react';
import './SettingsPanel.scss';

interface PanelProps {
    children: {
        left?: ReactNode;
        middle?: ReactNode;
        right?: ReactNode;
    };
}

export class SettingsPanel extends Component<PanelProps, any> {
    constructor(props: any) {
        super(props);
    }

    public render() {
        const { left, middle, right } = this.props.children;
        return (
            <div className='jumbotron jumbotron-fluid SettingsPanel'>
                {left ? <div>{left}</div> : null}
                {middle ? <div>{middle}</div> : null}
                {right ? <div>{right}</div> : null}
            </div>
        );
    }
}
