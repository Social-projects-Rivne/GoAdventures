import React, { Component} from 'react';
import {sentRecoveryEmail} from "../../api/auth.service";
import {Auth} from "../../context/auth.context.interface";
import {Redirect} from "react-router";

interface SendNewPasswordPropsTypes {
    context: {
        authorized: Auth['authorized'];
        authorize: Auth['authorize'];
    };
}

export class SendRecoveryEmail extends Component<SendNewPasswordPropsTypes, any> {
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
        this.props.context.authorize(
            sentRecoveryEmail, { param: this.token.slice(this.token.indexOf('?')) }
        );
    }

    render(): React.ReactNode {
        return <Redirect to='/'/>;
    }
}