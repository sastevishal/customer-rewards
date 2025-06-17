package com.retail.helper;

import com.retail.entity.Customer;
import com.retail.exceptionhandler.NoCustomerFoundException;
import com.retail.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Helper class for interacting with the CustomerRepository.
 * <p>
 * Provides utility methods to abstract and centralize customer-related database operations.
 */
@Component
public class CustomerRepositoryHelper {

    private static final Logger logger = LoggerFactory.getLogger(CustomerRepositoryHelper.class);

    private final CustomerRepository customerRepository;

    public CustomerRepositoryHelper(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * Fetches all customers from the repository.
     *
     * @return List of all {@link Customer} entities.
     */
    public List<Customer> fetchAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        logger.debug("Fetched {} customers from database", customers.size());
        return customers;
    }

    /**
     * Fetches a specific customer by their ID.
     *
     * @param customerId the unique ID of the customer to be fetched.
     * @return the {@link Customer} entity.
     * @throws NoCustomerFoundException if no customer exists with the provided ID.
     */
    public Customer fetchCustomerById(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> {
                    logger.warn("Customer not found with ID: {}", customerId);
                    return new NoCustomerFoundException("Customer not found with ID: " + customerId);
                });
    }
}
