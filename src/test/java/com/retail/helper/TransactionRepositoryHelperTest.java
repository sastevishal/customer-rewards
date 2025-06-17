package com.retail.helper;

import com.retail.entity.Customer;
import com.retail.entity.Transaction;
import com.retail.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransactionRepositoryHelperTest {

    private TransactionRepository transactionRepository;
    private TransactionRepositoryHelper transactionRepositoryHelper;

    @BeforeEach
    void setUp() {
        transactionRepository = mock(TransactionRepository.class);
        transactionRepositoryHelper = new TransactionRepositoryHelper(transactionRepository);
    }

    @Test
    void testFindByCustomerIdAndTransactionDate_returnsTransactions() {
        Long customerId = 1L;
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 3, 31);

        Customer customer = new Customer(customerId, "John Doe");

        Transaction transaction1 = new Transaction(1L, 120.0, LocalDate.of(2023, 2, 15), customer);
        Transaction transaction2 = new Transaction(2L, 75.0, LocalDate.of(2023, 3, 10), customer);

        List<Transaction> expectedTransactions = Arrays.asList(transaction1, transaction2);

        when(transactionRepository.findByCustomerIdAndTransactionDateBetween(customerId, startDate, endDate))
                .thenReturn(expectedTransactions);

        List<Transaction> result = transactionRepositoryHelper.findByCustomerIdAndTransactionDate(customerId, startDate, endDate);

        assertEquals(2, result.size());
        assertEquals(expectedTransactions, result);
        verify(transactionRepository, times(1)).findByCustomerIdAndTransactionDateBetween(customerId, startDate, endDate);
    }

    @Test
    void testFindByCustomerIdAndTransactionDate_returnsEmptyList() {
        Long customerId = 2L;
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 3, 31);

        when(transactionRepository.findByCustomerIdAndTransactionDateBetween(customerId, startDate, endDate))
                .thenReturn(Collections.emptyList());

        List<Transaction> result = transactionRepositoryHelper.findByCustomerIdAndTransactionDate(customerId, startDate, endDate);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(transactionRepository, times(1)).findByCustomerIdAndTransactionDateBetween(customerId, startDate, endDate);
    }

    @Test
    void testFindByCustomerIdAndTransactionDate_handlesNullReturn() {
        Long customerId = 3L;
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 3, 31);

        when(transactionRepository.findByCustomerIdAndTransactionDateBetween(customerId, startDate, endDate))
                .thenReturn(null);

        List<Transaction> result = transactionRepositoryHelper.findByCustomerIdAndTransactionDate(customerId, startDate, endDate);

        assertNull(result);
        verify(transactionRepository, times(1)).findByCustomerIdAndTransactionDateBetween(customerId, startDate, endDate);
    }
}
