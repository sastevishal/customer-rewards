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
 * REST Controller to handle reward-related endpoints.
 *
 * Endpoint: /api/rewards
 * - Accepts 'endDate' as a query parameter in 'yyyy-MM-dd' format.
 * - Returns a list of CustomerRewardResponse objects.
 */
@RestController
@RequestMapping("/api")
public class RewardController {
    private static final Logger logger = LoggerFactory.getLogger(RewardController.class);
    private final RewardService rewardService;
    public RewardController(RewardService rewardService) {
        this.rewardService = rewardService;
    }

    /**
     * Endpoint to fetch rewards for all customers for the last 3 months from the provided endDate.
     *
     * @param endDate the end date in yyyy-MM-dd format
     * @return ResponseEntity containing list of customer reward responses
     */
    @GetMapping("/rewards")
    public ResponseEntity<List<CustomerRewardResponse>> getRewardsForAllCustomers(
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        logger.info("Received request to fetch customer rewards with endDate: {}", endDate);
        List<CustomerRewardResponse> customerRewardResponses = rewardService.getAllCustomerRewards(endDate);
        return ResponseEntity.ok(customerRewardResponses);
    }
}
