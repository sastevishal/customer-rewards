package com.retail.service;

import com.retail.dto.CustomerRewardResponse;
import com.retail.entity.Customer;
import com.retail.hanlder.RewardDataHandler;
import com.retail.hanlder.RewardValidationHandler;
import com.retail.helper.CustomerRepositoryHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * Implementation of RewardService to process reward calculation logic.
 *
 * This class:
 * - Validates the input date.
 * - Fetches customer data.
 * - Validates presence of customers.
 * - Calculates reward points for each customer within the last 3 months.
 */
@Service
public class RewardServiceImpl implements RewardService {
    private static final Logger logger = LoggerFactory.getLogger(RewardServiceImpl.class);
    private final RewardDataHandler rewardDataHandler;
    private final CustomerRepositoryHelper customerRepositoryHelper;
    private final RewardValidationHandler rewardValidationHandler;
    public RewardServiceImpl(
            RewardDataHandler rewardDataHandler,
            CustomerRepositoryHelper customerRepositoryHelper,
            RewardValidationHandler rewardValidationHandler) {
        this.rewardDataHandler = rewardDataHandler;
        this.customerRepositoryHelper = customerRepositoryHelper;
        this.rewardValidationHandler = rewardValidationHandler;
    }

    /**
     * Retrieves reward points for all customers over the last 3-month period ending with the given date.
     *
     * @param endDate The end date for calculating rewards.
     * @return List of CustomerRewardResponse objects.
     */
    @Override
    public List<CustomerRewardResponse> getAllCustomerRewards(LocalDate endDate) {
        logger.info("Starting reward calculation for endDate: {}", endDate);
        rewardValidationHandler.validateEndDate(endDate);
        logger.debug("End date validated: {}", endDate);
        LocalDate startDate = endDate.minusMonths(3);
        logger.debug("Computed start date: {}", startDate);
        List<Customer> customers = customerRepositoryHelper.fetchAllCustomers();
        logger.debug("Fetched {} customers from repository", customers.size());
        rewardValidationHandler.validateCustomerList(customers);
        List<CustomerRewardResponse> customerRewardResponses = rewardDataHandler.buildCustomerRewardResponses(customers, startDate, endDate);
        logger.info("Successfully calculated rewards for {} customers", customerRewardResponses.size());
        return customerRewardResponses;
    }
}
