package com.retail.controller;

import com.retail.dto.CustomerRewardResponse;
import com.retail.service.RewardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * REST Controller to expose reward-related endpoints.
 * <p>
 * Base Path: /api
 * Endpoints:
 * - GET /api?startDate=yyyy-MM-dd&endDate=yyyy-MM-dd → Rewards for all customers
 * - GET /api/{customerId}?startDate=yyyy-MM-dd&endDate=yyyy-MM-dd → Rewards for a specific customer
 */
@RestController
@RequestMapping("/api/rewards")
public class RewardController {

    private static final Logger logger = LoggerFactory.getLogger(RewardController.class);

    private final RewardService rewardService;

    public RewardController(RewardService rewardService) {
        this.rewardService = rewardService;
    }

    /**
     * Fetch reward summary for all customers between the specified date range.
     *
     * @param startDate the start date in yyyy-MM-dd format
     * @param endDate   the end date in yyyy-MM-dd format
     * @return ResponseEntity containing list of customer reward responses
     */
    @GetMapping
    public ResponseEntity<List<CustomerRewardResponse>> getAllCustomerRewards(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {

        logger.info("Fetching rewards for all customers from {} to {}", startDate, endDate);

        List<CustomerRewardResponse> rewards = rewardService.getAllCustomerRewards(startDate, endDate);

        logger.debug("Total customer rewards retrieved: {}", rewards.size());
        return ResponseEntity.ok(rewards);
    }

    /**
     * Fetch reward summary for a specific customer between the specified date range.
     *
     * @param customerId the unique identifier of the customer
     * @param startDate  the start date in yyyy-MM-dd format
     * @param endDate    the end date in yyyy-MM-dd format
     * @return ResponseEntity containing reward response of the specified customer
     */
    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerRewardResponse> getCustomerRewardById(
            @PathVariable Long customerId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {

        logger.info("Fetching reward for customerId={} from {} to {}", customerId, startDate, endDate);

        CustomerRewardResponse response = rewardService.getCustomerRewardById(customerId, startDate, endDate);

        logger.debug("Customer {} reward total: {}", customerId, response.getTotalRewards());
        return ResponseEntity.ok(response);
    }
}
