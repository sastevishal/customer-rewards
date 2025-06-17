package com.retail.dto;

import java.time.LocalDate;

public class TransactionResponse {
    private Long transactionId;
    private Double transactionAmount;
    private LocalDate transactionDate;

    public TransactionResponse(Long id, Double amt, LocalDate date) {
        this.transactionId = id;
        this.transactionAmount = amt;
        this.transactionDate = date;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public Double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(Double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }
}
