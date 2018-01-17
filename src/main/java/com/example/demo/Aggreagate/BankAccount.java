package com.example.demo.Aggreagate;

import com.example.demo.Commands.CloseAccountCommand;
import com.example.demo.Commands.DepositMoneyCommand;
import com.example.demo.Commands.WithdrawMoneyCommand;
import com.example.demo.Commands.CreateAccountCommand;
import com.example.demo.Events.AccountClosedEvent;
import com.example.demo.Events.AccountCreatedEvent;
import com.example.demo.Events.MoneyDepositedEvent;
import com.example.demo.Events.MoneyWithdrawnEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateLifecycle;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.util.Assert;

import java.io.Serializable;
@Aggregate
public class BankAccount implements Serializable {
    private static final long serialVersionUID=1L;
    @CommandHandler
    public BankAccount(CreateAccountCommand command){
        String id=command.id;
        String creator=command.accountCreator;

        Assert.hasLength(id,"Missing id");
        Assert.hasLength(creator,"Missing account creator");
        AggregateLifecycle.apply(new AccountCreatedEvent(id,creator,0));
    }
    public BankAccount(){

    }
    @AggregateIdentifier
    private String id;
    private double balance;
    private String owner;
    @EventSourcingHandler
    protected void on(AccountCreatedEvent event){
        this.id=event.id;
        this.owner=event.accountCreator;
        this.balance=event.balance;
    }
    @CommandHandler
    protected void on(DepositMoneyCommand command){
        double amount =command.amount;
        Assert.isTrue(amount>0.0, "Deposite must be a postive number.");

        AggregateLifecycle.apply(new MoneyDepositedEvent(id,amount));
    }
    @EventSourcingHandler
    protected void on(MoneyDepositedEvent event){
        this.balance+=event.amount;
    }
    @CommandHandler
    protected void on(WithdrawMoneyCommand command){
        double amount=command.amount;

        Assert.isTrue(amount>0.0,"Amount must be a positive number.");

        if(balance-amount<0){
            throw new InsufficientBalanceException("Insufficient balance.");
        }
        AggregateLifecycle.apply(new MoneyWithdrawnEvent(id,amount));
    }
    public static class InsufficientBalanceException extends RuntimeException{
            InsufficientBalanceException(String message){
                super(message);
            }
    }
    @EventSourcingHandler
    protected void on(MoneyWithdrawnEvent event){
        this.balance-=event.amount;
    }
    @CommandHandler
    protected void on(CloseAccountCommand command){
        AggregateLifecycle.apply(new AccountClosedEvent(id));
    }
    @EventSourcingHandler
    protected void on(AccountClosedEvent event){
        AggregateLifecycle.markDeleted();
    }
}
