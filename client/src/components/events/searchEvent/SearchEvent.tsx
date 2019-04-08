import React, { Component, ChangeEvent } from 'react';
import { withRouter, Route } from 'react-router';

export class SearchEvent extends Component<any, any> {
    constructor(props: any) {
        super(props);
        this.state = {
            redirect: false
        };
        console.debug(this.props);
    }
    /**
     * redirect
     */
    public redirectTo() {
        this.setState({ redirect: true });
    }
    handleChange(e: any) {
        if (window.location.pathname != '/events') {
            // <Link ></Link>

        }
        <Route path="/events" />
        console.log(e.target.value)
    }
    public render() {
        return (<div>
            <input placeholder="search" onChange={(e: any) => {
                //console.debug(e);
                this.handleChange(e);
            }}></input>
        </div>)
    }
}

export default withRouter(SearchEvent);