import React, {ChangeEvent, Component, SyntheticEvent} from 'react';
import axios from 'axios';
import './ForgotPassword.scss';

export class ForgotPassword extends Component<any, any> {
    constructor(props: any) {
        super(props);

        this.state = {
            email: ''
        };

        this.handleClick = this.handleClick.bind(this);
    }

    handleClick(event: SyntheticEvent) {
        event.preventDefault();

        if (this.state.email == "") {
            alert("Please enter your email.")
        } else {
            alert("Recovery mail sent!")

            axios.post("http://localhost:8080/auth/sent-recovery-email", null,{
                headers: {
                    'email': this.state.email
                }}).then((response) =>
                    console.log(response.status)
                );
            }
    }

    handle(event: ChangeEvent<HTMLInputElement>) {
        this.setState({
            email: event.target.value
        })
    }

    public render() {
        return (
            <div className='allHeight'>
                <div className="jumbotron">
                    <div className='container'>
                        <h1>Recovery password page</h1>
                        <hr/>
                        <div className="card-body">
                            <form id='recovery-dialog' onSubmit={this.handleClick} >
                                <input className="form-control" type="email" onChange={this.handle.bind(this)}
                                       placeholder="enter your email"/>
                                <button type="submit" className="btn btn-success">
                                    Recover password
                                </button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        );

    }
}
