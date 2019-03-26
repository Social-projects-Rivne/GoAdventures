import React, { Component } from 'react';

import { withRouter } from 'react-router-dom';
import { FaPlus } from 'react-icons/fa';
import "./AddEventBtn.scss"
import { RouteComponentProps } from 'react-router';



export class AddEventBtn extends Component<any, any> {

    setRedirect = () => {
        // this.props.history.push('/');
        console.debug(this.props)
    }

    render() {
        return (
            <div className="btn-add ">
                <button type="button" className="btn btn-info btn-circle btn-xl fixed-bottom " onClick={this.setRedirect}><FaPlus />

                </button>
            </div>)
    }
}

withRouter(AddEventBtn);