package com.retail.service;

import com.retail.dto.CustomerRewardResponse;

import java.time.LocalDate;
import java.util.List;

public interface RewardService {
    List<CustomerRewardResponse> getAllCustomerRewards(LocalDate startDate, LocalDate endDate);

    CustomerRewardResponse getCustomerRewardById(Long customerId, LocalDate startDate, LocalDate endDate);
}
