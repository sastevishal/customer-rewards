package com.retail.hanlder;

import com.retail.dto.CustomerRewardResponse;
import com.retail.entity.Customer;
import com.retail.entity.Transaction;
import com.retail.exceptionhandler.NoTransactionFoundException;
import com.retail.helper.RewardCalculatorHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Handler class responsible for computing customer reward points
 * based on their transactions within a specified date range.
 */
@Component
public class RewardDataHandler {

    private static final Logger logger = LoggerFactory.getLogger(RewardDataHandler.class);

    /**
     * Builds a list of CustomerRewardResponse containing rewards per month and total for each customer.
     *
     * @param customers List of customers
     * @param startDate Start date of the 3-month window
     * @param endDate   End date of the 3-month window
     * @return List of CustomerRewardResponse
     * @throws NoTransactionFoundException if no transactions exist in the period
     */
    public List<CustomerRewardResponse> buildCustomerRewardResponses(List<Customer> customers, LocalDate startDate, LocalDate endDate) {
        logger.info("Starting reward calculation from {} to {}", startDate, endDate);

        List<CustomerRewardResponse> responseList = new ArrayList<>();
        int totalTransactionsInPeriod = 0;

        for (Customer customer : customers) {
            logger.debug("Processing customer: {} (ID: {})", customer.getCustomerName(), customer.getCustomerId());

            List<Transaction> filteredTx = filterTransactions(customer.getTransactions(), startDate, endDate);
            logger.debug("Filtered {} transactions for customer ID {}", filteredTx.size(), customer.getCustomerId());

            totalTransactionsInPeriod += filteredTx.size();

            if (filteredTx.isEmpty()) {
                logger.debug("No transactions found for customer ID {} in the period", customer.getCustomerId());
                continue;
            }

            Map<String, Integer> monthlyRewards = new HashMap<>();
            int totalRewards = 0;

            for (Transaction tx : filteredTx) {
                String month = tx.getTransactionDate().getMonth().toString();
                int points = RewardCalculatorHelper.calculateRewardPoints(tx.getTransactionAmount());
                monthlyRewards.put(month, monthlyRewards.getOrDefault(month, 0) + points);
                totalRewards += points;
                logger.debug("Transaction ID {}: Amount = {}, Points = {}", tx.getTransactionId(), tx.getTransactionAmount(), points);
            }

            CustomerRewardResponse customerRewardResponse = new CustomerRewardResponse();
            customerRewardResponse.setCustomerId(customer.getCustomerId());
            customerRewardResponse.setCustomerName(customer.getCustomerName());
            customerRewardResponse.setMonthlyRewards(monthlyRewards);
            customerRewardResponse.setTotalRewards(totalRewards);

            responseList.add(customerRewardResponse);
            logger.info("Total rewards for customer ID {}: {}", customer.getCustomerId(), totalRewards);
        }

        if (totalTransactionsInPeriod == 0) {
            logger.warn("No transactions found for any customer in the date range.");
            throw new NoTransactionFoundException("No transactions found for any customer in the last 3 months.");
        }

        logger.info("Reward calculation completed. Total customers with rewards: {}", responseList.size());
        return responseList;
    }

    /**
     * Filters the given transactions within the provided date range.
     *
     * @param transactions List of transactions
     * @param start        Start date
     * @param end          End date
     * @return Filtered list of transactions
     */
    private List<Transaction> filterTransactions(List<Transaction> transactions, LocalDate start, LocalDate end) {
        logger.debug("Filtering transactions between {} and {}", start, end);
        return transactions.stream()
                .filter(tx -> !tx.getTransactionDate().isBefore(start) && !tx.getTransactionDate().isAfter(end))
                .collect(Collectors.toList());
    }
}
