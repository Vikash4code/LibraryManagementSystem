package com.vikash.lms.model;

import java.math.BigDecimal;
import java.sql.Date;

public class Fine {
    private int id;
    private int transactionId;
    private int userId;
    private int bookId;
    private BigDecimal amount;
    private int daysOverdue;
    private Date fineDate;
    private String status;

    // Constructors
    public Fine() {}

    public Fine(int transactionId, int userId, int bookId, BigDecimal amount, int daysOverdue) {
        this.transactionId = transactionId;
        this.userId = userId;
        this.bookId = bookId;
        this.amount = amount;
        this.daysOverdue = daysOverdue;
        this.fineDate = new Date(System.currentTimeMillis());
        this.status = "PENDING";
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public int getDaysOverdue() {
        return daysOverdue;
    } 

    public void setDaysOverdue(int daysOverdue) {
        this.daysOverdue = daysOverdue;
    }

    public Date getFineDate() {
        return fineDate;
    }

    public void setFineDate(Date fineDate) {
        this.fineDate = fineDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}