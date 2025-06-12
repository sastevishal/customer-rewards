package com.retail.helper;

import com.retail.entity.Customer;
import com.retail.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerRepositoryHelperTest {

    private CustomerRepositoryHelper customerRepositoryHelper;
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        customerRepository = mock(CustomerRepository.class);
        customerRepositoryHelper = new CustomerRepositoryHelper(customerRepository);
    }

    @Test
    void fetchAllCustomers_shouldReturnCustomerList() {
        Customer customer1 = new Customer();
        customer1.setCustomerId(1L);
        customer1.setCustomerName("Alice");
        Customer customer2 = new Customer();
        customer2.setCustomerId(2L);
        customer2.setCustomerName("Bob");
        List<Customer> mockCustomers = Arrays.asList(customer1, customer2);
        when(customerRepository.findAll()).thenReturn(mockCustomers);
        List<Customer> result = customerRepositoryHelper.fetchAllCustomers();
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Alice", result.get(0).getCustomerName());
        assertEquals("Bob", result.get(1).getCustomerName());
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    void fetchAllCustomers_shouldReturnEmptyListWhenNoCustomers() {
        when(customerRepository.findAll()).thenReturn(List.of());
        List<Customer> result = customerRepositoryHelper.fetchAllCustomers();
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(customerRepository, times(1)).findAll();
    }
}
