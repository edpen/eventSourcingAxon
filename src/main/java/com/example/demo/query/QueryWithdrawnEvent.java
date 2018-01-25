package com.example.demo.query;

import com.example.demo.enums.EventType;

public class QueryWithdrawnEvent extends QueryEvent {
    private double amount;

    public QueryWithdrawnEvent(EventType eventType, String id, double amount) {
        super(eventType, id);
        this.amount=amount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
