package com.retail.repository;

import com.retail.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Customer entity.
 * <p>
 * This interface extends JpaRepository to provide built-in CRUD operations
 * for Customer data using the customer's ID as the primary key.
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
