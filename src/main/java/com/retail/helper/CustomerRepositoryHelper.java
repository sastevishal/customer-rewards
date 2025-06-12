package com.retail.helper;

import com.retail.entity.Customer;
import com.retail.repository.CustomerRepository;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class CustomerRepositoryHelper {

    private final CustomerRepository customerRepository;

    public CustomerRepositoryHelper(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * Fetches all customers from the repository.
     *
     * @return List of all customers
     */
    public List<Customer> fetchAllCustomers() {
        return customerRepository.findAll();
    }
}
