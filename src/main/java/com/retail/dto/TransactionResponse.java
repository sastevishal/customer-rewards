package com.retail.dto;

import java.time.LocalDate;

public class TransactionResponse {

    private double amount;
    private LocalDate date;
    private int rewardPoints;

    public TransactionResponse(double amount, LocalDate date, int rewardPoints) {
        this.amount = amount;
        this.date = date;
        this.rewardPoints = rewardPoints;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getRewardPoints() {
        return rewardPoints;
    }

    public void setRewardPoints(int rewardPoints) {
        this.rewardPoints = rewardPoints;
    }

}
