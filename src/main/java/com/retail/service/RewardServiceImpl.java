package com.retail.service;

import com.retail.dto.CustomerRewardResponse;
import com.retail.entity.Customer;
import com.retail.entity.Transaction;
import com.retail.handler.RewardValidationHandler;
import com.retail.helper.CustomerRepositoryHelper;
import com.retail.helper.RewardCalculatorHelper;
import com.retail.helper.TransactionRepositoryHelper;
import com.retail.util.DateValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Implementation of RewardService to handle business logic for reward point calculation.
 * <p>
 * Responsibilities:
 * - Validates input dates.
 * - Retrieves customer and transaction data from the database.
 * - Calculates reward points for individual and all customers.
 * - Groups reward points by transaction month.
 * - Returns structured responses for the controller layer.
 */
@Service
public class RewardServiceImpl implements RewardService {

    private static final Logger logger = LoggerFactory.getLogger(RewardServiceImpl.class);

    private final CustomerRepositoryHelper customerRepositoryHelper;
    private final RewardValidationHandler rewardValidationHandler;
    private final TransactionRepositoryHelper transactionRepositoryHelper;

    public RewardServiceImpl(
            CustomerRepositoryHelper customerRepositoryHelper,
            RewardValidationHandler rewardValidationHandler,
            TransactionRepositoryHelper transactionRepositoryHelper) {
        this.customerRepositoryHelper = customerRepositoryHelper;
        this.rewardValidationHandler = rewardValidationHandler;
        this.transactionRepositoryHelper = transactionRepositoryHelper;
    }

    /**
     * Retrieves reward points for all customers for the given time period.
     * Calculates monthly and total reward points for each customer.
     *
     * @param startDate Start of the transaction period.
     * @param endDate   End of the transaction period.
     * @return List of CustomerRewardResponse containing points info per customer.
     */
    @Override
    public List<CustomerRewardResponse> getAllCustomerRewards(LocalDate startDate, LocalDate endDate) {
        logger.info("Initiating reward calculation for all customers from {} to {}", startDate, endDate);

        DateValidator.validateDates(startDate, endDate);
        rewardValidationHandler.validateDate(startDate);
        rewardValidationHandler.validateDate(endDate);

        List<Customer> customers = customerRepositoryHelper.fetchAllCustomers();
        logger.debug("Fetched {} customers", customers.size());

        rewardValidationHandler.validateCustomerList(customers);

        return customers.stream().map(customer -> {
            logger.debug("Processing customer: {} (ID: {})", customer.getCustomerName(), customer.getCustomerId());

            List<Transaction> transactions = transactionRepositoryHelper
                    .findByCustomerIdAndTransactionDate(customer.getCustomerId(), startDate, endDate);
            logger.debug("Found {} transactions for customer {}", transactions.size(), customer.getCustomerId());

            Map<String, Integer> monthlyRewards = new HashMap<>();
            int totalRewards = 0;

            for (Transaction tx : transactions) {
                String month = tx.getTransactionDate().getMonth().toString();
                int points = RewardCalculatorHelper.calculateRewardPoints(tx.getTransactionAmount());
                monthlyRewards.put(month, monthlyRewards.getOrDefault(month, 0) + points);
                totalRewards += points;

                logger.debug("Transaction ID {}: Amount = {}, Points = {}", tx.getTransactionId(), tx.getTransactionAmount(), points);
            }

            CustomerRewardResponse response = new CustomerRewardResponse();
            response.setCustomerId(customer.getCustomerId());
            response.setCustomerName(customer.getCustomerName());
            response.setMonthlyRewards(monthlyRewards);
            response.setTotalRewards(totalRewards);

            logger.info("Total rewards for customer {} = {}", customer.getCustomerId(), totalRewards);

            return response;
        }).collect(Collectors.toList());
    }

    /**
     * Retrieves reward points for a specific customer over a given time frame.
     *
     * @param customerId ID of the customer.
     * @param startDate  Start of the transaction period.
     * @param endDate    End of the transaction period.
     * @return CustomerRewardResponse containing the total reward points.
     */
    @Override
    public CustomerRewardResponse getCustomerRewardById(Long customerId, LocalDate startDate, LocalDate endDate) {
        logger.info("Calculating reward for customer ID: {} from {} to {}", customerId, startDate, endDate);

        DateValidator.validateDates(startDate, endDate);

        Customer customer = customerRepositoryHelper.fetchCustomerById(customerId);
        logger.debug("Fetched customer: {}", customer.getCustomerName());

        List<Transaction> transactions = transactionRepositoryHelper
                .findByCustomerIdAndTransactionDate(customerId, startDate, endDate);

        logger.debug("Found {} transactions for customer {}", transactions.size(), customerId);

        int totalPoints = transactions.stream()
                .mapToInt(t -> {
                    int points = RewardCalculatorHelper.calculateRewardPoints(t.getTransactionAmount());
                    logger.debug("Transaction ID {}: Amount = {}, Points = {}", t.getTransactionId(), t.getTransactionAmount(), points);
                    return points;
                })
                .sum();

        logger.info("Total rewards for customer ID {} = {}", customerId, totalPoints);

        return new CustomerRewardResponse(customer.getCustomerId(), customer.getCustomerName(), totalPoints);
    }
}
