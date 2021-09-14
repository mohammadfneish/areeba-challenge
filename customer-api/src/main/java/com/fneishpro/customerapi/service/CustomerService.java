package com.fneishpro.customerapi.service;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fneishpro.customerapi.model.Customer;
import com.fneishpro.mobile.validation.service.ValidateMobileService;

@Service
public class CustomerService {
	
	private static final Logger log = LoggerFactory.getLogger(CustomerService.class);
	
	@Autowired private EntityManager entityManager;
	@Autowired private ValidateMobileService validateMobileService;
	
	/**
	 * Get all customers from DB
	 * @param queryText will search over name, address and mobile
	 * @return list of customers
	 */
	@SuppressWarnings("unchecked")
	public List<Customer> getAllCustomers(String queryText) {
		try {
            String sql = "select c from "+Customer.class.getName()+" c";
            if( queryText !=null && !queryText.isEmpty() ) {
            	sql += "where LOWER(c.name) like ?1 or LOWER(c.address) like ?2 or LOWER(c.mobile) like ?3";
            }
 
            Query query = entityManager.createQuery(sql, Customer.class);
            
            if( queryText !=null && !queryText.isEmpty() ) {
	            String _queryText = "%"+queryText.toLowerCase()+"%";
	            query.setParameter(1, _queryText);
	            query.setParameter(2, _queryText);
	            query.setParameter(3, _queryText);
            }
            
            return (List<Customer>) query.getResultList();
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
	}

	/**
	 * Get customer by id
	 * @param id
	 * @return Customer
	 */
	public Customer getCustomerById(int id) {
        try {
            String sql = "select c from "+Customer.class.getName()+" c where c.id = ?1";
            
            Query query = entityManager.createQuery(sql, Customer.class);
            query.setParameter(1, id);
 
            return (Customer) query.getSingleResult();
        } catch (NoResultException e) {
        	log.warn("No customer found for ID={}", id);
            return null;
        }
    }

	/**
	 * Create new customer
	 * @param customer
	 * @return the created customer
	 */
	@Transactional
	public Customer createCustomer(Customer customer) {
		Customer c = null;
        try {
        	if( validateMobileService.isPhoneValid(customer.getMobile()) ) {
        		entityManager.persist(customer);
        		c = customer;
        	} else {
        		c = new Customer(-1);
        	}
        } catch (Exception e) {
        	log.warn("", e);
        }
        
        return c;
    }

	/**
	 * update the given customer
	 * @param customer
	 * @return the updated customer
	 */
	@Transactional
	public Customer updateCustomer(Customer customer) {
		try {
        	return entityManager.merge(customer);
        } catch (Exception e) {
        	log.warn("", e);
            return null;
        }
	}

	/**
	 * Delete customer by id
	 * @param id the id of the customer
	 * @return true if the customer was deleted else false
	 */
	@Transactional
	public boolean deleteCustomer(int id) {
		try {
			entityManager.remove(getCustomerById(id));
			return true;
		} catch (Exception e) {
        	log.warn("", e);
            return false;
        }
	}
}
