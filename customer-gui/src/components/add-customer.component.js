import React, { Component } from "react";
import CustomerDataService from "../services/customer.service";

import { confirmAlert } from 'react-confirm-alert'; // Import
import 'react-confirm-alert/src/react-confirm-alert.css'; // Import css

export default class AddCustomer extends Component {
  constructor(props) {
    super(props);
    this.onChangeName = this.onChangeName.bind(this);
    this.onChangeAddress = this.onChangeAddress.bind(this);
    this.onChangeMobile = this.onChangeMobile.bind(this);
    this.saveCustomer = this.saveCustomer.bind(this);
    this.newCustomer = this.newCustomer.bind(this);
    this.showCustomers = this.showCustomers.bind(this);

    this.state = {
      id: null,
      name: "",
      address: "",
      mobile: "",

      submitted: false
    };
  }

  onChangeName(e) {
    this.setState({
      name: e.target.value
    });
  }

  onChangeAddress(e) {
    this.setState({
      address: e.target.value
    });
  }
  
  onChangeMobile(e) {
    this.setState({
      mobile: e.target.value
    });
  }

  saveCustomer() {
    var data = {
      name: this.state.name,
      address: this.state.address,
      mobile: this.state.mobile
    };

    CustomerDataService.create(data)
      .then(response => {
        console.log(response.data);
      	if( response.data.id == -1 ) {
      		confirmAlert({
      			title: 'Validation',
      			message: 'Invalid Phone Number',
      			buttons: [{
			      label: 'OK',
			      onClick: () => console.log('ok clicked')
			    }]
      		});
      	} else {
	        this.setState({
	          id: response.data.id,
	          name: response.data.name,
	          mobile: response.data.mobile,
	          address: response.data.address,
	          submitted: true
	        });
      	}
      })
      .catch(e => {
        console.log(e);
      });
  }

  newCustomer() {
    this.setState({
      id: null,
      name: "",
      mobile: "",
      address: "",
      submitted: false
    });
  }
  
  showCustomers() {
  	this.props.history.push('/customers')
  }

  render() {
    return (
      <div className="submit-form">
        {this.state.submitted ? (
          <div>
            <h4>You submitted successfully!</h4>
            <button className="btn btn-success" onClick={this.newCustomer}>
              Add
            </button>
            
            <button className="btn btn-info" onClick={this.showCustomers}>
              Customers
            </button>
          </div>
        ) : (
          <div>
            <div className="form-group">
              <label htmlFor="name">Name</label>
              <input
                type="text"
                className="form-control"
                id="name"
                required
                value={this.state.name}
                onChange={this.onChangeName}
                name="name"
              />
            </div>

            <div className="form-group">
              <label htmlFor="address">Mobile</label>
              <input
                type="text"
                className="form-control"
                id="mobile"
                required
                value={this.state.mobile}
                onChange={this.onChangeMobile}
                name="mobile"
              />
            </div>
            
            <div className="form-group">
              <label htmlFor="address">Address</label>
              <input
                type="text"
                className="form-control"
                id="address"
                required
                value={this.state.address}
                onChange={this.onChangeAddress}
                name="address"
              />
            </div>

            <button onClick={this.saveCustomer} className="btn btn-success">
              Submit
            </button>
          </div>
        )}
      </div>
    );
  }
}
