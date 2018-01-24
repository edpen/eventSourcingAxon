package com.example.demo.Aggreagate;

import com.example.demo.Commands.CloseAccountCommand;
import com.example.demo.Commands.DepositMoneyCommand;
import com.example.demo.Commands.WithdrawMoneyCommand;
import com.example.demo.Commands.CreateAccountCommand;
import com.example.demo.EventType;
import com.example.demo.Events.AccountClosedEvent;
import com.example.demo.Events.AccountCreatedEvent;
import com.example.demo.Events.MoneyDepositedEvent;
import com.example.demo.Events.MoneyWithdrawnEvent;
import com.example.demo.Query.*;
//import com.example.demo.QueryStore;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateLifecycle;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Aggregate
public class BankAccount implements Serializable {
    private static final long serialVersionUID=1L;
    public static List<QueryEvent> list=new ArrayList<>();
    //@Autowired
    //private QueryStore store;
    @CommandHandler
    public BankAccount(CreateAccountCommand command){
        String id=command.id;
        String creator=command.accountCreator;
        Assert.hasLength(id,"Missing id");
        Assert.hasLength(creator,"Missing account creator");
       // store.save(new QueryEvent(EventType.CREATE,id,creator,0,0.0));
        list.add(new QueryCreatedEvent(EventType.CREATE,id,creator,0));
        AggregateLifecycle.apply(new AccountCreatedEvent(id,creator,0));
    }
    public BankAccount(){

    }
    public List<QueryEvent> getList(){
        return list;
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
        Assert.isTrue(amount>0.0, "Deposit must be a positive number.");
        //store.save(new QueryEvent(EventType.DEPOSIT,id,"",0.0,amount));
        list.add(new QueryDepositedEvent(EventType.DEPOSIT,id,amount));
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
       // store.save(new QueryEvent(EventType.WITHDRAW,id,"",0.0,amount));
        list.add(new QueryWithdrawnEvent(EventType.WITHDRAW,id,amount));
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
        //store.save(new QueryEvent(EventType.CLOSE,id,"",0.0,0.0));
        list.add(new QueryClosedEvent(EventType.CLOSE,id));
        AggregateLifecycle.apply(new AccountClosedEvent(id));
    }
    @EventSourcingHandler
    protected void on(AccountClosedEvent event){
        AggregateLifecycle.markDeleted();
    }
}
