package com.retail.helper;

import com.retail.entity.Transaction;
import com.retail.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

/**
 * Helper component that encapsulates transaction-related data access operations.
 * <p>
 * This class interacts with {@link TransactionRepository} to fetch transactions
 * for specific customers and date ranges.
 */
@Component
public class TransactionRepositoryHelper {

    private static final Logger logger = LoggerFactory.getLogger(TransactionRepositoryHelper.class);

    private final TransactionRepository transactionRepository;

    public TransactionRepositoryHelper(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    /**
     * Retrieves all transactions for a specific customer within a given date range.
     *
     * @param customerId the unique identifier of the customer
     * @param startDate  the start date of the period
     * @param endDate    the end date of the period
     * @return List of {@link Transaction} entities
     */
    public List<Transaction> findByCustomerIdAndTransactionDate(Long customerId, LocalDate startDate, LocalDate endDate) {
        logger.debug("Fetching transactions for customerId={} between {} and {}", customerId, startDate, endDate);
        return transactionRepository.findByCustomerIdAndTransactionDateBetween(customerId, startDate, endDate);
    }
}
