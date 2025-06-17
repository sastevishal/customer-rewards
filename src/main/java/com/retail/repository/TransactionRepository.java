package com.retail.repository;

import com.retail.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository interface for managing {@link Transaction} entities.
 * <p>
 * This interface extends {@link JpaRepository} to provide standard CRUD operations
 * and includes a custom method to fetch transactions for a specific customer
 * within a given date range.
 * <p>
 * Methods:
 * - findByCustomerIdAndTransactionDateBetween:
 * Retrieves all transactions made by a specific customer between two dates (inclusive).
 */
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    /**
     * Fetches all transactions for the specified customer within the given date range.
     *
     * @param customerId the ID of the customer
     * @param startDate  start of the transaction date range
     * @param endDate    end of the transaction date range
     * @return List of Transaction objects matching the criteria
     */
    List<Transaction> findByCustomerIdAndTransactionDateBetween(Long customerId, LocalDate startDate, LocalDate endDate);
}
