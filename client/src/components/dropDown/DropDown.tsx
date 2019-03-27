import axios from 'axios';
import React, { Component } from 'react';
import { DropDownSettings } from './dropDown.interface';

export class DropDown extends Component<any, DropDownSettings> {

    state: DropDownSettings = {
        categories: []
    };

    constructor(props: DropDownSettings) {
        super(props);
        this.handleChange = this.handleChange.bind(this);
    }

    componentDidMount() {
        axios.get(`http://localhost:8080/event/allCategory`)
            .then((res) => {
                const categories = res.data;
                this.setState({ categories });
            });
    }

    componentWillMount() {
        axios.get(`http://localhost:8080/event/allCategory`)
            .then((res) => {
                const categories = res.data;
                this.setState({ categories });
            });
    }

    handleChange(e: any) {
        this.props.onCategoryChange(e.target.value);
    }

    render() {
        if (typeof this.state.categories === 'undefined') {
            console.log('I during componentDidMount ' + this.state.categories);
            return (
                <div>
                </div>
            );
        } else {
            return <div>
                <select onChange={this.handleChange}>{
                    this.state.categories.map((option) =>
                        <option value={option.categoryName}>{option.categoryName}</option>)
                }
                </select>
            </div>;
        }
    }
}
