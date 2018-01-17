package com.example.demo.Events;

public class MoneyDepositedEvent {
    public final String id;
    public final double amount;
    public MoneyDepositedEvent(String id,double amount){
        this.id=id;
        this.amount=amount;
    }
}
