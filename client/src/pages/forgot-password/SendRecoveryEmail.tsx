import React, { Component} from 'react';
import {sentRecoveryEmail} from "../../api/auth.service";
import {Redirect} from "react-router";

export class SendRecoveryEmail extends Component<any, any> {
    private token: string;

    constructor(props: any) {
        super(props);
        this.token = '';
        console.debug(this.props)
    }

    public componentWillMount() {
        this.token = window.location.toString();
    }

    public async componentDidMount() {
        return sentRecoveryEmail(this.token.slice(this.token.indexOf('?')));
    }

    render(): React.ReactNode {
        return <Redirect to='/'/>;
    }
}
