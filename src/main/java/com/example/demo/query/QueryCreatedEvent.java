package com.example.demo.query;

import com.example.demo.enums.EventType;

public class QueryCreatedEvent extends QueryEvent {
    private String accountCreator;
    private double balance;
    public QueryCreatedEvent(EventType eventType, String id,String accountCreator,double balance) {
        super(eventType, id);
        this.balance=balance;
        this.accountCreator=accountCreator;
    }

    public void setAccountCreator(String accountCreator) {
        this.accountCreator = accountCreator;
    }

    public String getAccountCreator() {
        return accountCreator;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
