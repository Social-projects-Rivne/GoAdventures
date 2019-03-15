import React, { Component } from 'react';
import axios from 'axios';
import { DropDownSettings } from './dropDown.interface';

var values;

export class DropDown extends Component<any, DropDownSettings> {

    public state: DropDownSettings = {
        "categories": []
    }

    constructor(props: DropDownSettings){
        super(props)
    }

    componentDidMount() {
        axios.get(`http://localhost:8080/event/allCategory`)
            .then(res => {
                const categories = res.data;
                this.setState({ categories });
            })
    }
    componentWillMount() {
        axios.get(`http://localhost:8080/event/allCategory`)
            .then(res => {
                const categories = res.data;
                this.setState({ categories });
            })
    }

    render() {
        if(typeof this.state.categories === "undefined") {
            console.log('I during componentDidMount ' + this.state.categories)
            return(
                <div>

                </div>
            );
        }else {
        return <div>
            <select>{
                this.state.categories.map((option) => <option value = {option.categoryName}>{option.categoryName}</option>)
            }
            </select>
        </div>
    }
}

}