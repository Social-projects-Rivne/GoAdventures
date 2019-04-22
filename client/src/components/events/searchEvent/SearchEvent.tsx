import React, { Component, ChangeEvent } from 'react';
import { withRouter, Route } from 'react-router';

export class SearchEvent extends Component<any, any> {
    constructor(props: any) {
        super(props);
        this.state = {
            redirect: false
        };
    }
    /**
     * redirect
     */
    public redirectTo() {
        this.setState({ redirect: true });
    }
    public handleChange(e: any) {
        if (window.location.pathname != '/events') {
            // <Link ></Link>

        }
        <Route path='/events' />;
        console.log(e.target.value);
    }
    public render() {
        return (<div>
            <input placeholder='search' onChange={(e: any) => {
                this.handleChange(e);
            }}></input>
        </div>);
    }
}

export default withRouter(SearchEvent);
