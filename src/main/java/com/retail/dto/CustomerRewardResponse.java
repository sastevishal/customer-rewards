package com.retail.dto;

import java.util.List;
import java.util.Map;

public class CustomerRewardResponse {
    private Long customerId;
    private String customerName;
    private Map<String, Integer> monthlyRewards;
    private int totalRewards;
    private List<TransactionResponse> transactions;

    public CustomerRewardResponse(Long customerId, String customerName, int totalPoints, List<TransactionResponse> transactions) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.totalRewards = totalPoints;
        this.transactions = transactions;
    }

    public CustomerRewardResponse() {
    }

    public CustomerRewardResponse(Long customerId, String customerName, int totalPoints) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.totalRewards = totalPoints;
    }

    public CustomerRewardResponse() {

    }

    public CustomerRewardResponse(Long customerId, String customerName, int totalPoints) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.totalRewards = totalPoints;
    }

    public CustomerRewardResponse() {

    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Map<String, Integer> getMonthlyRewards() {
        return monthlyRewards;
    }

    public void setMonthlyRewards(Map<String, Integer> monthlyRewards) {
        this.monthlyRewards = monthlyRewards;
    }

    public int getTotalRewards() {
        return totalRewards;
    }

    public void setTotalRewards(int totalRewards) {
        this.totalRewards = totalRewards;
    }

    public List<TransactionResponse> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionResponse> transactions) {
        this.transactions = transactions;
    }
}

