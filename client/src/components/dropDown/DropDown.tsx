import axios from "axios";
import React, { Component, ChangeEvent } from "react";
import { DropDownSettings } from "./dropDown.interface";

export class DropDown extends Component<any, DropDownSettings> {
  constructor(props: DropDownSettings) {
    super(props);
    this.state = {
      categories: [],
      categ: ""
    };
    this.handleChange = this.handleChange.bind(this);
  }

  public componentDidMount() {
    axios.get(`http://localhost:8080/event/allCategory`).then(res => {
      const categories = res.data;
      this.setState({ categories });
    });
  }
  public componentWillMount() {
    axios.get(`http://localhost:8080/event/allCategory`).then(res => {
      const categories = res.data;
      this.setState({ categories });
    });
  }

  public handleChange(e: ChangeEvent<HTMLSelectElement>) {
    if (!!this.props.onChangeHandle) this.props.onChangeHandle(e.target.value);
  }

  public render() {
    if (typeof this.state.categories === "undefined") {
      console.log("I during componentDidMount " + this.state.categories);
      return <div />;
    } else {
      return (
        <div className={`input-group ${this.props.customClassName}`}>
          <select className="custom-select select" onChange={this.handleChange}>
            {this.state.categories.map((option, index) => (
              <option key={index} value={option.categoryName}>
                {option.categoryName}
              </option>
            ))}
          </select>
        </div>
      );
    }
  }
}
