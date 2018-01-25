package com.example.demo.model;

public class AccountModel {
    private String id;
    private double balance;
    private String owner;
    public AccountModel(String id, double balance,String owner){
        this.id=id;
        this.balance=balance;
        this.owner=owner;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
