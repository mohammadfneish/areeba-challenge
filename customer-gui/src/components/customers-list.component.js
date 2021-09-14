import React, { Component } from "react";
import CustomerDataService from "../services/customer.service";
import { Link } from "react-router-dom";

import { confirmAlert } from 'react-confirm-alert'; // Import
import 'react-confirm-alert/src/react-confirm-alert.css'; // Import css

export default class CustomersList extends Component {
  constructor(props) {
    super(props);
    this.onChangeSearch = this.onChangeSearch.bind(this);
    this.retrieveCustomers = this.retrieveCustomers.bind(this);
    this.refreshList = this.refreshList.bind(this);
    this.setActiveCustomer = this.setActiveCustomer.bind(this);
    this.deleteCustomer = this.deleteCustomer.bind(this);
    this.searchCustomer = this.searchCustomer.bind(this);

    this.state = {
      customers: [],
      currentCustomer: null,
      currentIndex: -1,
      searchCustomer: ""
    };
  }

  componentDidMount() {
    this.retrieveCustomers();
  }

  onChangeSearch(e) {
    const searchCustomer = e.target.value;

    this.setState({
      searchCustomer: searchCustomer
    });
  }

  retrieveCustomers() {
    CustomerDataService.getAll()
      .then(response => {
        this.setState({
          customers: response.data
        });
        console.log(response.data);
      })
      .catch(e => {
        console.log(e);
      });
  }

  refreshList() {
    this.retrieveCustomers();
    this.setState({
      currentCustomer: null,
      currentIndex: -1
    });
  }

  setActiveCustomer(customer, index) {
    this.setState({
      currentCustomer: customer,
      currentIndex: index
    });
  }
  
  deleteCustomer(e) {
  	const me = this;
  	const customerId = e.target.dataset.cid;
  	confirmAlert({
  		title: 'Confirm',
      	message: 'Are you sure you want to delete this customer.',
     	buttons: [{
          label: 'Yes',
          onClick: () => me._deleteCustomer(customerId)
          	
        }, {
          label: 'No',
          onClick: () => console.log('close confirm windows')
        }]
  	});
  }
  
  _deleteCustomer(customerId) {
	CustomerDataService.delete(customerId)
      .then(response => {
        console.log(response.data);
        this.refreshList();
      })
      .catch(e => {
        console.log(e);
      });
  }
          
  searchCustomer() {
    this.setState({
      currentCustomer: null,
      currentIndex: -1
    });

    CustomerDataService.findCustomer(this.state.searchCustomer)
      .then(response => {
        this.setState({
          customers: response.data
        });
        console.log(response.data);
      })
      .catch(e => {
        console.log(e);
      });
  }

  render() {
    const { searchCustomer, customers, currentCustomer, currentIndex } = this.state;

    return (
      <div className="list row">
        <div className="col-md-8">
          <div className="input-group mb-3">
            <input
              type="text"
              className="form-control"
              placeholder="Search customer name/mobile/address"
              value={searchCustomer}
              onChange={this.onChangeSearch}
            />
            <div className="input-group-append">
              <button
                className="btn btn-outline-secondary"
                type="button"
                onClick={this.searchCustomer}
              >
                Search
              </button>
            </div>
          </div>
        </div>
        <div className="col-md-6">
          <h4>Customers List</h4>

          <ul className="list-group">
            {customers &&
              customers.map((customer, index) => (
                <li
                  className={
                    "list-group-item " +
                    (index === currentIndex ? "active" : "")
                  }
                  onClick={() => this.setActiveCustomer(customer, index)}
                  key={index}
                >
                  {customer.name}
                </li>
              ))}
          </ul>

        </div>
        <div className="col-md-6">
          {currentCustomer ? (
            <div>
              <h4>Customer</h4>
              <div>
                <label>
                  <strong>Name:</strong>
                </label>{" "}
                {currentCustomer.name}
              </div>
              <div>
                <label>
                  <strong>Mobile:</strong>
                </label>{" "}
                {currentCustomer.mobile}
              </div>
              <div>
                <label>
                  <strong>Address:</strong>
                </label>{" "}
                {currentCustomer.address}
              </div>

              <Link
                to={"/customers/" + currentCustomer.id}
                className="badge badge-warning"
              >
                Edit
              </Link>
              
              <Link
              	to=""
                className="badge badge-danger ml-2"
                onClick={this.deleteCustomer}
                data-cid={currentCustomer.id}
              >
                Delete
              </Link>
              
            </div>
          ) : (
            <div>
              <br />
              <p>Please click on a Customer...</p>
            </div>
          )}
        </div>
      </div>
    );
  }
}
