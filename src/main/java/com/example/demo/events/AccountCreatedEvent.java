package com.example.demo.events;

public class AccountCreatedEvent {
    public final String id;
    public final String accountCreator;
    public final double balance;

    public AccountCreatedEvent(String id,String accountCreator,double balance){
        this.id=id;
        this.accountCreator=accountCreator;
        this.balance=balance;

    }

}
